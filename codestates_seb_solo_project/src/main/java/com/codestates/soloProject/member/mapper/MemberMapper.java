package com.codestates.soloProject.member.mapper;

import com.codestates.soloProject.member.dto.MemberDto;
import com.codestates.soloProject.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    MemberDto memberToMemberResponseDto(Member member);
    List<MemberDto> membersToMemberResponses(List<Member> members);
}
