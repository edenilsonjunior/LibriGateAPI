package br.com.librigate.model.service.authorization;

import br.com.librigate.dto.authorization.AuthenticationRequest;
import br.com.librigate.dto.authorization.AuthenticationResponse;
import br.com.librigate.dto.authorization.RegisterRequest;
import br.com.librigate.exception.ValidationException;
import br.com.librigate.model.entity.people.User;
import br.com.librigate.model.repository.UserRepository;
import br.com.librigate.model.service.interfaces.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public ResponseEntity<?> login(AuthenticationRequest authenticationRequest) {


        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.login(),
                authenticationRequest.password()
        );
        var authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        var token = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @Override
    public void register(RegisterRequest registerRequest){
        if (this.userRepository.findByLogin(registerRequest.login()) != null) {
            throw new ValidationException("User already exists");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequest.password());
        User user = new User(registerRequest.login(), encryptedPassword, registerRequest.role());

        this.userRepository.save(user);
    }

}
