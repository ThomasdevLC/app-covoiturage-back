package diginamic.fr.app_covoiturage.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.security.authentication.AuthenticationProvider;

/**
 * SecurityConfiguration class is responsible for configuring Spring Security settings
 * for the application, including authentication and authorization mechanisms, session
 * management, and CORS configurations. This configuration class defines custom beans
 * and settings to secure the application endpoints and manage user access.
 *
 * Constructor Dependencies:
 * - authenticationProvider: Provides custom authentication logic for validating user credentials.
 * - jwtAuthenticationFilter: A custom filter for validating and processing JSON Web Tokens (JWT).
 *
 * Key Configuration Details:
 * 1. Configures a SecurityFilterChain using the HttpSecurity object:
 *    - Disables CSRF protection as the application uses stateless sessions.
 *    - Defines access rules for API endpoints:
 *        - Endpoints under "/auth/**" are open to all users.
 *        - "/company-vehicles/admin" and "/vehicle-bookings/admin" require the "ADMIN" role.
 *        - "/roles-management/" requires the "SUPER_ADMIN" role.
 *        - All other endpoints require authentication.
 *    - Configures stateless session management to avoid maintaining user session on the server.
 *    - Registers custom authentication and JWT filtering mechanisms.
 *
 * 2. Configures CORS settings using the CorsFilter bean:
 *    - Specifies allowed origins, including whitelisted URLs for frontend applications.
 *    - Defines allowed HTTP methods and headers.
 *    - Enables credential sharing for cross-origin requests.
 */
@Configuration
public class SecurityConfiguration {

        private final AuthenticationProvider authenticationProvider;
        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        public SecurityConfiguration(AuthenticationProvider authenticationProvider,
                        JwtAuthenticationFilter jwtAuthenticationFilter) {
                this.authenticationProvider = authenticationProvider;
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                httpSecurity
                                .csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(request -> request
                                                .requestMatchers("/auth/**").permitAll()
                                                .requestMatchers("/company-vehicles/admin").hasRole("ADMIN")
                                                .requestMatchers("/vehicle-bookings/admin").hasRole("ADMIN")
                                                .requestMatchers("/roles-management/").hasRole("SUPER_ADMIN")
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                                .addFilterBefore(corsFilter(), JwtAuthenticationFilter.class);
                return httpSecurity.build();
        }

        @Bean
        CorsFilter corsFilter() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of(
                                "https://thomasdevlc.github.io/",
                                "http://localhost:4200"));
                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
                configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                configuration.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);

                return new CorsFilter(source);
        }
}