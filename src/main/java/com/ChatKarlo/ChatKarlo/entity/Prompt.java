package com.ChatKarlo.ChatKarlo.entity;

import javax.persistence.*;

import com.ChatKarlo.ChatKarlo.repository.MemberRepository;
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
    private String beforeWords;

    // 번역 및 추출된 제시어
    private String afterWords;

    // 생성된 이미지의 url
    @Column(columnDefinition = "TEXT")
    private String url;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Prompt createPrompt(Member member){
        Prompt prompt = new Prompt();
        prompt.setMember(member);
        return prompt;
    }
}
