package com.example.security;

import com.example.enums.RoleUser;
import com.example.security.jwt.AuthEntryPointJwt;
import com.example.security.jwt.AuthTokenFilter;
import com.example.security.services.UserDetailsServiceImpl;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author BAO 7/12/2023
 */
/**
 * @EnableWebSecurity allows Spring to find and automatically apply the class to the global Web
 * Security.
 */
@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

  private final UserDetailsServiceImpl userDetailsService;
  private final AuthEntryPointJwt unauthorizedHandler;

  public WebSecurityConfig(
      UserDetailsServiceImpl userDetailsService, AuthEntryPointJwt unauthorizedHandler) {
    this.userDetailsService = userDetailsService;
    this.unauthorizedHandler = unauthorizedHandler;
  }

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
      throws Exception {
    return authConfig.getAuthenticationManager();
  }

  /**
   * Khi dùng permitAll() thì các Spring Security cho phép tất cả các yêu cầu đi qua mà không cần
   * xác thức 1 cái gì hết. Bao gồm cả yêu cầu từ các origin khác và các request cùng origin
   *
   * <p>khi dùng post đi qua permitAll() nó cho phép cookie được gửi di vì nó không các xác thực 1
   * cái gì hết
   *
   * <p>còn khi áp dụng các bảo mật chi tiết, các giới hạn CORS sẽ được áp dụng và các yêu cầu khác
   * origin sẽ bị từ chối nếu không cấu hình CORS cho phép.
   *
   * <p>Spring security tích hợp sẵn 1 bộ lọc CORS mặc định, chỉ hỗ trợ same origin. Bộ lọc CORS này
   * cho phép yêu cầu từ nguồn khác nhưng chỉ cho phương thức GET
   *
   * <p>Điều này có nghĩa là mặc định, bộ loc CORS trong spring security của spring boot, chỉ cho
   * phép yêu cầu GET từ các nguồn khác
   *
   * @param httpSecurity
   * @return
   * @throws Exception
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .cors(config -> config.configurationSource(corsConfigurationSource()))
        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/api/admin/**")
                    .hasAnyAuthority(RoleUser.ROLE_ADMIN.name(), RoleUser.ROLE_MANAGEMENT.name())
                    .requestMatchers("/api/auth/**")
                    .permitAll()
                    .requestMatchers("/api/user/**")
                    .hasAnyAuthority(RoleUser.ROLE_USER.name())
                    .requestMatchers("/api/mobile/user/**")
                    .hasAnyAuthority(RoleUser.ROLE_USER.name())
                    .anyRequest()
                    .permitAll());
    httpSecurity.authenticationProvider(authenticationProvider());
    httpSecurity.addFilterBefore(
        authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    return httpSecurity.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
