package com.bugtracker.back.dto;

import com.bugtracker.back.utils.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {

    private String id;

    private String title;

    private String description;

    private Long[] assigneesIds;

    private TicketStatus status;

}
