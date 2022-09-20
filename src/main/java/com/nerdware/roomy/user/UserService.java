package com.nerdware.roomy.user;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            throw new IllegalStateException(String.format("ID %d taken", user.getId()));
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
