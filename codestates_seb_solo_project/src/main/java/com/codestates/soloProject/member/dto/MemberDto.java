package com.codestates.soloProject.member.dto;

import com.codestates.soloProject.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class MemberDto {

    @AllArgsConstructor
    @Getter
    public static class Response{
        private Long memberId;
        private String name;
        private String password;
        private String sex;
        private String company_name;
        private String company_type;
        private String company_location;
        private Member.MemberStatus memberStatus;

        public String getMemberStatus(){
            return memberStatus.getStatus();
        }
    }
}
