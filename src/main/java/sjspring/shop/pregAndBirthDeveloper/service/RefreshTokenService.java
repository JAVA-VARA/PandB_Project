package sjspring.shop.pregAndBirthDeveloper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sjspring.shop.pregAndBirthDeveloper.domain.RefreshToken;
import sjspring.shop.pregAndBirthDeveloper.repository.RefreshTokenRepository;

//service 디렉터리에 RefreshTokenService 파일을 새로 만들어 전달받은 리프레시 토큰으로
//리프레시 토큰 객체를 검색해서 전달하는 findByRefreshToken() 메서드 구현

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new IllegalArgumentException("Unexpected token"));
    }
}
