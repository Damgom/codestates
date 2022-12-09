package com.codestates.soloProject.projectTest;

import com.codestates.soloProject.member.dto.MemberDto;
import com.codestates.soloProject.member.entity.Member;
import com.codestates.soloProject.member.mapper.MemberMapper;

import com.codestates.soloProject.member.service.MemberService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class memberRepositoryTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MemberService memberService;
    @MockBean
    private MemberMapper memberMapper;

    @Autowired
    private Gson gson;

    @Test
    void getMemberTest() throws Exception{
        long memberId = 1L;
        Member member = new Member("김코딩", "s4goodbye!", "m", "코드스테이츠", "005", "001");
        member.setMemberId(memberId);
        member.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);

        MemberDto.Response response = new MemberDto.Response(1L, "김코딩", "s4goodbye!", "m", "코드스테이츠", "005", "001",
                Member.MemberStatus.MEMBER_ACTIVE);

        given(memberService.findMember(Mockito.anyLong())).willReturn(new Member());
        given(memberMapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(response);

        URI uri = UriComponentsBuilder.newInstance().path("/v1/members/{memberId}").buildAndExpand(memberId).toUri();

        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(uri)
                        .accept(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(member.getName()))
                .andExpect(jsonPath("$.data.password").value(member.getPassword()))
                .andExpect(jsonPath("$.data.sex").value(member.getSex()))
                .andExpect(jsonPath("$.data.company_name").value(member.getCompany_name()))
                .andExpect(jsonPath("$.data.company_type").value(member.getCompany_type()))
                .andExpect(jsonPath("$.data.company_location").value(member.getCompany_location()))
                .andExpect(jsonPath("$.data.memberStatus").value(member.getMemberStatus()));
    }

}
