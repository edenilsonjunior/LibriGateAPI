package br.com.librigate.controller;

import br.com.librigate.dto.authorization.AuthenticationRequest;
import br.com.librigate.dto.authorization.AuthenticationResponse;
import br.com.librigate.dto.authorization.RegisterRequest;
import br.com.librigate.model.entity.people.User;
import br.com.librigate.model.repository.UserRepository;
import br.com.librigate.model.service.authorization.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.login(),
                authenticationRequest.password()
        );
        var authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        var token = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        if (this.userRepository.findByLogin(registerRequest.login()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequest.password());
        User user = new User(registerRequest.login(), encryptedPassword, registerRequest.role());

        this.userRepository.save(user);

        return ResponseEntity.ok().build();
    }

}
