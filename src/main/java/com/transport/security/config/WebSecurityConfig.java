package com.transport.security.config;

import static com.transport.security.utils.SecurityConstants.TRANSPORTER_ENDPOINTS;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService jwtUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    private final AccessDeniedHandler customAccessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .cors().configurationSource(corsConfigurationSource()) // Включаем CORS
            .and()
            .csrf().disable()
            .exceptionHandling()
            .accessDeniedHandler(customAccessDeniedHandler)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/login", "/home")
            .permitAll()
            .antMatchers(HttpMethod.GET, "/api/transportations/**", "/api/users/**",
                "/api/users/find", "/api/deliveries/**", "/api/cargos/**")
            .hasAnyRole("FORWARDER", "CUSTOMER", "TRANSPORTER", "ADMIN")
            .antMatchers(HttpMethod.PUT, "/api/users/**")
            .hasAnyRole("FORWARDER", "CUSTOMER", "TRANSPORTER", "ADMIN")
            .antMatchers(HttpMethod.POST, "/api/transportations/**", "/api/addresses/**",
                "/api/deliveries/**", "/api/payments/**")
            .hasAnyRole("FORWARDER", "ADMIN")
            .antMatchers(HttpMethod.PUT, "/api/transportations/**", "/api/addresses/**",
                "/api/deliveries/**", "/api/payments/**")
            .hasAnyRole("FORWARDER", "ADMIN")
            .antMatchers(HttpMethod.DELETE, "/api/transportations/**", "/api/addresses/**",
                "/api/deliveries/**", "/api/payments/**")
            .hasAnyRole("FORWARDER", "ADMIN")
            .antMatchers(HttpMethod.POST, "/api/cargos/**")
            .hasAnyRole("CUSTOMER", "ADMIN")
            .antMatchers(HttpMethod.PUT, "/api/cargos/**")
            .hasAnyRole("CUSTOMER", "ADMIN")
            .antMatchers(HttpMethod.DELETE, "/api/cargos/**")
            .hasAnyRole("CUSTOMER", "ADMIN")
            .antMatchers(HttpMethod.GET, "/api/payments/current")
            .hasAnyRole("CUSTOMER", "ADMIN")
            .antMatchers(HttpMethod.GET, TRANSPORTER_ENDPOINTS)
            .hasAnyRole("TRANSPORTER", "ADMIN")
            .antMatchers(HttpMethod.POST, "/api/emails/sendReport")
            .hasAnyRole("TRANSPORTER", "ADMIN")
            .antMatchers(HttpMethod.POST, "/api/emails/sendCustomerReminder")
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.GET, "/api/**")
            .hasAnyRole("FORWARDER", "ADMIN")
            .antMatchers(HttpMethod.POST, "/api/emails/send", "/api/emails/sendWithAttachment")
            .hasAnyRole("FORWARDER", "CUSTOMER", "TRANSPORTER", "ADMIN");

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*")); // Используем allowedOriginPatterns вместо allowedOrigins
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Разрешаем все методы
        configuration.setAllowedHeaders(List.of("*")); // Разрешаем все заголовки
        configuration.setAllowCredentials(true); // Разрешаем куки и аутентификацию

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Применяем ко всем путям
        return source;
    }
}
