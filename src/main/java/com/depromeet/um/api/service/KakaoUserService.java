package com.depromeet.um.api.service;

import com.depromeet.um.api.model.KakaoUser;
import com.depromeet.um.api.repository.KakaoUserRepository;
import org.springframework.stereotype.Service;

@Service
public class KakaoUserService {
    private final KakaoUserRepository kakaoUserRepository;

    public KakaoUserService(KakaoUserRepository kakaoUserRepository) {
        this.kakaoUserRepository = kakaoUserRepository;
    }

    public void save(KakaoUser kakaoUser) {
        kakaoUserRepository.save(kakaoUser);
    }

    public KakaoUser findByKakaoId(String kakaoId) {
        return kakaoUserRepository.findById(kakaoId)
                .orElseThrow(IllegalAccessError::new);
    }

    public boolean existsByKakaoId(String kakaoId) {
        return kakaoUserRepository.existsById(kakaoId);
    }
}
