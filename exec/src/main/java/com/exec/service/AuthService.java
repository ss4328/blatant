package com.exec.service;

import com.exec.dto.RegisterRequest;
import com.exec.model.NotificationEmail;
import com.exec.model.User;
import com.exec.model.VerificationToken;
import com.exec.repository.UserRepository;
import com.exec.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor

public class AuthService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;


    @Transactional
    public void signup(RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getEmail());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);
        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please activate you Blatant Account",
                user.getEmail(),
                "Thanks for signing up for Blatant. Please click on teh following link to activate your account:"
                        + "http://localhost:/8080/api/auth/accountVerification/"
                +token));
    }

    private String generateVerificationToken(User user) {

        String token = UUID.randomUUID().toString();
        //persist this in db; user may activate after days

        VerificationToken vt = new VerificationToken();
        vt.setToken(token);
        verificationTokenRepository.save(vt);
        return token;
    }

}
