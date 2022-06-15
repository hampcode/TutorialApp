package com.hampcode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TutorialResponseDto {
    private long id;
    private String title;
    private String description;
    private boolean published;
}
