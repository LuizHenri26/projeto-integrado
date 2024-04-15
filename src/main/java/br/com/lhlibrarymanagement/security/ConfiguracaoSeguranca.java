package br.com.lhlibrarymanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.lhlibrarymanagement.service.UsuarioService;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class ConfiguracaoSeguranca {

	private static final String ADMIN = "ADMINISTRADOR";
	private static final String FUNCIONARIO = "FUNCIONARIO";

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/js/*", "/img/*", "/css/*").permitAll()
				.requestMatchers("/").permitAll().requestMatchers("/buscar").permitAll().requestMatchers("/login")
				.permitAll().requestMatchers("/livro/*", "/autentica/*", "/emprestimo/*", "/membro/*")
				.hasAnyAuthority(FUNCIONARIO, ADMIN).requestMatchers("/usuario/*").hasAuthority(ADMIN).anyRequest()
				.authenticated())
				.formLogin((form) -> form.loginPage("/login").defaultSuccessUrl("/autentica/inicio", true).permitAll())
				.logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/").deleteCookies("JSESSIONID").permitAll())
				.exceptionHandling((ex) -> ex.accessDeniedPage("/acesso-negado"));
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder,
			UsuarioService userDetailsService) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder()).and().build();
	}

}
