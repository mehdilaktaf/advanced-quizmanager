package com.epita.quizmanager.quizmgr.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;



@Entity
public class Question {
	
	@Id
	@GeneratedValue
	private Integer questionId;
	
	private String questionTitle;
	private String topic ;
	
	
	private Integer difficulty;
	
	public Question() {
		
	}
	
	public Question(Integer questionId, String questionTitle, String topic, Integer difficulty) {
		this.questionId = questionId;
		this.questionTitle = questionTitle;
		this.topic = topic;
		this.difficulty = difficulty;
	}

	
	
	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public Integer getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}
	
	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	@Override
	public String toString() {
		return "Question [questionTitle=" + questionTitle + ", topic=" + topic + ", difficulty="
				+ difficulty + "]";
	}
	
	
}
