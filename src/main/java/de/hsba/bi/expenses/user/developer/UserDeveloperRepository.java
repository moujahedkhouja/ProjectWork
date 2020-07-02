package de.hsba.bi.expenses.user.developer;

import de.hsba.bi.expenses.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDeveloperRepository extends JpaRepository<User, Long> {
}
