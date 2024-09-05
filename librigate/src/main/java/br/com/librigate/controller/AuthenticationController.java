package br.com.librigate.controller;

import br.com.librigate.dto.authorization.AuthenticationRequest;
import br.com.librigate.dto.authorization.RegisterRequest;
import br.com.librigate.model.entity.people.UserRole;
import br.com.librigate.model.service.interfaces.IAuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "auth")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.login(authenticationRequest);
    }

    @PostMapping("/start-test")
    public ResponseEntity<?> startTest() {

        var registerRequest = new RegisterRequest("admin", "admin", UserRole.ADMIN);
        authenticationService.register(registerRequest);

        return ResponseEntity.ok().build();
    }

}
