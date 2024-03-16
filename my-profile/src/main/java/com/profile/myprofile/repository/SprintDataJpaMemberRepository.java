package com.profile.myprofile.repository;

import com.profile.myprofile.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
}
