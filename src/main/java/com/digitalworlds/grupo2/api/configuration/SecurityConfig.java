package com.digitalworlds.grupo2.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    private static final String CTL_MOVIE_SEARCH = "/api2/movies/search";
    private static final String CTL_MOVIE_COMING = "/api2/movies/comingsoon/{region}";
    private static final String CTL_CONFIG_COMING = "/api2/config/coming/{region}";
    private static final String CTL_INFO = "/api2/movies/";

    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";

    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, CTL_MOVIE_SEARCH + "/{movieName}", CTL_MOVIE_COMING).permitAll()
                .antMatchers(HttpMethod.POST, CTL_MOVIE_SEARCH).hasAnyRole(ADMIN, USER)

                .antMatchers(HttpMethod.GET, CTL_CONFIG_COMING).hasAnyRole(ADMIN, USER)
                .antMatchers(HttpMethod.POST, CTL_CONFIG_COMING).hasRole(ADMIN)
                .antMatchers(HttpMethod.PUT, CTL_CONFIG_COMING).hasRole(ADMIN)
                .antMatchers(HttpMethod.DELETE, CTL_CONFIG_COMING).hasRole(ADMIN)

                .antMatchers(HttpMethod.GET, CTL_INFO + "/country").hasAnyRole(ADMIN, USER)
                .antMatchers(HttpMethod.GET, CTL_INFO + "/genre").hasAnyRole(ADMIN, USER)
                .and()
                .httpBasic();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        UserDetails leandro = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin1234"))
                .roles(ADMIN)
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user1234"))
                .roles(USER)
                .build();

        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager(leandro, user);

        return userDetailsManager;
    }

}
