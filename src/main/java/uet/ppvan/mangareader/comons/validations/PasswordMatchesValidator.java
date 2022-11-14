package uet.ppvan.mangareader.comons.validations;

import uet.ppvan.mangareader.users.UserRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        UserRequest user = (UserRequest) obj;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
