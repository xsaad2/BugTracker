package com.bugtracker.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto implements Serializable {

    private String title;

    private String description;

    private List<String> membersIds;
}
