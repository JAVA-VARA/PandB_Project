package sjspring.shop.pregAndBirthDeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sjspring.shop.pregAndBirthDeveloper.dto.CreateAccessTokenRequest;
import sjspring.shop.pregAndBirthDeveloper.dto.CreateAccessTokenResponse;
import sjspring.shop.pregAndBirthDeveloper.service.TokenService;

//토큰 서비스에서 refresh token을 기반으로 새로운 액세스 토큰을 만들어 준다.
@RequiredArgsConstructor
@RestController
public class TokenApiController {

    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }
}
