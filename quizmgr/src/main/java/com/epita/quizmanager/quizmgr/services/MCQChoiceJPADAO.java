package com.epita.quizmanager.quizmgr.services;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epita.quizmanager.quizmgr.models.MCQChoice;

@Repository
public interface MCQChoiceJPADAO extends JpaRepository<MCQChoice, Integer> {
	
	MCQChoice findById(int id);

	List<MCQChoice> findByValidIsTrue();

	List<MCQChoice> findByQuestionIdEquals(int question_id);
	
}
