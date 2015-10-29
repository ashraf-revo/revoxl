package revox.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.config.annotation.EnableSocial;

/**
 * Created by ashraf on 8/2/15.
 */
@Configuration
@EnableSocial
public class socialConfig extends SocialConfigurerAdapter {
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }
    @Bean
    public ProviderSignInUtils signInUtils(ConnectionFactoryLocator connectionFactoryLocator,UsersConnectionRepository connectionRepository){
        return new ProviderSignInUtils(connectionFactoryLocator,connectionRepository);
    }

}
