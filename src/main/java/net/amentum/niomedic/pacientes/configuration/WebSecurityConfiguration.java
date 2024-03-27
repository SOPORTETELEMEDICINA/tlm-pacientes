package net.amentum.niomedic.pacientes.configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author victor de la Cruz
 */
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**").authorizeRequests()
                .antMatchers("/v2/api-docs**","/info").permitAll()
                .antMatchers(HttpMethod.POST, "/pacientes**").permitAll()
                .antMatchers(HttpMethod.POST, "/pacientes/**").permitAll()
                .antMatchers(HttpMethod.POST, "/pacientes/grupos/**").permitAll()
                .antMatchers(HttpMethod.POST, "/paciente-servicios/**").permitAll()
                .antMatchers(HttpMethod.GET, "/paciente-servicios/add**").permitAll()
                .antMatchers(HttpMethod.POST, "/notificaciones-paciente**").permitAll()
                .antMatchers(HttpMethod.POST, "/notificaciones-paciente/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/notificaciones-paciente/deleteAll**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/notificaciones-paciente/deleteAll/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .httpBasic().disable();
    }
}

