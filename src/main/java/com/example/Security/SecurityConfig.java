package com.example.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.AppConfig;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private EmployeeUserDetailsService employeeDetailsService;

    @Autowired
    private AppConfig appconfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // ✅ Public Endpoints
                .requestMatchers(
                    "/login",
                    "/api/test/**",
                    "/api/otp/send/**",
                    "/api/otp/validateOtp",
                    "/api/attendance/mark/**",
                    "/api/attendance/marks",
                    "/api/notifications/email",
                    "/api/notifications/clear/**",
                    "/api/employees/check-name",
                    "/api/otp/update-password",
                    "/ws/**",
                    "/api/attendance/all",
                    
                    "/app/**",

                    // ✅ Make leave APIs public for testing
                    "/api/leaves/**"
                ).permitAll()

                // ✅ Restricted employee endpoints
                .requestMatchers(
                    "/api/employees/findByEmp/**",
                    "/api/employees/updateEmpBasicInfo/**",
                    "/api/employees/updateEmp/**",
                    "/api/attendance/break/start",
                    "/api/attendance/break/end",
                    "/api/attendance/face-mark",
                    "/employee/getEmp/with-leaves/**"
                ).hasRole("EMPLOYEE")

                // ✅ Restricted admin endpoints (still protected)
                .requestMatchers(
                    "/admin/**",
                    "/api/employees/update/**",
                    "/api/employees/delete/**",
                    "/api/employees/all",
                    "/employee/update/**",
                    "/api/employees/register",
                    "/leave_policy",
                    "/leave_policy/**",
                    "/leavePolicy_table",
                    "/api/attendance/manualAttendance",
                    
                    "/ws/**"
                ).hasRole("ADMIN")

                // ✅ All others must be authenticated
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager() {
        DaoAuthenticationProvider adminProvider = new DaoAuthenticationProvider();
        adminProvider.setUserDetailsService(myUserDetailsService);
        adminProvider.setPasswordEncoder(appconfig.passwordEncoder());

        DaoAuthenticationProvider employeeProvider = new DaoAuthenticationProvider();
        employeeProvider.setUserDetailsService(employeeDetailsService);
        employeeProvider.setPasswordEncoder(appconfig.passwordEncoder());

        return new ProviderManager(adminProvider, employeeProvider);
    }
}
