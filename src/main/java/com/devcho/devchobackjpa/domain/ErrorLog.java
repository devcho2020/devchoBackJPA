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

    private LocalDateTime createdAt;

    @Builder
    public ErrorLog(String title, String area, String content) {
        this.title = title;
        this.area = area;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public void update(String title, String area, String content) {
        this.title = title;
        this.area = area;
        this.content = content;
    }
}
