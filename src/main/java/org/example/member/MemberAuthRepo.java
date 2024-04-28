package org.example.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberAuthRepo extends JpaRepository<MemberAuthEntity,Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM  member_auth WHERE username = ?1 and is_deleted = false")
    MemberAuthEntity findOneByUsername(String userName);
}
