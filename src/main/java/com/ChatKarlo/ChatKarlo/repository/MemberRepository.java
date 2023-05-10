package com.ChatKarlo.ChatKarlo.repository;

import com.ChatKarlo.ChatKarlo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
