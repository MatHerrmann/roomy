package com.nerdware.roomy.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

// User = T = Typ der Daten, die dieses Repository verwaltet
// long = Typ der ID (könnte auch int )
// Interface für den Datenzugriff
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    Optional<User> findUserByID(long id);
}
