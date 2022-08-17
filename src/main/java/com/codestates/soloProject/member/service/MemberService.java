package com.codestates.soloProject.member.service;

import com.codestates.soloProject.member.entity.Member;
import com.codestates.soloProject.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member findMember(long memberId) {
        return memberRepository.findById(memberId).get();
    }


    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size, Sort.by("memberId").descending()));
    }

    public List<Member> findMembersByLocation(String location){
        Optional<List<Member>> optionalMember = Optional.ofNullable(memberRepository.findLocation(location));

        List<Member> members = optionalMember.orElseThrow();

        return members;
    }

    public List<Member> findMembersByType(String type){
        Optional<List<Member>> optionalMember = Optional.ofNullable(memberRepository.findType(type));
        List<Member> members = optionalMember.orElseThrow();

        return members;
    }
}
