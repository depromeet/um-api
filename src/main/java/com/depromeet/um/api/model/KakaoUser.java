package com.depromeet.um.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class KakaoUser {
    protected KakaoUser() {
    }

    /**
     * 카카오 - 앱 사이의 부여되는 고유한 id
     */
    @Id
    @NotNull
    private String kakaoId;

    @NotNull
    private String kakaoEmail;

    @NotNull
    @Setter
    private String lastAccessToken;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = CASCADE)
    @JoinColumn(updatable = false)
    @NotNull
    private User user;
}