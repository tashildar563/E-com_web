package org.example.actor;

import org.example.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<ActorEntity,Long> {
}
