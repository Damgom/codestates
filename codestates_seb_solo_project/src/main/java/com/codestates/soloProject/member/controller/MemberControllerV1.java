package com.codestates.soloProject.member.controller;

import com.codestates.soloProject.dto.MultiResponseDto;
import com.codestates.soloProject.dto.SingleResponseDto;
import com.codestates.soloProject.member.dto.MemberDto;
import com.codestates.soloProject.member.entity.Member;
import com.codestates.soloProject.member.mapper.MemberMapper;
import com.codestates.soloProject.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/members")
@Slf4j
@Validated
public class MemberControllerV1 {
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    public MemberControllerV1(MemberService memberService, MemberMapper memberMapper) {
        this.memberService = memberService;
        this.memberMapper = memberMapper;
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") long memberId) {
        Member member = memberService.findMember(memberId);
        return new ResponseEntity<>(
                new SingleResponseDto<>(memberMapper.memberToMemberResponseDto(member)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers(@RequestParam int page,
                                     @RequestParam int size) {
        Page<Member> pageMembers = memberService.findMembers(page - 1, size);
        List<Member> members = pageMembers.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(memberMapper.membersToMemberResponses(members), pageMembers), HttpStatus.OK);
    }

}
