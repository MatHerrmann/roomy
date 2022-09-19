package com.nerdware.roomy.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void shouldCheckIfUserIdexists() {
        // given
        User user = new User(
                1,
                "Matthias",
                "matthias@gmail.com",
                true
        );
        underTest.save(user);

        // when
        Optional<User> userMaybe = underTest.findUserByID(user.getId());

        // then
        assertThat(userMaybe.isPresent()).isTrue();
    }

    @Test
    void shouldCheckIfUserDoesNotExist() {
        // given
        long userID = 1;

        // when
        Optional<User> userMaybe = underTest.findUserByID(userID);

        // then
        assertThat(userMaybe.isPresent()).isFalse();
    }
}