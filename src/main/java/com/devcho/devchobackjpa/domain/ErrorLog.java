package com.devcho.devchobackjpa.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "error_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED
)
public class ErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    private String area;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private UserInfo creator;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updater")
    private UserInfo updater;

    private LocalDateTime updatedAt;

    @Builder
    public ErrorLog(String title, String area, String content, UserInfo userInfo) {
        this.title = title;
        this.area = area;
        this.content = content;
        this.creator = userInfo;
        this.createdAt = LocalDateTime.now();
    }

    public void update(String title, String area, String content, UserInfo updater) {
        this.title = title;
        this.area = area;
        this.content = content;
        this.updater = updater;
        this.updatedAt = LocalDateTime.now();
    }
}
