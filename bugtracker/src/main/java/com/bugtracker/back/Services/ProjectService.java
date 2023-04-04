package com.bugtracker.back.Services;

import com.bugtracker.back.dto.ProjectDto;
import com.bugtracker.back.models.Project;
import com.bugtracker.back.models.User;

import java.util.List;
import java.util.UUID;

public interface ProjectService {

    Project createProject(ProjectDto projectDto);

    Project updateProject(Project project);

    Project assignDevToProject(String projectId, Long devId);

    Project removeDevFromProject(String projectId, Long devId);

    Project assignDevsToProject(String projectId, Long[] devsIds);
    List<Project> getAllProjects();

    List<Project> getAllCurrentUserProjects();

    Project getProjectById(String id);

    List<User> getProjectMembers(String ProjectId);

    Project deleteProjectById(UUID id);
}
