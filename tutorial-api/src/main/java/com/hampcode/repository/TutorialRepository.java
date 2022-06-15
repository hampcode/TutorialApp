package com.hampcode.repository;

import java.util.List;

import com.hampcode.model.Tutorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
	//List<Tutorial> findByPublished(boolean published);
	//List<Tutorial> findByTitleContaining(String title);

	Page<Tutorial> findByPublished(boolean published, Pageable pageable);
	Page<Tutorial> findByTitleContaining(String title, Pageable pageable);
}
