package com.codestates.member;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/members")
public class MemberController {
    @PostMapping
    public ResponseEntity postMember(@RequestParam("email") String email,
                                     @RequestParam("name") String name,
                                     @RequestParam("phone") String phone,
                                     @RequestParam("memberId") long memberId) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("name", name);
        map.put("phone", phone);
        map.put("memberId", memberId);

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") long memberId) {
        System.out.println("# memberId: " + memberId);

        // not implementation
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers() {
        System.out.println("# get Members");

        // not implementation

        return new ResponseEntity<>(HttpStatus.OK);
    }
    //---------------- 여기서 부터 아래에 코드를 구현하세요! -------------------//
    // 1. 회원 정보 수정을 위한 핸들러 메서드 구현
    // 2. 회원 정보 삭제를 위한 핸들러 메서드 구현

    // 회원정보 수정
    @PatchMapping("/{memberId}")
    public ResponseEntity patchMember(@RequestParam("email") String email,
                                      @RequestParam("name") String name,
                                      @RequestParam("phone") String phone,
                                      @RequestParam("memberId") long memberId) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("name", name);
        map.put("phone", phone);
        map.put("memberId",memberId);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // 회원정보 삭제
    @DeleteMapping("/{memberId}")
    public ResponseEntity deleteMember(@PathVariable("memberId") long memberId) {
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
