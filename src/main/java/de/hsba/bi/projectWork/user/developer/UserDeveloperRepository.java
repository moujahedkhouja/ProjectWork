package de.hsba.bi.projectWork.user.developer;

import de.hsba.bi.projectWork.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDeveloperRepository extends JpaRepository<User, Long> {
}
