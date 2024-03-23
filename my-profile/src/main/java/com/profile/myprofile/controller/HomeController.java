package com.profile.myprofile.controller;

import com.profile.myprofile.domain.Member;
import com.profile.myprofile.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private final MemberService memberService;

    @Autowired
    public HomeController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String home(
            Model model,
            HttpServletRequest request
    ) {
        String ipAddress = getCliendIp(request);
        if (!memberService.isLoggedIn(ipAddress)) {
            return "redirect:/login";
        }

        Member member = memberService.findByUserId(memberService.getLoggedInUserId(ipAddress));
        model.addAttribute("member", member);
        return "home";
    }

    @GetMapping("/login") // 로그인 화면
    public String login() {
        return "login";
    }

    @GetMapping("/sign-in") // 로그인 요청
    public String signIn(
            @RequestParam String userId,
            @RequestParam String pw,
            HttpServletRequest request
    ) {
        Member foundMember = memberService.findByUserId(userId);
        if (foundMember == null) {
            return "login";
        } else if (!foundMember.getPw().equals(pw)) {
            return "login";
        }
        String ipAddress = getCliendIp(request);
        memberService.setLoggedInUser(ipAddress, foundMember.getUserId());
        return "redirect:/";
    }

    @GetMapping("/sign-up") //회원가입 화면
    public String signUp() {
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpReq(Member member) {
        memberService.addInfo(member);
        return "login";
    }

    @GetMapping("/sign-out")
    public String signOut(HttpServletRequest request) {
        String ipAddress = getCliendIp(request);
        if (memberService.isLoggedIn(ipAddress)) {
            memberService.setLoggedOutUser(ipAddress, memberService.getLoggedInUserId(ipAddress));
        }
        return "login";
    }

    private String getCliendIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
