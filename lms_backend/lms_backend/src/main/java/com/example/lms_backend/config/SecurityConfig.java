package com.example.lms_backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/courses/**",
                                "/api/assessments/**",
                                "/api/questions/**",
                                "/api/results/**",
                                "/api/progress/**",
                                "/api/lessons/**",
                                "/api/certificates/**",
                                "/api/announcements/**",
                                "/error"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                // Enable form login for username/password authentication
                .formLogin(form -> form
                        .loginPage("/login") // optional: your custom login page
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}


























//
//package com.example.lms_backend.config;
//
//import com.example.lms_backend.security.CustomOAuth2UserService;
//import com.example.lms_backend.security.OAuth2LoginSuccessHandler;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
//import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
//import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
//import org.springframework.security.web.SecurityFilterChain;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
//    private final CustomOAuth2UserService customOAuth2UserService;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(
//            HttpSecurity http,
//            ClientRegistrationRepository clientRegistrationRepository
//    ) throws Exception {
//
//        OAuth2AuthorizationRequestResolver resolver =
//                authorizationRequestResolver(clientRegistrationRepository);
//
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/api/auth/**",
//                                "/api/courses/**",
//                                "/api/assessments/**",
//                                "/api/questions/**",
//                                "/api/results/**",
//                                "/api/progress/**",
//                                "/api/lessons/**",
//                                "/api/certificates/**",
//                                "/oauth2/**",
//                                "/login/**",
//                                //"/api/resources/**",
//                                "/api/announcements/**",
////                                "/api/leaderboard/**",
//                                "/error"
//                        ).permitAll()
//                        .anyRequest().authenticated()
//                )
//                .oauth2Login(oauth2 -> oauth2
//                        .loginPage("/oauth2/authorization/google")
//                        .authorizationEndpoint(ae -> ae.authorizationRequestResolver(resolver))
//                        .userInfoEndpoint(ue -> ue.userService(customOAuth2UserService))
//                        .successHandler(oAuth2LoginSuccessHandler)
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public OAuth2AuthorizationRequestResolver authorizationRequestResolver(
//            ClientRegistrationRepository clientRegistrationRepository) {
//
//        DefaultOAuth2AuthorizationRequestResolver defaultResolver =
//                new DefaultOAuth2AuthorizationRequestResolver(
//                        clientRegistrationRepository, "/oauth2/authorization");
//
//        // Wrapper instead of subclass
//        return new OAuth2AuthorizationRequestResolver() {
//            @Override
//            public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
//                OAuth2AuthorizationRequest req = defaultResolver.resolve(request);
//                return customize(req);
//            }
//
//            @Override
//            public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
//                OAuth2AuthorizationRequest req = defaultResolver.resolve(request, clientRegistrationId);
//                return customize(req);
//            }
//
//            private OAuth2AuthorizationRequest customize(OAuth2AuthorizationRequest req) {
//                if (req == null) return null;
//                Map<String, Object> extraParams = new HashMap<>(req.getAdditionalParameters());
//                extraParams.put("prompt", "select_account"); // 🔑 Force account chooser
//                return OAuth2AuthorizationRequest.from(req)
//                        .additionalParameters(extraParams)
//                        .build();
//            }
//        };
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//}
