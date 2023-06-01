package com.ChatKarlo.ChatKarlo.repository;

import com.ChatKarlo.ChatKarlo.entity.Prompt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromptRepository extends JpaRepository<Prompt, Long> {
    Prompt findByMember_Id(Long memberId);
}
