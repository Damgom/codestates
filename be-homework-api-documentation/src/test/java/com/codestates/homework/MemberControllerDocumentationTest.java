package com.codestates.homework;

import com.codestates.helper.MemberControllerTestHelper;
import com.codestates.member.controller.MemberController;
import com.codestates.member.dto.MemberDto;
import com.codestates.member.entity.Member;
import com.codestates.member.mapper.MemberMapper;
import com.codestates.member.service.MemberService;
import com.codestates.stamp.Stamp;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static com.codestates.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MemberControllerDocumentationTest implements MemberControllerTestHelper{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper mapper;

    @Autowired
    private Gson gson;

    @Test
    public void getMemberTest() throws Exception {
        // TODO 여기에 MemberController의 getMember() 핸들러 메서드 API 스펙 정보를 포함하는 테스트 케이스를 작성 하세요.
        Member member = new Member("hgd@gmailcom", "TestNum1", "010-1234-9999");
        member.setMemberId(1L);
        member.setStamp(new Stamp());
        long memberId = member.getMemberId();

        MemberDto.Response responseDto =
                new MemberDto.Response(1L,
                        "hgd@gmail.com",
                        "홍길동",
                        "010-1234-1234",
                        Member.MemberStatus.MEMBER_ACTIVE,
                        new Stamp());

        given(memberService.findMember(memberId)).willReturn(member);
        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(responseDto);

        ResultActions actions = mockMvc.perform(
                get("/v11/members/{member-id}", memberId)
                        .accept(MediaType.APPLICATION_JSON)
        );
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(member.getMemberId()))
                .andDo(document(
                        "get-member",
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("member-id").description("회원 식별자")
                                ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data.phone").type(JsonFieldType.STRING).description("휴대폰 번호"),
                                        fieldWithPath("data.memberStatus").type(JsonFieldType.STRING).description("회원 상태"),
                                        fieldWithPath("data.stamp").type(JsonFieldType.NUMBER).description("스탬프 갯수")
                                )
                        )
                ));
    }

    @Test
    public void getMembersTest() throws Exception {
        // TODO 여기에 MemberController의 getMembers() 핸들러 메서드 API 스펙 정보를 포함하는 테스트 케이스를 작성 하세요.
        Member member1 = new Member("test1@gmail.com", "테스터1", "010-1234-9999");
        member1.setStamp(new Stamp());

        Member member2 = new Member("test2@gmail.com", "테스터2", "010-1234-8888");
        member2.setStamp(new Stamp());

        List<Member> listMembers = new ArrayList<>(List.of(member1, member2));
        Page<Member> pageMembers = new PageImpl<>(listMembers);

        given(memberService.findMembers(0, 10))
                .willReturn(pageMembers);

        MemberDto.Response responseDto1 =
                new MemberDto.Response(1L,
                        "hgd@gmail.com",
                        "홍길동",
                        "010-1234-1234",
                        Member.MemberStatus.MEMBER_ACTIVE,
                        new Stamp());

        MemberDto.Response responseDto2 =
                new MemberDto.Response(2L,
                        "gnd@gmail.com",
                        "가나다",
                        "010-1234-7890",
                        Member.MemberStatus.MEMBER_ACTIVE,
                        new Stamp());

        List<MemberDto.Response> listResponseDtos = new ArrayList<>(List.of(responseDto1, responseDto2));
        given(mapper.membersToMemberResponses(anyList())).willReturn(listResponseDtos);

        ResultActions actions = mockMvc.perform(
                get("/v11/members")
                        .param("page", "1")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON)
        );

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("pageInfo.totalElements").value(2))
                .andDo(document(
                        "get-members",
                        getResponsePreProcessor(),
                        requestParameters(
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("페이지별 목록 수")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data[0]").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[0].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data[0].email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("data[0].name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data[0].phone").type(JsonFieldType.STRING).description("휴대폰 번호"),
                                        fieldWithPath("data[0].memberStatus").type(JsonFieldType.STRING).description("회원 상태"),
                                        fieldWithPath("data[0].stamp").type(JsonFieldType.NUMBER).description("스탬프 갯수"),
                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 관련 데이터"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지별 목록 수"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 목록 수"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                                )
                        )
                ));
    }

    @Test
    public void deleteMemberTest() throws Exception {
        // TODO 여기에 MemberController의 deleteMember() 핸들러 메서드 API 스펙 정보를 포함하는 테스트 케이스를 작성 하세요.
        long memberId = 1L;
        doNothing().when(memberService).deleteMember(memberId);

        ResultActions actions = mockMvc.perform(
                delete("/v11/members/{member-id}", memberId)
        );

        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-member",
                        pathParameters(
                                parameterWithName("member-id").description("회원 식별자")
                        )));
    }
}
