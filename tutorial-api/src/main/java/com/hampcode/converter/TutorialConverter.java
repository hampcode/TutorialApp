package com.hampcode.converter;

import com.hampcode.dto.TutorialRequestDto;
import com.hampcode.dto.TutorialResponseDto;
import com.hampcode.model.Tutorial;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TutorialConverter {

    private ModelMapper modelMapper;

    public TutorialConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TutorialResponseDto convertEntityToDto(Tutorial tutorial) {
        return modelMapper.map(tutorial, TutorialResponseDto.class);
    }

    public List<TutorialResponseDto> convertEntityToDto(List<Tutorial> tutorials) {
        return tutorials.stream()
                .map(order -> convertEntityToDto(order))
                .collect(Collectors.toList());
    }

    public Tutorial convertDtoToEntity(TutorialRequestDto tutorialRequestDto) {
        return modelMapper.map(tutorialRequestDto, Tutorial.class);
    }

}
