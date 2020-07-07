package de.hsba.bi.projectWork.user.annotations;

import de.hsba.bi.projectWork.user.ChangePasswordForm;
import de.hsba.bi.projectWork.user.RegisterUserForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

        @Override
        public void initialize(PasswordMatches constraintAnnotation) {
        }
        @Override
        public boolean isValid(Object obj, ConstraintValidatorContext context){
            if (obj.getClass() == RegisterUserForm.class) {
                RegisterUserForm user = (RegisterUserForm) obj;
                return user.getPassword().equals(user.getMatchingPassword());
            }

            else if (obj.getClass() == ChangePasswordForm.class) {
                ChangePasswordForm user = (ChangePasswordForm) obj;
                return user.getPassword().equals(user.getMatchingPassword());
            }

            else {
                return false;
            }

        }

}
