package org.example.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.actor.ActorEntity;
import org.example.actor.ActorService;
import org.example.emailUtility.EmailUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ActorService actorService;
    private final MemberAuthRepo memberAuthRepo;
    private final EmailUtility emailUtility;

    public MemberService(MemberRepository memberRepository, ActorService actorService, MemberAuthRepo memberAuthRepo, EmailUtility emailUtility) {
        this.memberRepository = memberRepository;
        this.actorService = actorService;
        this.memberAuthRepo = memberAuthRepo;
        this.emailUtility = emailUtility;
    }

@Transactional
    public void addMember(String memberDetails) throws JsonProcessingException {
    Map<String, String> member = getStringStringMap(memberDetails);
    if (memberDetails == null || memberDetails.isEmpty() || null==member.get("formattedId") || member.get("formattedId").isEmpty()) {
            throw new IllegalArgumentException("Invalid Json Format");
        } else {
            ActorEntity actorEntity = actorService.createMemberActor();
            MemberEntity memberEntityExisted = memberRepository.findByFormattedId(member.get("formattedId"));
            if (actorEntity == null || memberEntityExisted != null) {
                throw new IllegalArgumentException("Actor not created");
            } else {
                MemberEntity memberEntity = new MemberEntity();
                memberEntity.setSalutation(member.get("salutation"));
                memberEntity.setFirstName(member.get("firstName"));
                memberEntity.setMiddleName(member.get("middleName"));
                memberEntity.setLastName(member.get("lastName"));
                memberEntity.setGender(member.get("gender"));
                String dob = member.get("dob");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(dob, formatter);
                LocalDateTime dateTime = date.atStartOfDay();
                memberEntity.setDob(dateTime);
                memberEntity.setFormattedId(member.get("formattedId"));
                memberEntity.setCreatedBy("System");
                memberEntity.setCreatedOn(LocalDateTime.now());
                memberEntity.setUpdatedBy("System");
                memberEntity.setUpdatedOn(LocalDateTime.now());
                memberEntity.setActorId(actorEntity.getId());
                memberEntity.setType(member.get("type"));
                memberEntity.setStatus("ACTIVE");
                memberEntity.setDeleted(false);
                memberEntity=memberRepository.save(memberEntity);
                createMemberAuth(memberEntity);
            }
        }
    }

    public static Map<String, String> getStringStringMap(String memberDetails) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> member = objectMapper.readValue(memberDetails, Map.class);
        return member;
    }

    public void createMemberAuth(MemberEntity memberEntity) {
        String firstFourCharacters = memberEntity.getFirstName().substring(0, 4).toUpperCase();
        MemberAuthEntity memberAuth = new MemberAuthEntity();
        memberAuth.setUsername(memberEntity.getFormattedId());
        memberAuth.setPassword(firstFourCharacters);
        memberAuth.setMemberId(memberEntity.getId());
        memberAuth.setCreatedBy("System");
        memberAuth.setCreatedOn(LocalDateTime.now());
        memberAuth.setUpdatedBy("System");
        memberAuth.setUpdatedOn(LocalDateTime.now());
        memberAuth.setDeleted(false);
        memberAuthRepo.save(memberAuth);
        StringBuilder body = new StringBuilder();
        body.append("Member added successfully >> username: ").append(memberEntity.getFormattedId()).append(" password: ").append(firstFourCharacters);
        emailUtility.sendEmail("arc563@outlook.com", "Member Added", body.toString());
    }

    public ResponseEntity<String> validateMemberAuth(String memberDetails) throws Exception {
        Map<String, String> memberInput = getStringStringMap(memberDetails);
        String userName = memberInput.get("username");
        String password = memberInput.get("password");
        MemberAuthEntity memberAuth = memberAuthRepo.findOneByUsername(userName);
        if(password.equals(memberAuth.getPassword())){
            String successMessage = "Log in completed successfully";
            return ResponseEntity.ok(successMessage);
        }else{
            throw new Exception("Wrong username or password");
        }

    }
}
