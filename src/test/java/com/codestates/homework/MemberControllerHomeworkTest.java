package com.codestates.homework;

import com.codestates.member.dto.MemberDto;
import com.codestates.member.dto.MemberPatchDto;
import com.codestates.member.entity.Member;
import com.codestates.member.repository.MemberRepository;
import com.codestates.member.service.MemberService;
import com.codestates.slice.repository.member.MemberRepositoryTest;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Autowired
    private MemberService memberService;

    @Test
    void postMemberTest() throws Exception {
        // given
        MemberDto.Post post = new MemberDto.Post("hgd@gmail.com",
                "홍길동",
                "010-1234-5678");
        String content = gson.toJson(post);


        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/v11/members")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        // then
        MvcResult result = actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.email").value(post.getEmail()))
                .andExpect(jsonPath("$.data.name").value(post.getName()))
                .andExpect(jsonPath("$.data.phone").value(post.getPhone()))
                .andReturn();

//        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void patchMemberTest() throws Exception {
        MemberDto.Post post = new MemberDto.Post("hgd@gmail.com",
                "홍길동",
                "010-1234-5678");
        String postContent = gson.toJson(post);
        mockMvc.perform(
                post("/v11/members")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postContent)
        );
        // TODO MemberController의 patchMember() 핸들러 메서드를 테스트하는 테스트 케이스를 여기에 작성하세요.

        MemberDto.Patch patch = new MemberDto.Patch(1L, "홍길동"
                , "010-1234-5678", Member.MemberStatus.MEMBER_SLEEP);
        String content = gson.toJson(patch);

        ResultActions actions =
                mockMvc.perform(
                        patch("/v11/members/" + patch.getMemberId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        MvcResult result = actions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.data.memberId").value(patch.getMemberId()))
                .andExpect(jsonPath("$.data.name").value(patch.getName()))
                .andExpect(jsonPath("$.data.phone").value(patch.getPhone()))
                .andExpect(jsonPath("$.data.memberStatus").value(patch.getMemberStatus().getStatus()))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

    }

    @Test
    void getMemberTest() throws Exception {
        MemberDto.Post getPost = new MemberDto.Post("hgd@gmail.com",
                "홍길동",
                "010-1234-5678");
        String postContent = gson.toJson(getPost);
        mockMvc.perform(
                post("/v11/members")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postContent)
        );
        // TODO MemberController의 getMember() 핸들러 메서드를 테스트하는 테스트 케이스를 여기에 작성하세요.
        Member member = memberService.findMember(1L);

        // Controller GetMapping
        ResultActions actions = mockMvc.perform(
                get("/v11/members/" + member.getMemberId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        MvcResult result = actions
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        System.out.println("response : " + result.getResponse().getContentAsString());
    }

    @Test
    void getMembersTest() throws Exception {
        MemberDto.Post post1 = new MemberDto.Post("11111@gmail.com",
                "11111",
                "010-1111-1111");
        MemberDto.Post post2 = new MemberDto.Post("22222@gmail.com",
                "22222",
                "010-2222-2222");
        MemberDto.Post post3 = new MemberDto.Post("33333@gmail.com",
                "33333",
                "010-3333-3333");

        String postContent1 = gson.toJson(post1);
        String postContent2 = gson.toJson(post2);
        String postContent3 = gson.toJson(post3);
        // TODO MemberController의 getMembers() 핸들러 메서드를 테스트하는 테스트 케이스를 여기에 작성하세요.
        mockMvc.perform(
                post("/v11/members")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postContent1)
        );

        mockMvc.perform(
                post("/v11/members")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postContent2)
        );

        mockMvc.perform(
                post("/v11/members")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postContent3)
        );

        ResultActions actions = mockMvc.perform(
                get("/v11/members?page=1&size=4")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        MvcResult result = actions
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void deleteMemberTest() throws Exception {
        MemberDto.Post deletePost = new MemberDto.Post("hgd@gmail.com",
                "홍길동",
                "010-1234-5678");
        String postContent = gson.toJson(deletePost);
        mockMvc.perform(
                delete("/v11/members")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postContent)
        );
        // TODO MemberController의 deleteMember() 핸들러 메서드를 테스트하는 테스트 케이스를 여기에 작성하세요.
        Member member = memberService.findMember(1L);

        // DeleteMapping
        ResultActions actions = mockMvc.perform(
                delete("/v11/members/" + member.getMemberId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        MvcResult result = actions
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }
}
