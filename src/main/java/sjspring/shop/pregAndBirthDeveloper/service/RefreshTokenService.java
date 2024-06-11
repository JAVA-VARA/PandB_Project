package sjspring.shop.pregAndBirthDeveloper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sjspring.shop.pregAndBirthDeveloper.domain.RefreshToken;
import sjspring.shop.pregAndBirthDeveloper.repository.RefreshTokenRepository;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new IllegalArgumentException("Unexpected token"));
    }
}
