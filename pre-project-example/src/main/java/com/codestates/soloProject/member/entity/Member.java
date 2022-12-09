package com.codestates.soloProject.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member {
    @Id
    private Long memberId;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(length = 100, nullable = false)
    private String sex;
    @Column(nullable = false)
    private String company_name;
    @Column(nullable = false)
    private String company_type;
    @Column(nullable = false)
    private String company_location;
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    public Member(String name, String password, String sex, String company_name, String company_type, String company_location) {
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.company_name = company_name;
        this.company_type = company_type;
        this.company_location = company_location;
    }

    public enum MemberStatus {
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }
}
