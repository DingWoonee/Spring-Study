package com.profile.myprofile.repository;

import com.profile.myprofile.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    List<Member> findAll();
    Member findByName(String name);
    Optional<Member> findByUserId(String userId);
}
