package br.com.librigate.controller;

import br.com.librigate.model.service.DatabaseHealthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health")
@Tag(name = "Health Controller", description = "Controlador que testa a aplicação")
public class HealthController {

    @Autowired
    private DatabaseHealthService databaseHealthService;


    @GetMapping("/database")
    public String checkDatabaseConnection() {
        if (databaseHealthService.isDatabaseConnected()) {
            return "Database connection is OK";
        } else {
            return "Database connection is NOT OK";
        }
    }
}
