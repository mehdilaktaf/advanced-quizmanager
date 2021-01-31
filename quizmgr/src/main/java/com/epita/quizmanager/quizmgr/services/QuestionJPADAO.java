package com.epita.quizmanager.quizmgr.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epita.quizmanager.quizmgr.models.Question;

@Repository
public interface QuestionJPADAO extends JpaRepository<Question, Integer> {
	
	Question findById(int id);
	
	List<Question> findByTopicLike(String researchedTopic);
	List<Question> findByQuestionTitleLike(String researchedTitle);
	
	List<Question> findByDifficultyGreaterThan(int diffLimit);

	List<Question> findByDifficultyLessThan(int difficulty_limit);

	List<Question> findByDifficultyEquals(int difficulty_limit);
	
	
}
