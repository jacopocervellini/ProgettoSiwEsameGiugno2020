package com.example.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Comment;

@Component
public class CommentValidator implements Validator {

	final Integer MAX_TEXT_LENGTH=100;

	final Integer MIN_TEXT_LENGTH=6;
	


	@Override
	public boolean supports(Class<?> clazz) {

		return Comment.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Comment c= (Comment) target;
		String commento= c.getText().trim();


		if(commento.isEmpty())
			errors.rejectValue("text","required");
		else
			if(commento.length()<MIN_TEXT_LENGTH || commento.length()>MAX_TEXT_LENGTH)
				errors.rejectValue("text","size");
		
	}


}


