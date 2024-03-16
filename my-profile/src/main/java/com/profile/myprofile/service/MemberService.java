package com.profile.myprofile.service;

import com.profile.myprofile.domain.Member;
import com.profile.myprofile.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void addInfo(Member member) {
        Member preMember = memberRepository.findByName(member.getName());
        if (preMember == null) {
            memberRepository.save(member);
        } else {
            preMember.setAge(member.getAge());
            preMember.setGender(member.getGender());
            preMember.setPhone(member.getPhone());
            memberRepository.save(preMember);
        }
    }

    public List<Member> find() {
        List<Member> memberList = memberRepository.findAll();
        if (memberList.isEmpty())
            return null;
        return memberList;
    }
}
