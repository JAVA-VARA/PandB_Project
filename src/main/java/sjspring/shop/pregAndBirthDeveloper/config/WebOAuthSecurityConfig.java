package sjspring.shop.pregAndBirthDeveloper.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import sjspring.shop.pregAndBirthDeveloper.config.jwt.TokenAuthenticationFilter;
import sjspring.shop.pregAndBirthDeveloper.config.jwt.TokenProvider;
import sjspring.shop.pregAndBirthDeveloper.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import sjspring.shop.pregAndBirthDeveloper.config.oauth.OAuth2SuccessHandler;
import sjspring.shop.pregAndBirthDeveloper.config.oauth.OAuth2UserCustomService;
import sjspring.shop.pregAndBirthDeveloper.repository.RefreshTokenRepository;
import sjspring.shop.pregAndBirthDeveloper.service.UserDetailService;
import sjspring.shop.pregAndBirthDeveloper.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebOAuthSecurityConfig {

    private final OAuth2UserCustomService oAuth2UserCustomService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers("/img/**", "/css/**", "/js/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic().disable()
                .logout().disable();

        //세션 로그인 사용X
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);


        http.authorizeHttpRequests()
                .requestMatchers("/api/token", "/login", "/signup", "/users", "/findId", "/showId", "/findPwd", "/showPwd", "/freeBoardList", "/freeBoardListEmpty", "/homePage").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll();

        http.oauth2Login()
                .loginPage("/login")
                .authorizationEndpoint()
                //사용자가 호출하는 클라이언트의 인증 시작 api에 대한 설정 ->
                //이 api를 호출하면 소셜 로그인 페이지로 사용자를 redirect 한다.
                //사용자의 인증 요청을 임시로 보관하는 리포지토리에 대한 설정
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                .and()
                //인증 및 유저 정보를 가져오는 것 까지 성공했을 때 호출되는 핸들러
                .successHandler(oAuth2SuccessHandler())
                //리소스 서버로부터 유저 정보를 가져올 때 사용되는 설정
                .userInfoEndpoint()
                .userService(oAuth2UserCustomService);

//        http.formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/articles", true);

        http.logout()
                .logoutSuccessUrl("/login");

        http.exceptionHandling()
                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                        new AntPathRequestMatcher("/api/**"));


        return http.build();
    }

    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(tokenProvider,
                refreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                userService
        );
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }
}