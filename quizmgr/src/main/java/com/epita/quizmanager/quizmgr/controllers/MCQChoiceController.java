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
import com.epita.quizmanager.quizmgr.models.MCQChoice;
import com.epita.quizmanager.quizmgr.services.MCQChoiceJPADAO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "MCQChoice CRUD API for MCQ quiz")
@RestController
public class MCQChoiceController {
	
	@Autowired
	private MCQChoiceJPADAO mcqchoiceDAO;
	
	// GET mcqchoices route
	@ApiOperation(value = "Gets all mcqchoices.")
	@GetMapping(value="/QuizManager/mcqchoices")
    public List<MCQChoice> listmcqchoices() {
        return mcqchoiceDAO.findAll();
    }
	
	// GET mcqchoices/{id} route
	@ApiOperation(value = "Gets MCQChoice n°{id}, if it exists in the database.")
	@GetMapping(value = "/QuizManager/mcqchoices/{id}")
	  public MCQChoice showOneMCQChoice(@PathVariable int id) {
		
		MCQChoice MCQChoice = mcqchoiceDAO.findById(id);
		
		if(MCQChoice==null) 
			throw new ErrorNotFoundException("MCQ Choices with id " + id + " CANNOT BE FOUND.");
		return MCQChoice;
	}
	
	// POST questions route
	@ApiOperation(value = "Creates mcqchoice if the {id} already exists in the database then updates it.")
	@PostMapping(value= "/QuizManager/mcqchoices")
	public ResponseEntity<Void> addMCQChoice(@Validated @RequestBody MCQChoice JSONMCQChoice) {
		
        MCQChoice mcqchoiceAdded = mcqchoiceDAO.save(JSONMCQChoice);
        if(mcqchoiceAdded == null) 
        	return ResponseEntity.noContent().build();
        
        URI location = ServletUriComponentsBuilder
        		.fromCurrentRequest()
        		.path("/{id}")
        		.buildAndExpand(mcqchoiceAdded.getMcqchoiceId())
        		.toUri();
        
        return ResponseEntity.created(location).build();
	}
	
	// POST questions list route
	// This is not working for some reason 
	@ApiOperation(value = "Creates mcqchoices from a JSON list.")
	@RequestMapping(
			  value = "/QuizManager/mcqchoices/add_list", 
			  method = RequestMethod.POST)
	public ResponseEntity<Void> addMCQChoice(@Validated @RequestBody List<MCQChoice> JSONMCQChoices) {
		
        List<MCQChoice> mcqchoiceAdded = mcqchoiceDAO.saveAll(JSONMCQChoices);
        if(mcqchoiceAdded == null) 
        	return ResponseEntity.noContent().build();
        
        // Loop through the JSONMCQChoices
        for (MCQChoice m : mcqchoiceAdded) {
        	URI location = ServletUriComponentsBuilder
	        		.fromCurrentRequest()
	        		.path("/{id}")
	        		.buildAndExpand(m.getMcqchoiceId())
	        		.toUri();
        	ResponseEntity.created(location).build();
		}
        
        
        return ResponseEntity.ok().build();
	}

	
	
	// PUT questions/ route
	@ApiOperation(value = "Updates mcqchoice with same id, if it exists in the database.")
	@PutMapping(value = "/QuizManager/mcqchoices")
	public void updateQuestion(@RequestBody MCQChoice mcqchoice) {
		mcqchoiceDAO.save(mcqchoice);
	}
	
	// DELETE mcqchoices/{id} route
	// Because of the "/Test/" route this will not be displayed on the API documentation but it can still be accessed
	@DeleteMapping (value = "/Test/mcqchoices/{id}")
	public void deleteMCQChoice(@PathVariable int id) {
		// Bad idea to delete mcqchoice because other objects will rely on the mcqchoices
		mcqchoiceDAO.deleteById(id);
	}
	
	// GET answers/ route
	@ApiOperation(value = "Gets valid answers.")
	@GetMapping(value = "/QuizManager/answers/")
    public List<MCQChoice> searchAnswers() {
        return mcqchoiceDAO.findByValidIsTrue();
    }
	
	
	// GET mcqchoices/questions/equals/{question_id} route
	@ApiOperation(value = "Gets mcqchoices with question id equal to {question_id}.")
	@GetMapping(value = "/QuizManager/mcqchoices/questions/equals/{question_id}")
	  public List<MCQChoice> getMCQChoicesWithEquals(@PathVariable int question_id) {
		return mcqchoiceDAO.findByQuestionIdEquals(question_id);
	}

}
