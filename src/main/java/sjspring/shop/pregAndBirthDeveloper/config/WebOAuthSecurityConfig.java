package sjspring.shop.pregAndBirthDeveloper.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
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
public class WebOAuthSecurityConfig implements WebMvcConfigurer {

    private final OAuth2UserCustomService oAuth2UserCustomService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/photo/**")
                .addResourceLocations("file:///C:/Users/sjyou/IdeaProjects/PandBproject/PandB_Project/files/");
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic().disable()
                .logout().disable();

        //세션 로그인 사용X
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //커스텀 필터 추가
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("/api/token", "/login", "/signup", "/users", "/findId", "/showId", "/findPwd", "/showPwd", "/homePage", "/").permitAll()

//                //글 작성은 ADMIN, USER에게만
                .requestMatchers("/api/**").authenticated()
                .requestMatchers("/api/**").hasAnyRole("ADMIN", "USER")
//                //글이 보이는 것은 모두에게 보이도록 설정
                .requestMatchers("/articles/**").hasAnyRole("ADMIN", "USER", "ANONYMOUS")
                .anyRequest().permitAll();

        http.oauth2Login()
                .loginPage("/login")
                //사용자를 인증 서버로 리디렉션 시키는 엔드포인트 설정.
                .authorizationEndpoint()
                //authorization 요청과 관련된 상태 저장
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                .and()
                // 로그인 성공 후의 처리를 정의하는 커스텀 핸들러 주로 토큰을 발급
                .successHandler(oAuth2SuccessHandler())
                //리소스 서버로부터 유저 정보를 가져올 때 사용되는 설정
                .userInfoEndpoint()
                .userService(oAuth2UserCustomService);

        http.formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(formLoginSuccessHandler())
                .failureUrl("/login");

        http.logout()
                .logoutSuccessUrl("/login");

        //인증 실패시 401 상태 코드 반환
        http.exceptionHandling()
                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                        new AntPathRequestMatcher("/api/**"))

                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                new AntPathRequestMatcher("/articles/**"));


        return http.build();
    }

    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(
                tokenProvider,
                refreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                userService
        );
    }

    @Bean
    public AuthenticationSuccessHandler formLoginSuccessHandler() {
        return new AuthenticationSuccessHandler(
                tokenProvider,
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
    public AuthenticationManager authenticationManager(
            HttpSecurity http,
            PasswordEncoder bCryptPasswordEncoder,
            UserDetailService userDetailService) throws Exception {

        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }


}