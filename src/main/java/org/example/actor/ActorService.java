package org.example.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;
    @Transactional
    public ActorEntity createMemberActor() {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setType("MEMBER");
        actorEntity.setIsDeleted(false);
        return actorRepository.save(actorEntity);
    }
}
