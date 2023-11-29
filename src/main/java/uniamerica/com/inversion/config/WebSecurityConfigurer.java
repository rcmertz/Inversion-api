package uniamerica.com.inversion.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uniamerica.com.inversion.service.AuthenticationService;
import uniamerica.com.inversion.config.AuthenticationFilter;
import uniamerica.com.inversion.config.AuthorizationFilter;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpMethod.GET;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            "/api/usuarios/login/**",
            "/api/usuarios/cadastro/**",
            "/swagger-resources/**",
            "/swagger-ui.html**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/webjars/**"
    };
    @Autowired
    private final AuthenticationService authenticationService;

    @Bean
    PasswordEncoder passwordEncoder() {
        String defaultEncoding = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(defaultEncoding, new BCryptPasswordEncoder());

        return new DelegatingPasswordEncoder(defaultEncoding, encoders);
    }

    @Bean
    public AuthorizationFilter authorizationTokenFilterBean() throws Exception {
        return new AuthorizationFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManagerBean());
        authenticationFilter.setFilterProcessesUrl("/api/usuarios/login");

        http.csrf().disable();
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(authenticationFilter);
        http.addFilterBefore(authorizationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.authenticationService)
                .passwordEncoder(passwordEncoder());
    }
}
