package br.com.librigate.model.service;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class HandleRequest {

    private final Map<String, Object> validationErrorResponse = new HashMap<>();
    private final Map<String, Object> entityNotFoundResponse = new HashMap<>();
    private final Map<String, Object> internalServerErrorResponse = new HashMap<>();


    public HandleRequest() {
        validationErrorResponse.put("error", "Validation Failed");
        validationErrorResponse.put("status", HttpStatus.BAD_REQUEST.value());

        entityNotFoundResponse.put("error", "Entity not found");
        entityNotFoundResponse.put("status", HttpStatus.NOT_FOUND.value());

        internalServerErrorResponse.put("error", "Internal Server Error");
        internalServerErrorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


    @Transactional
    public ResponseEntity<?> handle(Supplier<ResponseEntity<?>> action) {
        try {
            ResponseEntity<?> response = action.get();

            if (isEmptyList(response))
                return new ResponseEntity<>(response.getBody(), HttpStatus.NO_CONTENT);

            return response;

        } catch (ValidationException ex) {
            validationErrorResponse.put("message", ex.getMessage());
            return new ResponseEntity<>(validationErrorResponse, HttpStatus.BAD_REQUEST);

        } catch (EntityNotFoundException ex) {
            entityNotFoundResponse.put("message", ex.getMessage());
            return new ResponseEntity<>(entityNotFoundResponse, HttpStatus.NOT_FOUND);

        } catch (Exception ex) {
            internalServerErrorResponse.put("message", ex.getMessage());
            return new ResponseEntity<>(internalServerErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isEmptyList(ResponseEntity<?> responseEntity) {
        return responseEntity.getBody() instanceof List && ((List<?>) responseEntity.getBody()).isEmpty();
    }

}
