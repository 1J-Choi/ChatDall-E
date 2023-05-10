package com.ChatKarlo.ChatKarlo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "prompt")
@Getter
@Setter
public class Prompt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "prompt_id")
    private Long id;

    // 입력받은 자연어 문장
    @Nullable
    private String beforeWords;

    // 추출된 제시어
    @Nullable
    private String afterWords;

    // 생성된 사진의 Base64 인코딩
    @Nullable
    private String img;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
