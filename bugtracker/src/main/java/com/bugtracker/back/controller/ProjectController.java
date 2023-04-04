package com.bugtracker.back.controller;

import com.bugtracker.back.dto.ProjectDto;
import com.bugtracker.back.models.Project;
import com.bugtracker.back.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 *
 The ProjectController interface defines the REST API endpoints for managing projects.
 */
public interface ProjectController {

    /**
     * Creates a new project with the provided data.
     * @param projectDto the data for the new project
     * @return a ResponseEntity containing the newly created Project object
     */
    @PostMapping("/project")
    ResponseEntity<Project> createProject(@RequestBody ProjectDto projectDto);

    /**
     * Updates an existing project with the provided data.
     * @param project the updated project data
     * @return a ResponseEntity containing the updated Project object
     */
    @PutMapping("/project")
    ResponseEntity<Project> updateProject(@RequestBody Project project);

    /**
     * Finds a project by its id.
     * @param id the id of the project to find
     * @return a ResponseEntity containing the Project object with the corresponding id
     */
    @GetMapping("/project/{id}")
    ResponseEntity<Project> findProjectById(@PathVariable String id);

    /**
     * Adds a developer to the project members .
     * @param projectId the id of the project to add the developer to
     * @param devId the id of the developer to add
     * @return a ResponseEntity containing the updated Project object
     */
    @PutMapping("/project/{projectId}/{devId}")
    ResponseEntity<Project> addDevToProject(@PathVariable String projectId, @PathVariable Long devId);

    /**
     * Adds multiple developers to a project.
     * @param projectId the id of the project to add the developers to
     * @param devIds an array of developer ids to add
     * @return a ResponseEntity containing the updated Project object
     */
    @PutMapping("/project/{projectId}")
    ResponseEntity<Project> addDevsToProject(@PathVariable String projectId, @RequestBody Long[] devIds);

    /**
     * Removes a developer from a project.
     * @param projectId the id of the project to remove the developer from
     * @param devId the id of the developer to remove
     * @return a ResponseEntity containing the updated Project object
     */
    @DeleteMapping("/project/{projectId}/{devId}")
    ResponseEntity<Project> removeDevFromProject(@PathVariable String projectId, @PathVariable Long devId);

    /**
     * Finds all projects.
     * @return a ResponseEntity containing a list of all Project objects
     */
    @GetMapping("/projects")
    ResponseEntity<List<Project>> findAllProjects();

    /**
     * Finds all developers associated with a project.
     * @param projectId the id of the project to find developers for
     * @return a ResponseEntity containing a list of User objects representing the project's developers
     */
    @GetMapping("/project/members/{projectId}")
    ResponseEntity<List<User>> getProjectMembers(@PathVariable String projectId);

    /**
     * Deletes a project by its id.
     * @param id the id of the project to delete
     * @return a ResponseEntity containing the deleted Project object
     */
    @DeleteMapping("/project/{id}")
    ResponseEntity<Project> deleteProjectById(@PathVariable UUID id);
}
