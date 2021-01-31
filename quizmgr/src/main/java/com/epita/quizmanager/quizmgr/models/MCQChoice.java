package com.epita.quizmanager.quizmgr.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MCQChoice {

	@Id
	@GeneratedValue
	private int mcqchoiceId;
	
	private String choice;
	private boolean valid;

	private int questionId;

	public MCQChoice() {
		
	}

	public MCQChoice(int mcqchoiceId, String choice, boolean valid, int questionId) {
		this.mcqchoiceId = mcqchoiceId;
		this.choice = choice;
		this.valid = valid;
		this.questionId = questionId;
	}

	public int getMcqchoiceId() {
		return mcqchoiceId;
	}

	public void setMcqchoiceId(int mcqchoiceId) {
		this.mcqchoiceId = mcqchoiceId;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}


	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	
	
	
	
	
}

