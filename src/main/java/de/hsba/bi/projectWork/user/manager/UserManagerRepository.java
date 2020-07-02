package de.hsba.bi.projectWork.user.manager;

import de.hsba.bi.projectWork.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserManagerRepository extends JpaRepository<User, Long> {
}