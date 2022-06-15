package com.hampcode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  TutorialRequestDto {
    @NotBlank
    @NotNull
    private String title;

    @NotBlank
    @NotNull
    private String description;


    private boolean published=false;

}
