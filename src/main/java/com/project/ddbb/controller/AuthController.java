package com.project.ddbb.controller;

import com.project.ddbb.domain.service.BoardService;
import com.project.ddbb.domain.service.MemberService;
import com.project.ddbb.domain.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


@Controller
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    /**
     * 로그인 화면
     * @return
     */
    @GetMapping("/signIn")
    public String signIn() {

        return "auth/sign_in";
    }

    /**
     * 로그인 처리
     * @return
     */
    @PostMapping("/signIn")
    public String signInProcess(MemberVO memberVO) throws Exception{


//         userid와 password 검증 로직
        if (memberVO.getId() != null && memberVO.getPassword() != null && !memberVO.getId().isEmpty() && !memberVO.getPassword().isEmpty()) {
//             로그인 성공
            if (memberService.accountPermitId(memberVO.getId()) && memberService.accountPermitPw(memberService.PassWordEncrypt(memberVO.getPassword()))){
                System.out.println("Success");
                return "redirect:/project/home";
            } else {

                System.out.println("Fail");
                return "redirect:/signIn";
            }
        } else {
            // 로그인 실패
            System.out.println("Fail2");
            return "redirect:/signIn";
        }
    }

    /**
     * 회원가입 화면
     * @return
     */
    @GetMapping("/signUp")
    public String signUp() {

        return "auth/sign_up";
    }

    /**
     * 회원가입 처리
     * @return
     */
    @PostMapping("/signUp")
    public String signUpProcess(MemberVO memberVO) throws Exception {
        if (memberVO.getEmail() != null && !memberVO.getEmail().isEmpty()){
            //memberVO.getId() != null && memberVO.getPassword() != null && memberVO.getName() != null && memberVO.getEmail() != null
            //&& !memberVO.getId().isEmpty() && !memberVO.getPassword().isEmpty() && memberVO.getName().isEmpty() && memberVO.getEmail().isEmpty()) {
            memberVO.setPassword(memberService.PassWordEncrypt(memberVO.getPassword()));
            memberService.SignUp(memberVO);
            return "auth/sign_up_complete";
        } else {
            return "auth/sign_up";
        }

    }

    /**
     * ID 유효성 검증
     * @return
     */
    @PostMapping("/idValidation")
    public boolean idValidation() {

        return true;
    }
}
