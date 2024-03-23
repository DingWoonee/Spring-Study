package com.profile.myprofile.service;

import com.profile.myprofile.domain.Member;
import com.profile.myprofile.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class MemberService {
    // ipAddress, userId
    private Map<String, String> loggedInUserMap = new HashMap<>();
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

    public Member findByUserId(String userId) {
        Optional<Member> memberOptional = memberRepository.findByUserId(userId);
        return memberOptional.orElse(null);
    }

    // 로그인 설정
    public void setLoggedInUser(String ipAddress, String userId) {
        loggedInUserMap.put(ipAddress, userId);
    }

    public void setLoggedOutUser(String ipAddress, String userId) {
        if (loggedInUserMap.containsKey(ipAddress)) {
            if (loggedInUserMap.get(ipAddress).equals(userId)) {
                loggedInUserMap.remove(ipAddress);
            }
        }
    }

    public boolean isLoggedIn(String ipAddress) {
        if (loggedInUserMap.containsKey(ipAddress)) {
            return true;
        }
        return false;
    }

    public String getLoggedInUserId(String ipAddress) {
        return loggedInUserMap.get(ipAddress);
    }
}
