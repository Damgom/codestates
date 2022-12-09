package com.codestates.soloProject.member.repository;

import com.codestates.soloProject.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "SELECT m FROM Member m WHERE m.companyType = :typeCode and m.companyLocation =:locationCode")
    List<Member> findType(String typeCode);

    @Query(value = "SELECT m FROM Member m WHERE m.companyType = :typeCode and m.companyLocation =:locationCode")
    List<Member> findLocation(String locationCode);
}
