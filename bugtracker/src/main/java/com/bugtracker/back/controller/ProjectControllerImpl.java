package com.bugtracker.back.controller;

import com.bugtracker.back.Services.ProjectService;
import com.bugtracker.back.dto.ProjectDto;
import com.bugtracker.back.models.Project;
import com.bugtracker.back.models.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;


import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class ProjectControllerImpl implements ProjectController{

    private ProjectService projectService;

    @Override
    public ResponseEntity<Project> createProject(ProjectDto projectDto ) {
        try {
            Project createdProject = projectService.createProject(projectDto);
            return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public ResponseEntity<Project> updateProject(Project project) {
        Project updatedProject = projectService.updateProject(project);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Project> findProjectById(String id) {
        try {
            Project foundProject = projectService.getProjectById(id);
            return new ResponseEntity<>(foundProject, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public ResponseEntity<List<Project>> findAllProjects() {
        try{
            return new ResponseEntity<>(projectService.getAllCurrentUserProjects(),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public ResponseEntity<List<User>> getProjectMembers(String projectId) {
        try{
            return new ResponseEntity<>(projectService.getProjectMembers(projectId),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Project> addDevToProject( String projectId,  Long devId){
        try {
            return new ResponseEntity<>(projectService.assignDevToProject(projectId,devId),HttpStatus.OK) ;
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public ResponseEntity<Project> addDevsToProject(String projectId, Long[] devsIds){
        try {
            return new ResponseEntity<>(projectService.assignDevsToProject(projectId,devsIds),HttpStatus.OK) ;
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public ResponseEntity<Project> removeDevFromProject(String projectId,Long devId){
        try {
            return new ResponseEntity<>(projectService.removeDevFromProject(projectId,devId),HttpStatus.OK) ;
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Project> deleteProjectById(UUID id) {
        try {
            return new ResponseEntity<>(projectService.deleteProjectById(id),HttpStatus.OK) ;
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
