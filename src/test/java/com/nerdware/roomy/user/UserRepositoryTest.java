package com.nerdware.roomy.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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