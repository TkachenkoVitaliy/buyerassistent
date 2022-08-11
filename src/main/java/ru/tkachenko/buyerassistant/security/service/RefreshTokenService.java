package ru.tkachenko.buyerassistant.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tkachenko.buyerassistant.security.entity.RefreshToken;
import ru.tkachenko.buyerassistant.security.exception.TokenRefreshException;
import ru.tkachenko.buyerassistant.security.repository.RefreshTokenRepository;
import ru.tkachenko.buyerassistant.security.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${tkachenko.buyerassistant.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Transactional
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            if(refreshTokenRepository.existsById(token.getId())) {
                System.out.println(refreshTokenRepository.existsById(token.getId()));
                refreshTokenRepository.delete(token);
                throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
            }
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
}
