package com.example.demo.authentication;




import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.demo.model.UserCredentials.ADMIN_ROLE;

@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter{


	/* punto di accesso diretto al database, serve perchè lavoriamo a livello più basso */
	@Autowired 
	DataSource datasource;
	
	@Bean
	PasswordEncoder passwordEncorder() {return new BCryptPasswordEncoder();}
	
	/* quali sono le politiche effettive di authentication e autorization*/
	@Override
	public void configure (HttpSecurity http ) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/css/**").permitAll().
		antMatchers(HttpMethod.GET,"/","/index","/login","/users/register").
		permitAll().
		antMatchers(HttpMethod.POST,"/login","/users/register").
		permitAll().
		antMatchers(HttpMethod.GET,"/admin","/admin/users").hasAnyAuthority(ADMIN_ROLE).
		anyRequest().
		authenticated().
		and().
		formLogin().loginPage("/login").
		defaultSuccessUrl("/home").and().logout().
		logoutUrl("/logout").logoutSuccessUrl("/index");
	}
	
	/*specifica come trovare username, password e ruolo nel database */
	@Override
	public void configure (AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(this.datasource).
		authoritiesByUsernameQuery("SELECT user_credentials.username , user_credentials.role FROM user_credentials WHERE user_credentials.username=?")
		.usersByUsernameQuery("SELECT user_credentials.username , user_credentials.password , 1 as enabled From user_credentials WHERE user_credentials.username=?");
	}
	
}
