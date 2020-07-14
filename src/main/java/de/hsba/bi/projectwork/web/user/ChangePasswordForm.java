package de.hsba.bi.projectwork.web.user;

import de.hsba.bi.projectwork.user.annotations.OldPasswordIsCorrect;
import de.hsba.bi.projectwork.user.annotations.PasswordMatches;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@PasswordMatches
@OldPasswordIsCorrect
public class ChangePasswordForm {

    private String oldPassword;

    @NotEmpty(message = "Please enter a password.")
    @NotNull(message = "Please confirm your password.")
    @Size(min = 10, message = "Your password has to be at least 10 characters long.")
    private String password;

    @NotEmpty(message = "Please confirm your password.")
    @NotNull(message = "Please confirm your password.")
    @Size(min = 10, message = "Your password has to be at least 10 characters long.")
    private String matchingPassword;

}