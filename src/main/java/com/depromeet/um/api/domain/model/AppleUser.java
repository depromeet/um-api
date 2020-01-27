package com.depromeet.um.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class AppleUser {
    /**
     * Apple 에서 제공해주는 unique Id
     */
    @Id
    @NotNull
    private String appleId;

    @NotNull
    private String appleEmail;

    @NotNull
    @Setter
    private String lastAccessToken;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = CASCADE)
    @JoinColumn(updatable = false)
    @NotNull
    private User user;
}
