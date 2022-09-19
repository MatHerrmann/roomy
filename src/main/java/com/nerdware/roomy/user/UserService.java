package com.nerdware.roomy.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void addUser(User user) {
        Optional<User> userById = userRepository.findUserByID(user.getId());
        if (userById.isPresent()){
            throw new IllegalStateException("ID taken");
        }
        userRepository.save(user);
    }

    public void deleteUser(long userID) {
        boolean exists = userRepository.existsById(userID);
        if (! exists){
            throw new IllegalStateException(String.format("User with ID %d does not exist", userID));
        }
        userRepository.deleteById(userID);
    }

    @Transactional
    public void updateUser(long userID, String name, String email) {
        boolean exists = userRepository.existsById(userID);
        if (! exists){
            throw new IllegalStateException(String.format("User with ID %d does not exist", userID));
        }
        User user = userRepository.findById(userID).get();
        if (email != null && email.length() > 0 && ! user.getEmail().equals(email)) {
            user.setEmail(email);
        }
        if (name != null && name.length() > 0 && ! user.getName().equals(name)) {
            user.setName(name);
        }
    }
}
