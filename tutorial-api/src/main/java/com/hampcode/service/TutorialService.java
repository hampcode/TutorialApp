package com.hampcode.service;

import com.hampcode.converter.TutorialConverter;
import com.hampcode.dto.TutorialRequestDto;
import com.hampcode.exception.NotFoundException;
import com.hampcode.model.Tutorial;
import com.hampcode.repository.TutorialRepository;
import com.hampcode.validators.TutorialValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TutorialService {
    private TutorialRepository tutorialRepository;

    private TutorialConverter tutorialConverter;

    public TutorialService (TutorialRepository tutorialRepository,
                            TutorialConverter tutorialConverter){
        this.tutorialRepository=tutorialRepository;
        this.tutorialConverter=tutorialConverter;
    }

    @Transactional(readOnly = true)
    public Page<Tutorial> getAllTutorials(Pageable pageable){
        return  tutorialRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Tutorial> getAllTutorialsPublished(Pageable pageable){
        return  tutorialRepository.findByPublished(true,pageable);
    }

    @Transactional(readOnly = true)
    public Page<Tutorial> getAllTutorialsByTitle(String title,Pageable pageable){
        return  tutorialRepository.findByTitleContaining(title,pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Tutorial> getTutorialById(Long tutorialId){
        return Optional.ofNullable(tutorialRepository.findById(tutorialId)
                .orElseThrow(() -> new NotFoundException("Tutorial no existe")));
    }

    @Transactional
    public Tutorial createTutorial(TutorialRequestDto tutorialRequestDto) {
        TutorialValidator.validate(tutorialRequestDto);
        Tutorial tutorial=tutorialConverter.convertDtoToEntity(tutorialRequestDto);
        return tutorialRepository.save(tutorial);
    }

    @Transactional
    public Tutorial updateTutorial(Long id,TutorialRequestDto tutorialRequestDto) {
        TutorialValidator.validate(tutorialRequestDto);

        Tutorial tutorialFromDb=tutorialRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tutorial no existe"));

        tutorialFromDb.setTitle(tutorialRequestDto.getTitle());
        tutorialFromDb.setDescription(tutorialRequestDto.getDescription());
        tutorialFromDb.setPublished(tutorialRequestDto.isPublished());

        return tutorialRepository.save(tutorialFromDb);
    }

    @Transactional
    public void deleteTutorial(Long id){
        Tutorial tutorialFromDb=tutorialRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tutorial no existe"));
        tutorialRepository.delete(tutorialFromDb);
    }


    @Transactional
    public void deleteAllTutorials(){
        tutorialRepository.deleteAll();
    }

}
