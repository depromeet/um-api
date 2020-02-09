package com.depromeet.um.api.domain;

import com.depromeet.um.api.domain.model.KakaoUser;
import com.depromeet.um.api.domain.repository.KakaoUserRepository;
import org.springframework.stereotype.Service;

@Service
public class KakaoUserService {
    private final KakaoUserRepository kakaoUserRepository;

    public KakaoUserService(KakaoUserRepository kakaoUserRepository) {
        this.kakaoUserRepository = kakaoUserRepository;
    }

    public KakaoUser save(KakaoUser kakaoUser) {
        return kakaoUserRepository.save(kakaoUser);
    }

    public KakaoUser findByKakaoId(String kakaoId) {
        return kakaoUserRepository.findById(kakaoId)
                .orElseThrow(IllegalArgumentException::new);
    }

    public boolean existsByKakaoId(String kakaoId) {
        return kakaoUserRepository.existsById(kakaoId);
    }
}
