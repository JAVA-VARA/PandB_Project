package sjspring.shop.pregAndBirthDeveloper.config.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import sjspring.shop.pregAndBirthDeveloper.config.jwt.TokenProvider;
import sjspring.shop.pregAndBirthDeveloper.domain.RefreshToken;
import sjspring.shop.pregAndBirthDeveloper.domain.User;
import sjspring.shop.pregAndBirthDeveloper.repository.RefreshTokenRepository;
import sjspring.shop.pregAndBirthDeveloper.service.UserService;
import sjspring.shop.pregAndBirthDeveloper.util.CookieUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final String ACCESS_TOKEN_COOKIE_NAME = "access_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
    public static final String REDIRECT_PATH_1 = "/additional-info";
    public static final String REDIRECT_PATH_2 = "/homePage";

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        // OAuth2User 인터페이스를 구현한 Principal(인증 주체)을 얻음.
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        User user = userService.findByEmail((String) oAuth2User.getAttributes().get("email"));

        //리프레시 토큰 생성
        String refreshtoken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
        //db에 리프레쉬 토큰 저장
        saveRefreshToken(user.getId(), refreshtoken);
        //쿠키에 리프레쉬 토큰 저장
        addRefreshTokenToCookie(request, response, refreshtoken);


        //액세스 토큰 생성
        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
//        addAccessTokenToCookie(request, response, accessToken);


        //인증 관련 설정값, 쿠키 제거
        clearAuthenticationAttributes(request, response);

        //
        if (user.getHp() == null) {
            String targetUrl = getTargetUrl1(accessToken);
            getRedirectStrategy().sendRedirect(request, response, targetUrl);

        }else {
            String targetUrl = getTargetUrl2(accessToken);
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }


        //리다이렉트
//        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }


    //생성된 리프레시 토큰을 전달받아 db에 저장
    private void saveRefreshToken(Long userId, String newRefreshToken){
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity->entity.update(newRefreshToken))
                .orElse(new RefreshToken(userId, newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }

    //생성된 리프레시 토큰을 쿠키에 저장
    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken){
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }

    private void addAccessTokenToCookie(HttpServletRequest request, HttpServletResponse response, String accessToken){
        int cookieMaxAge = (int) ACCESS_TOKEN_DURATION.toSeconds();
        CookieUtil.deleteCookie(request, response, ACCESS_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, ACCESS_TOKEN_COOKIE_NAME, accessToken, cookieMaxAge);
    }

    //인증 관련 설정값, 쿠키 제거
    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    //before
//    private String getTargetUrl(String token){
//        return UriComponentsBuilder.fromUriString(REDIRECT_PATH_2)
//                .queryParam("token", token)
//                .build()
//                .toUriString();
//    }

    //test
    private String getTargetUrl1(String token){
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH_1)
                .queryParam("token", token)
                .build()
                .toUriString();
    }

    private String getTargetUrl2(String token){
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH_2)
                .queryParam("token", token)
                .build()
                .toUriString();
    }



}
