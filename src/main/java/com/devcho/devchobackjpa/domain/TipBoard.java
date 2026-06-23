package com.devcho.devchobackjpa.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="tip_board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TipBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private UserInfo creator;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updater_id")
    private UserInfo updater;
    private LocalDateTime updatedAt;

    @Builder
    public TipBoard(Long id, String title, String content, UserInfo creator, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creator = creator;
        this.createdAt = LocalDateTime.now();
    }

    public void update(String title, String content, UserInfo updater) {
        this.title = title;
        this.content = content;
        this.updater = updater;
        this.updatedAt = LocalDateTime.now();
    }
}
