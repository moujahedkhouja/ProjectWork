package de.hsba.bi.projectWork.web.project;

import de.hsba.bi.projectWork.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class UpdateProjectForm {

        List<User> newUsers;

        public UpdateProjectForm (List<User> users) {
                this.newUsers = users;
        }

}
