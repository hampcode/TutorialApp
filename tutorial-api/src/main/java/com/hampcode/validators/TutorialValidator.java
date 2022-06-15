package com.hampcode.validators;

import com.hampcode.dto.TutorialRequestDto;
import com.hampcode.exception.IncorrectRequestException;

public class TutorialValidator {

    public static  void validate(TutorialRequestDto tutorialRequestDto){
        if(tutorialRequestDto.getTitle() == null || tutorialRequestDto.getTitle().trim().isEmpty()) {
            throw new IncorrectRequestException("El titulo es requerido");
        }

        if(tutorialRequestDto.getDescription() == null || tutorialRequestDto.getDescription().trim().isEmpty()) {
            throw new IncorrectRequestException("La descripción es requerido");
        }
    }
}
