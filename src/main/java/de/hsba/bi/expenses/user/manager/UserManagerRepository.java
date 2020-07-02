package de.hsba.bi.expenses.user.manager;

import de.hsba.bi.expenses.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserManagerRepository extends JpaRepository<User, Long> {
}