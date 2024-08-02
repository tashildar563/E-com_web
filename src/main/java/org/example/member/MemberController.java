package org.example.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.emailUtility.EmailUtility;
import org.example.scanner.ScannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ScannerService scannerService;

    @Autowired
    private EmailUtility emailUtility;

    @PostMapping("/add")
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
            return new ResponseEntity<>("Invalid Json Format", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/scan")
    public ResponseEntity<Map<String, String>> scan(@RequestBody String barcode) {
        if (barcode == null || barcode.isEmpty()) {
            return new ResponseEntity<>(Map.of("error", "Barcode is required"), HttpStatus.BAD_REQUEST);
        }
        try {
            return scannerService.getBarcodeInfo(barcode);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(new HashMap<>(), HttpStatus.BAD_REQUEST);
        }
    }

}
