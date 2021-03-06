package com.example.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Tag;

@Component
public class TagValidator implements Validator {

	final Integer MAX_NAME_LENGTH=100;

	final Integer MIN_NAME_LENGTH=2;
	
	final Integer MAX_DESCRIPTION_LENGTH=1000;


	@Override
	public boolean supports(Class<?> clazz) {

		return Tag.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Tag t= (Tag) target;
		String name= t.getName().trim();
		String description=  t.getDescription().trim();

		if(name.isEmpty())
			errors.rejectValue("name","required");
		else
			if(name.length()<MIN_NAME_LENGTH || name.length()>MAX_NAME_LENGTH)
				errors.rejectValue("name","size");
		if(description.isEmpty())
			errors.rejectValue("description","required");
		else
			if( description.length()>MAX_DESCRIPTION_LENGTH)
				errors.rejectValue("description","size");
	}


}


