package net.krchis.join.reposiory;


import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Answer extends AbstractEntity{

	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
	@JsonProperty
	private User writer;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
	@JsonProperty
	private Question question;
	@Lob
	@JsonProperty
	private String contents;
	
	

	public Answer() {

	}

	public Answer(User writer,Question question, String contents) {
		this.writer = writer;
		this.question = question;
		this.contents = contents;
		
		

	}
	
	
	
	@Override
	public String toString() {
		return "Answer [" + super.toString() + ", writer=" + writer + ", contents=" + contents +  "]";
	}
	public Boolean isSamewriter(User loginuser) {
		
		return this.writer.equals(loginuser);
		
	}
	
	public void deleteCountQuestionAnswer() {
		this.question.deleteAnswer();
	}
	
}
