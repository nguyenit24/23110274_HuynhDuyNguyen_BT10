package vn.iot.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iot.enity.UserEntity;
import vn.iot.repository.UserRepository;
import vn.iot.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity register(UserEntity user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return null; // Username exists
        }
        return userRepository.save(user);
    }

    @Override
    public UserEntity login(String username, String password) {
        UserEntity user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
