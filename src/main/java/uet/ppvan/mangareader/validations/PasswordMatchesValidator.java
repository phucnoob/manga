package uet.ppvan.mangareader.validations;

import uet.ppvan.mangareader.dtos.UserRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        UserRequest user = (UserRequest) obj;
        return user.password().equals(user.confirmPassword());
    }
}
