package com.nerdware.roomy.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock private UserRepository userRepository;
  private UserService underTest;

  @BeforeEach
  void setUp(){
    underTest = new UserService(userRepository);
  }

  @Test
  void testAddUser(){
    //given
    User user = new User(1, "Test", "test@gmail.com", false);

    //when
    underTest.addUser(user);

    //then
    ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
    verify(userRepository).save(userArgumentCaptor.capture());

    User capturedUser = userArgumentCaptor.getValue();
    assertThat(capturedUser).isEqualTo(user);
  }

  @Test
  void testAddExistingUser(){
    //given
    User user = new User(1, "Test", "test@gmail.com", false);
    given(userRepository.findUserByID(user.getId())).willReturn(Optional.of(user));

    //when
    //then
    assertThatThrownBy(() -> underTest.addUser(user)).isInstanceOf(IllegalStateException.class).hasMessageContaining("ID 1 taken");
    verify(userRepository, never()).save(any());
  }

  @Test
  void testDeleteNonExistingUser(){
    //given
    User user = new User(1, "Test", "test@gmail.com", false);

    //when
    //then
    //given(userRepository.findUserByID(user.getId())).willReturn(Optional.empty());
    assertThatThrownBy(() -> underTest.deleteUser(user.getId())).isInstanceOf(IllegalStateException.class).hasMessageContaining(String.format("User with ID %d does not exist", user.getId()));
    verify(userRepository, never()).delete(any());
  }

  @Test
  void testDeleteExistingUser(){
    //given
    User user = new User(1, "Test", "test@gmail.com", false);


    //when
    given(userRepository.existsById(user.getId())).willReturn(true);
    underTest.deleteUser(user.getId());

    //then
    ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
    verify(userRepository).save(userArgumentCaptor.capture());
    User capturedUser = userArgumentCaptor.getValue();
    assertThat(capturedUser).isEqualTo(user);
  }

  @Test
  void testGetAllUsers(){
    //when
    underTest.getUsers();

    //then
    verify(userRepository).findAll();

  }

  @Test
  @Disabled
  void testUpdateUser(){

  }
}
