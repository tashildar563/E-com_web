package org.example.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    @Autowired
    MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/member/add")
    public ResponseEntity<String> addMember(@RequestBody String memberDetails) throws JsonProcessingException {
        try {
            memberService.addMember(memberDetails);
            return new ResponseEntity<>("Member added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid Json Formate", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String memberDetails) {
        try {
            return memberService.validateMemberAuth(memberDetails);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid Json Formate", HttpStatus.BAD_REQUEST);
        }
    }

}
