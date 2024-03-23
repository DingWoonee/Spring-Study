package com.profile.myprofile.controller;

import com.profile.myprofile.domain.Member;
import com.profile.myprofile.domain.MemberForModify;
import com.profile.myprofile.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
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
    public String save(
            HttpServletRequest request,
            MemberForModify memberForModify
    ) {
        String ipAddress = getCliendIp(request);
        Member oldMember = memberService.findByUserId(memberService.getLoggedInUserId(ipAddress));
        if (oldMember == null) {
            return "redirect:/login";
        }
        oldMember.setName(memberForModify.getName());
        oldMember.setAge(memberForModify.getAge());
        oldMember.setGender(memberForModify.getGender());
        oldMember.setPhone(memberForModify.getPhone());
        memberService.addInfo(oldMember);
        return "redirect:/";
    }

    private String getCliendIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
