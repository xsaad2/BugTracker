package com.bugtracker.back.Services;

import com.bugtracker.back.dto.ProjectDto;
import com.bugtracker.back.exceptions.NotFoundException;
import com.bugtracker.back.models.Project;
import com.bugtracker.back.models.User;
import com.bugtracker.back.repositories.ProjectRepo;
import com.bugtracker.back.security.SecurityService;
import com.bugtracker.back.utils.AppUserRoles;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService{

    private ProjectRepo projectRepo;
    private UserService userService;
    private SecurityService securityService;
    @Override
    public Project createProject(ProjectDto projectDto) {
        Project newProject = new Project(projectDto.getTitle(),projectDto.getDescription());
        if(securityService.isLoggedIn()){
            User projectCreator = securityService.getCurrentUser();
            newProject.setProjectCreator(projectCreator);
        }
        return projectRepo.save(newProject);
    }

    @Override
    public Project updateProject(Project project) {
        return projectRepo.save(project);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    @Override
    public List<Project> getAllCurrentUserProjects() {
        User currentUser = securityService.getCurrentUser();
        boolean isAdminOrManager = currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority(AppUserRoles.ADMIN.toString())) ||
                currentUser.getAuthorities()
                        .contains(new SimpleGrantedAuthority(AppUserRoles.Manager.toString()));
        if (isAdminOrManager) {
            return getAllProjects();
        } else {
            return projectRepo.findAllByProjectCreatorOrMembers(currentUser,currentUser)
                    .orElseThrow(() -> new NotFoundException("Current User has or is in no projects"));
        }
    }


    @Override
    public Project getProjectById(String id) {
        UUID uuid = UUID.fromString(id);
        return projectRepo.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Project by id " + id + " was not found"));
    }

    @Override
    public List<User> getProjectMembers(String projectId) {
        return getProjectById(projectId).getMembers();
    }

    @Override
    public Project deleteProjectById(UUID id) {
        Project deletedProject = getProjectById(id.toString());
        projectRepo.deleteById(id);
        return deletedProject;
    }

    @Override
    @Transactional
    public Project assignDevToProject(String projectId, Long devId){
        Project projectToUpdate = getProjectById(projectId);
        User newAssignee = userService.findUserById(devId);
        newAssignee.getProjects().add(projectToUpdate);
        userService.updateUser(newAssignee);
        return projectToUpdate;
    }

    @Override
    public Project removeDevFromProject(String projectId, Long devId) {
        Project projectToUpdate = getProjectById(projectId);
        User newAssignee = userService.findUserById(devId);
        newAssignee.getProjects().remove(projectToUpdate);
        userService.updateUser(newAssignee);
        return projectToUpdate;
    }

    @Override
    @Transactional
    public Project assignDevsToProject(String projectId, Long[] devsIds){
        Project projectToUpdate = getProjectById(projectId);
        for(Long devId : devsIds){
            User newAssignee = userService.findUserById(devId);
            newAssignee.getProjects().add(projectToUpdate);
            userService.updateUser(newAssignee);
        }
        return updateProject(projectToUpdate);
    }
}
