package com.bugtracker.back.repositories;

import com.bugtracker.back.models.Project;
import com.bugtracker.back.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepo extends JpaRepository<Project, UUID> {

    Optional<List<Project>> findAllByProjectCreatorOrMembers(User projectCreator,User projectMember);
}
