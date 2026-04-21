package org.lukdt.bank_card_management.service.userService;

import org.lukdt.bank_card_management.entity.User;
import org.lukdt.bank_card_management.repository.UserRepository;
import org.lukdt.bank_card_management.service.userService.userServiceInterface.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean existsById(Long userId) {
        return userRepository.existsById(userId);
    }
}
