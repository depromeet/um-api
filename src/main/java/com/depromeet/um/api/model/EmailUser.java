package com.depromeet.um.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class EmailUser {
    protected EmailUser() {}
    /**
     * User entity 의 email 와 매핑된다.
     */
    @Id
    @Column
    @NotNull
    private String email;
    @Column
    @NotNull
    private String password;
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = CASCADE)
    @JoinColumn(updatable = false)
    @NotNull
    private User user;
}
