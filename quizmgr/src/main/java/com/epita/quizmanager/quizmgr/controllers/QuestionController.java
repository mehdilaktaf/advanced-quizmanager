package com.epita.quizmanager.quizmgr.controllers;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.epita.quizmanager.quizmgr.exceptions.ErrorNotFoundException;
import com.epita.quizmanager.quizmgr.models.Question;
import com.epita.quizmanager.quizmgr.services.QuestionJPADAO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "Question CRUD API for MCQ quiz")
@RestController
public class QuestionController {
	
	
	@Autowired
	private QuestionJPADAO questionDAO;
	
	// GET questions route
	@ApiOperation(value = "Gets all questions.")
	@GetMapping(value="/QuizManager/questions")
    public List<Question> listQuestions() {
        return questionDAO.findAll();
    }
	
	// GET questions/{id} route
	@ApiOperation(value = "Gets question n°{id}, if it exists in the database.")
	@GetMapping(value = "/QuizManager/questions/{id}")
	  public Question showOneQuestion(@PathVariable int id) {
		Question question = questionDAO.findById(id);
		System.out.println(question);
		if(question==null) 
			throw new ErrorNotFoundException("Questions with id " + id + " CANNOT BE FOUND.");
		return question;
	}
	
	// PUT questions/ route
	@ApiOperation(value = "Updates question with same id, if it exists in the database.")
	@PutMapping(value = "/QuizManager/questions")
	  public void updateQuestion(@RequestBody Question question) {
		questionDAO.save(question);
	}
	 
	// DELETE questions/{id} route
	// Because of the "/Test/" route this will not be displayed on the API documentation but it can still be accessed
	@DeleteMapping (value = "/Test/questions/{id}")
	   public void deleteQuestion(@PathVariable int id) {
			// Bad idea to delete question because other objects will rely on the questions
			questionDAO.deleteById(id);
	}
	
	// POST questions route
	@ApiOperation(value = "Creates question if the {id} already exists in the database then updates it.")
	@PostMapping(value= "/QuizManager/questions")
	public ResponseEntity<Void> addQuestion(@Validated @RequestBody Question JSONQuestion) {
		
        Question questionAdded = questionDAO.save(JSONQuestion);
        if(questionAdded == null) 
        	return ResponseEntity.noContent().build();
        
        URI location = ServletUriComponentsBuilder
        		.fromCurrentRequest()
        		.path("/{id}")
        		.buildAndExpand(questionAdded.getQuestionId())
        		.toUri();
        
        return ResponseEntity.created(location).build();
	}
	
	// POST questions list route
	@ApiOperation(value = "Creates questions from a JSON list.")
	@PostMapping(value= "/QuizManager/questions/add_list")
	public ResponseEntity<Void> addQuestion(@Validated @RequestBody List<Question> JSONQuestions) {
		
        List<Question> questionsAdded = questionDAO.saveAll(JSONQuestions);
        if(questionsAdded == null) 
        	return ResponseEntity.noContent().build();
        
        // Loop through the JSONQuestions
        for (Question q : questionsAdded) {
        	 URI location = ServletUriComponentsBuilder
             		.fromCurrentRequest()
             		.path("/{id}")
             		.buildAndExpand(q.getQuestionId())
             		.toUri();
        	 
        	 ResponseEntity.created(location).build();
		}
       
        
        return ResponseEntity.ok().build();
	}
	
	// GET questions/difficulties/greater_than/{difficulty_limit} route
	@ApiOperation(value = "Gets questions with difficulty strictly greater than {difficulty_limit}.")
	@GetMapping(value = "/QuizManager/difficulties/greater_than/{difficulty_limit}")
	  public List<Question> getQuestionsWithGreaterDiffThan(@PathVariable int difficulty_limit) {
		return questionDAO.findByDifficultyGreaterThan(difficulty_limit);
	}
	
	// GET questions/difficulties/less_than/{difficulty_limit} route
	@ApiOperation(value = "Gets questions with difficulty strictly less than {difficulty_limit}.")
	@GetMapping(value = "/QuizManager/difficulties/less_than/{difficulty_limit}")
	  public List<Question> getQuestionsWithLessDiffThan(@PathVariable int difficulty_limit) {
		return questionDAO.findByDifficultyLessThan(difficulty_limit);
	}
	
	// GET questions/difficulties/equals/{difficulty_limit} route
	@ApiOperation(value = "Gets questions with difficulty equal to {difficulty_limit}.")
	@GetMapping(value = "/QuizManager/difficulties/equals/{difficulty_limit}")
	  public List<Question> getQuestionsWithEquals(@PathVariable int difficulty_limit) {
		return questionDAO.findByDifficultyEquals(difficulty_limit);
	}
	
	// GET questions/topics/{searched_topic} route
	@ApiOperation(value = "Gets questions with topic like {searched_topic}.")
	@GetMapping(value = "/QuizManager/topics/{searched_topic}")
    public List<Question> searchTopic(@PathVariable String searched_topic) {
        return questionDAO.findByTopicLike("%"+searched_topic+"%");
    }
	
	// GET questions/titles/{searched_title} route
	@ApiOperation(value = "Gets questions with title like {searched_title}.")
	@GetMapping(value = "/QuizManager/titles/{searched_title}")
    public List<Question> searchTitle(@PathVariable String searched_title) {
        return questionDAO.findByQuestionTitleLike("%"+searched_title+"%");
    }
	
}
