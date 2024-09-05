package br.com.librigate.model.service.interfaces;

import br.com.librigate.dto.authorization.AuthenticationRequest;
import br.com.librigate.dto.authorization.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface IAuthenticationService {

     ResponseEntity<?> login(AuthenticationRequest authenticationRequest);
     void register(RegisterRequest registerRequest);
}
