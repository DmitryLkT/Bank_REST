package org.lukdt.bank_card_management.service.userService;

import org.lukdt.bank_card_management.dto.authentication.LoginRequest;
import org.lukdt.bank_card_management.dto.authentication.RegisterRequest;
import org.lukdt.bank_card_management.dto.authentication.TokenResponse;
import org.lukdt.bank_card_management.entity.Role;
import org.lukdt.bank_card_management.entity.User;
import org.lukdt.bank_card_management.exception.customException.UserAlreadyExistsException;
import org.lukdt.bank_card_management.repository.UserRepository;
import org.lukdt.bank_card_management.security.jwt.JwtService;
import org.lukdt.bank_card_management.service.userService.userServiceInterface.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean existsById(Long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        if(userRepository.existsByLogin(request.getLogin())) {
            throw new UserAlreadyExistsException(request.getLogin());
        }

        userRepository.save(new User(
                request.getName(),
                request.getSurname(),
                request.getAge(),
                Role.USER,
                request.getLogin(),
                passwordEncoder.encode(request.getPassword())
        ));
    }

    @Override
    public TokenResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword())
        );

        UserDetails user = (UserDetails) auth.getPrincipal();

        return jwtService.generateToken(user);
    }


}
