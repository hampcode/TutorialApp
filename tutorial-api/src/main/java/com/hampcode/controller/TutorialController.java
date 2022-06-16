package com.hampcode.controller;

import java.util.*;

import com.hampcode.converter.TutorialConverter;
import com.hampcode.dto.TutorialRequestDto;
import com.hampcode.dto.TutorialResponseDto;
import com.hampcode.service.TutorialService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hampcode.model.Tutorial;

@RestController
@RequestMapping("/api/tutorials")
public class TutorialController {

	private TutorialService tutorialService;
	private TutorialConverter tutorialConverter;


	public TutorialController(TutorialService tutorialService,
							  TutorialConverter tutorialConverter) {
		this.tutorialService = tutorialService;
		this.tutorialConverter=tutorialConverter;
	}

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<Map<String, Object>> getAllTutorials(
			@RequestParam(required = false) String title,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size
	){

		List<Tutorial> tutorials = new ArrayList<Tutorial>();
		Pageable paging = PageRequest.of(page, size);
		Page<Tutorial> pageTuts;

		if (title == null)
			pageTuts=tutorialService.getAllTutorials(paging);
		else
			pageTuts=tutorialService.getAllTutorialsByTitle(title,paging);


		tutorials = pageTuts.getContent();


		Map<String, Object> response = new HashMap<>();
		response.put("tutorials", tutorials);
		response.put("currentPage", pageTuts.getNumber());
		response.put("totalItems", pageTuts.getTotalElements());
		response.put("totalPages", pageTuts.getTotalPages());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<TutorialResponseDto> getTutorialById(@PathVariable("id") long id) {
		Optional<Tutorial> tutorialData = tutorialService.getTutorialById(id);
		return new ResponseEntity<>(tutorialConverter.convertEntityToDto(tutorialData.get()), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<TutorialResponseDto> createTutorial(@RequestBody TutorialRequestDto tutorialRequestDto) {
			Tutorial tutorial = tutorialService.createTutorial(tutorialRequestDto);
			return new ResponseEntity<>(tutorialConverter.convertEntityToDto(tutorial), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<TutorialResponseDto> updateTutorial(@PathVariable("id") long id,@RequestBody TutorialRequestDto tutorialRequestDto) {
		Tutorial tutorial = tutorialService.updateTutorial(id,tutorialRequestDto);
		return new ResponseEntity<>(tutorialConverter.convertEntityToDto(tutorial), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
			tutorialService.deleteTutorial(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping
	public ResponseEntity<HttpStatus> deleteAllTutorials() {
			tutorialService.deleteAllTutorials();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PreAuthorize("hasRole('USER')  or hasRole('ADMIN')")
	@GetMapping("/published")
	public ResponseEntity<Map<String, Object>> findByPublished(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size
	) {
		List<Tutorial> tutorials = new ArrayList<Tutorial>();
		Pageable paging = PageRequest.of(page, size);

		Page<Tutorial> pageTuts = tutorialService.getAllTutorialsPublished(paging);
		tutorials = pageTuts.getContent();

		Map<String, Object> response = new HashMap<>();
		response.put("tutorials", tutorials);
		response.put("currentPage", pageTuts.getNumber());
		response.put("totalItems", pageTuts.getTotalElements());
		response.put("totalPages", pageTuts.getTotalPages());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}


}
