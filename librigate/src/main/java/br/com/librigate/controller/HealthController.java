package br.com.librigate.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
@Tag(name = "Health Controller", description = "Controlador que testa a aplicação")
public class HealthController {

    @GetMapping
    public ResponseEntity<String> health(){

        return new ResponseEntity<>("Aplicacao rodando!", HttpStatus.OK);
    }
}
