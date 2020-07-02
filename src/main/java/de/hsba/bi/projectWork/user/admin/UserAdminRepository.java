package de.hsba.bi.projectWork.user.admin;

import de.hsba.bi.projectWork.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAdminRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);
}
