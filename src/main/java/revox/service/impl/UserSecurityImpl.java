package revox.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import revox.domain.user;
import revox.repository.userRepository;
import revox.service.UserSecurity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.UUID;

/**
 * Created by ashraf on 8/1/2015.
 */
@Service
@Transactional
public class UserSecurityImpl implements UserSecurity {
    @Autowired
    ProviderSignInUtils signInUtils;
    @Autowired
    revox.repository.userRepository userRepository;
    @Autowired
    RememberMeServices services;
    @Autowired
    PasswordEncoder encoder;

   public boolean handSignUp(WebRequest request, HttpServletRequest req, HttpServletResponse res) {
        Connection<?> connectionFromSession = signInUtils.getConnectionFromSession(request);
        if (connectionFromSession != null) {
            UserProfile userProfile = connectionFromSession.fetchUserProfile();
            user u = userRepository.findByEmail(userProfile.getEmail()).map(user -> user).orElseGet(() ->
                            userRepository.save(new user().setEmail(userProfile.getEmail()).setPassword(encoder.encode(UUID.randomUUID().toString())).setUserName(userProfile.getUsername()))
            );

            Authentication authentication = new UsernamePasswordAuthenticationToken(u.getEmail(), u.getPassword(), u.grantedAuthorities());
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                new SecurityContextLogoutHandler().logout(req, res, auth);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            signInUtils.doPostSignUp(userProfile.getEmail(), request);
            services.loginSuccess(req, res, authentication);

            return true;
        }
        return false;
    }

}
