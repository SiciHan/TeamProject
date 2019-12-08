package sg.nus.iss.team8.demo.services;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import antlr.collections.List;
import sg.nus.iss.team8.demo.models.User;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword arg0) {
    }
    
    public boolean userPasswordCheck(String password, User user) {

        PasswordEncoder passencoder = new BCryptPasswordEncoder();
        String encodedPassword = user.getPasswordHash();
        return passencoder.matches(password, encodedPassword);
    }
    

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
       
    	return false;
        
    }
}