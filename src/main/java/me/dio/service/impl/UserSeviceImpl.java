package me.dio.service.impl;

import jakarta.persistence.EntityNotFoundException;
import me.dio.domain.model.User;
import me.dio.domain.repository.UserRepository;
import me.dio.service.UserService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserSeviceImpl implements UserService {

    private final UserRepository userRepository;

    public UserSeviceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Iterable<User> findAll() {
        return  userRepository.findAll();
    }

    @Override
    public User create(User userToCreate) {
        if (userRepository.existsByAccountNumber(userToCreate.getAccount().getNumber())){
            throw new IllegalArgumentException("This Account number already exists.");
        }
        return userRepository.save(userToCreate);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void update(Long id, User userToUpdate) {
        User user = this.findById(id);
        if (!user.getId().equals(userToUpdate.getId())){
            throw new NoSuchElementException("This ID do not match to User ID");
        }
        user.setName(userToUpdate.getName());
        user.setAccount(userToUpdate.getAccount());
        user.setCard(userToUpdate.getCard());
        user.setFeatures(userToUpdate.getFeatures());
        user.setNews(userToUpdate.getNews());
        create(userToUpdate);

        userRepository.save(user);
    }
}
