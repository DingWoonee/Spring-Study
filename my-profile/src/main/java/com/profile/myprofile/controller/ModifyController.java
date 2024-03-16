package com.profile.myprofile.controller;

import com.profile.myprofile.domain.Member;
import com.profile.myprofile.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ModifyController {
    private final MemberService memberService;

    @Autowired
    public ModifyController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/modify")
    public String modify() {
        return "modify";
    }

    @PostMapping("/modify")
    public String save(Member member) {
        memberService.addInfo(member);
        return "redirect:/";
    }
}
