package revox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import revox.domain.user;
import revox.repository.userRepository;

import java.util.Optional;

/**
 * Created by ashraf on 8/2/15.
 */
@Service
@Transactional
public class UserDetailsServices implements UserDetailsService,SocialUserDetailsService {
    @Autowired
    userRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).
                map(user -> new User(user.getEmail(), user.getPassword(),
                        user.grantedAuthorities())).
                orElseThrow(() -> new UsernameNotFoundException("not found "));


    }

    @Override
    public SocialUserDetails loadUserByUserId(String s) throws UsernameNotFoundException, DataAccessException {
        UserDetails userDetails = loadUserByUsername(s);
        return new SocialUser(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
    }
}
