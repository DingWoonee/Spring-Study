package com.profile.myprofile.controller;

import com.profile.myprofile.domain.Member;
import com.profile.myprofile.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final MemberService memberService;

    @Autowired
    public HomeController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Member> member = memberService.find();
        if (member == null) {
            return "redirect:/modify";
        }
        model.addAttribute("infos", member);
        return "home";
    }
}
