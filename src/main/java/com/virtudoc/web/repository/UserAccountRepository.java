package com.virtudoc.web.repository;

import com.virtudoc.web.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * JPA access layer for UserAccount objects.
 *
 * @author ARMmaster17
 */
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    /**
     * Searches for a user account by Username. Automatically implemented by the JPA framework at runtime.
     * @param username Username to search for.
     * @return List of found user accounts. May contain zero or one accounts.
     */
    List<UserAccount> findByUsername(String username);
}
