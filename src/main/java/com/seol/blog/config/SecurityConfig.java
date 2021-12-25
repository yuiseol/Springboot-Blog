package com.seol.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.seol.blog.config.auth.PrincipalDetailService;

//빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
//밑에 3개는 시큐리티의 세트
@Configuration //빈등록(IoC관리)
@EnableWebSecurity //시큐리티 필터가 등록이 된다
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
public class SecurityConfig  extends WebSecurityConfigurerAdapter{

	@Autowired
	private PrincipalDetailService principalDetailService; 
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean //IoC가 되요(리턴하는 BCryptPasswordEncoder()값을 스프링이 관리)
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	//시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데
	//해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	//같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() //csrf토큰 비활성화 (테스트시 걸어두는 게 좋음)
			.authorizeRequests() //어떤 요청이 들어오면 
				.antMatchers("/", "/auth/**","/js/**", "/css/**", "/image/**", "/dummy/**") //() 안에 경로로
				.permitAll() //누가나 들어올 수 있다.
				.anyRequest() //이게 아니 다른 모든 요청은 
				.authenticated() //인증이 되야해  
			.and()
				.formLogin()
				.loginPage("/auth/loginForm") // ("/auth/**","/js/**", "/css/**", "/image/**") 빼고 우리가 커스터마이징한 /auth/loginForm 이곳으로 이동
				.loginProcessingUrl("/auth/loginProc") //스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인해준다.
				.defaultSuccessUrl("/"); //정상적으로 로그인이 끝나면 어디로 갈지 물어보는 함수
//				.failureUrl("/fail"); //실패하면 어디로 갈지 물어보는 함수 
	}
}
