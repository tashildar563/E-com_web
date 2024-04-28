package org.example.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM  member WHERE formatted_id = ?1 and is_deleted = false")
    MemberEntity findByFormattedId(String formattedId);
}
