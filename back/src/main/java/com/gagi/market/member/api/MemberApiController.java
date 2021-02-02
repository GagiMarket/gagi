package com.gagi.market.member.api;

import com.gagi.market.config.auth.LoginMember;
import com.gagi.market.member.api.dto.MemberRequestDto;
import com.gagi.market.member.api.dto.MemberLoginRequestDto;
import com.gagi.market.member.api.dto.MemberResponseDto;
import com.gagi.market.member.api.dto.SessionMember;
import com.gagi.market.member.domain.Member;
import com.gagi.market.member.service.LoginService;
import com.gagi.market.member.service.MemberService;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.gagi.market.member.api.MemberApiController.MEMBER_API_URI;
import static com.gagi.market.member.api.dto.MemberResponseDto.*;

@RestController
@RequestMapping(MEMBER_API_URI)
public class MemberApiController {
    public static final String MEMBER_API_URI = "/api/v1.0/members";

    private final MemberService memberService;
    private final LoginService loginService;

    public MemberApiController(MemberService memberService, LoginService loginService) {
        this.memberService = memberService;
        this.loginService = loginService;
    }

    /*
     * Login
     *
     */
    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login(@RequestBody @NonNull MemberLoginRequestDto loginRequestDto) {
        SessionMember sessionMember = loginService.login(loginRequestDto.getMemberEmail(), loginRequestDto.getMemberPw());
        if (sessionMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/duplicated/{memberEmail}")
    public ResponseEntity<HttpStatus> checkDuplicate(@PathVariable String memberEmail) {
        Member findMember = memberService.findMemberByMemberEmail(memberEmail);
        if (findMember != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/logout")
    public ResponseEntity<HttpStatus> logout(@LoginMember SessionMember member) {
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        loginService.logout();
        return ResponseEntity.ok().build();
    }

    /*
     * Member
     *
     */
    @PostMapping
    public ResponseEntity<HttpStatus> createMember(@RequestBody MemberRequestDto requestDto) {
        memberService.createMember(requestDto.toEntity());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{memberEmail}")
    public ResponseEntity<MemberResponseDto> findMemberByMemberEmail(@PathVariable String memberEmail) {
        MemberResponseDto findMember = of(memberService.findMemberByMemberEmail(memberEmail));
        return ResponseEntity
                .ok()
                .body(findMember);
    }

    @PutMapping("/{memberEmail}")
    public ResponseEntity<HttpStatus> updateMember(@LoginMember SessionMember member,
                                                   @PathVariable String memberEmail,
                                                   @RequestBody MemberRequestDto requestDto) {
        memberService.updateMember(memberEmail, requestDto.toEntity());
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity
                .created(URI.create(MEMBER_API_URI + "/" + memberEmail))
                .build();
    }

    @DeleteMapping("/{memberEmail}")
    public ResponseEntity<HttpStatus> deleteMember(@LoginMember SessionMember member,
                                                   @PathVariable String memberEmail) {
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        memberService.deleteMemberByMemberEmail(memberEmail);
        return ResponseEntity.ok().build();
    }
}
