package com.codestates.homework;

import com.codestates.member.dto.MemberDto;
import com.codestates.member.entity.Member;
import com.codestates.member.mapper.MemberMapper;
import com.codestates.member.service.MemberService;
import com.codestates.stamp.Stamp;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerHomeworkTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private MemberService memberService;

    @Autowired
    private MemberMapper mapper;

    @Test
    void postMemberTest() throws Exception {
        // TODO MemberController의 postMember() 핸들러 메서드를 테스트하는 테스트 케이스를 여기에 작성하세요.
        // TODO Mockito를 사용해야 합니다. ^^


        MemberDto.Post post = new MemberDto.Post("hgd@gmail.com",
                "홍길동",
                "010-1234-5678");

        Member member = mapper.memberPostToMember(post);
        member.setStamp(new Stamp());

        given(memberService.createMember(Mockito.any(Member.class))).willReturn(member);

        String content = gson.toJson(post);


        ResultActions actions = mockMvc.perform(post("/v11/members")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));


        MvcResult result = actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.email").value(post.getEmail()))
                .andExpect(jsonPath("$.data.name").value(post.getName()))
                .andExpect(jsonPath("$.data.phone").value(post.getPhone()))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void patchMemberTest() throws Exception {
        MemberDto.Patch patch = new MemberDto.Patch(1L,
                "홍길동",
                "010-1234-5678",
                Member.MemberStatus.MEMBER_ACTIVE);

        Member member = mapper.memberPatchToMember(patch);
        member.setStamp(new Stamp());

        given(memberService.updateMember(Mockito.any(Member.class))).willReturn(member);

        String content = gson.toJson(patch);


        ResultActions actions =
                mockMvc.perform(
                        patch("/v11/members/" + member.getMemberId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );


        MvcResult result = actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(patch.getMemberId()))
                .andExpect(jsonPath("$.data.name").value(patch.getName()))
                .andExpect(jsonPath("$.data.phone").value(patch.getPhone()))
                .andExpect(jsonPath("$.data.memberStatus").value(patch.getMemberStatus().getStatus()))
                .andReturn();
    }

    @Test
    void getMemberTest() throws Exception {
        // TODO MemberController의 getMember() 핸들러 메서드를 테스트하는 테스트 케이스를 여기에 작성하세요.
        // TODO Mockito를 사용해야 합니다. ^^
        Member member = new Member("hgd@gmail.com",
                "홍길동",
                "010-1234-5678");
        member.setMemberId(1L);
        member.setStamp(new Stamp());

        given(memberService.findMember(anyLong())).willReturn(member);

        String content = gson.toJson(member.getMemberId());

        ResultActions actions =
                mockMvc.perform(
                        get("/v11/members/" + member.getMemberId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        MvcResult result = actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(member.getMemberId()))
                .andExpect(jsonPath("$.data.name").value(member.getName()))
                .andExpect(jsonPath("$.data.email").value(member.getEmail()))
                .andExpect(jsonPath("$.data.phone").value(member.getPhone()))
                .andExpect(jsonPath("$.data.memberStatus").value(member.getMemberStatus().getStatus()))
                .andReturn();
    }

    @Test
    void getMembersTest() throws Exception {
        // TODO MemberController의 getMembers() 핸들러 메서드를 테스트하는 테스트 케이스를 여기에 작성하세요.
        // TODO Mockito를 사용해야 합니다. ^^
        Member member1 = new Member("hgd@gmail.com",
                "홍길동",
                "010-1234-5678");
        member1.setMemberId(1L);
        member1.setStamp(new Stamp());
        Member member2 = new Member("gnd@gmail.com",
                "가나다",
                "010-1111-2222");
        member2.setMemberId(2L);
        member2.setStamp(new Stamp());
        int page = 2;
        int size = 1;
        Page<Member> pageImpl = new PageImpl<>(List.of(member2), PageRequest.of(page - 1, size), 1);


        given(memberService.findMembers(anyInt(), anyInt())).willReturn(pageImpl);

        ResultActions actions =
                mockMvc.perform(
                        get("/v11/members")
                                .param("page", String.valueOf(page))
                                .param("size", String.valueOf(size))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                );
        MvcResult result = actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageInfo.page").value(page))
                .andExpect(jsonPath("$.pageInfo.size").value(size))
                .andExpect(jsonPath("$.data..name").value(member2.getName()))
                .andExpect(jsonPath("$.data..email").value(member2.getEmail()))
                .andExpect(jsonPath("$.data..phone").value(member2.getPhone()))
                .andReturn();
    }

    @Test
    void deleteMemberTest() throws Exception {
        // TODO MemberController의 deleteMember() 핸들러 메서드를 테스트하는 테스트 케이스를 여기에 작성하세요.
        // TODO Mockito를 사용해야 합니다. ^^
        Member member = new Member("hgd@gmail.com",
                "홍길동",
                "010-1234-5678");
        member.setMemberId(1L);
        member.setStamp(new Stamp());

        doNothing().when(memberService).deleteMember(anyLong());

        String content = gson.toJson(member.getMemberId());

        ResultActions actions =
                mockMvc.perform(
                        delete("/v11/members/" + member.getMemberId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        MvcResult result = actions
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
