package in.levyashvin.ticketbooking.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter; 
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll()

                .requestMatchers(
                    "/v3/api-docs",
                    "/v3/api-docs/**",
                    "/swagger-resources",
                    "/swagger-resources/**",
                    "/configuration/ui",
                    "/configuration/security",
                    "/swagger-ui/**",
                    "/webjars/**",
                    "/swagger-ui.html"
                ).permitAll()

                // RBAC security configs
                // ADMIN ONLY: Creating Data (Venues, Movies, Shows)
                .requestMatchers(HttpMethod.POST, "/api/v1/venues/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/movies/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/shows/**").hasRole("ADMIN")
                // DELETE/PUT endpoints here too
                        
                // PUBLIC/USER READ ACCESS: Viewing Movies & Shows
                .requestMatchers(HttpMethod.GET, "/api/v1/movies/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/v1/shows/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/v1/venues/**").authenticated()
                        
                // USER ACTIONS: Booking & History
                .requestMatchers("/api/v1/bookings/**").authenticated()

                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); 
        
        return http.build();
    }
}