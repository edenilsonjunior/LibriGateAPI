package br.com.librigate.model.service;

import br.com.librigate.exception.EntityNotFoundException;
import br.com.librigate.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.function.Supplier;

public class HandleRequest {

    public static ResponseEntity<?> handle(Supplier<ResponseEntity<?>> action){

        try {
            ResponseEntity<?> responseEntity = action.get();
            if (responseEntity.getBody() instanceof List && ((List<?>) responseEntity.getBody()).isEmpty()) {
                return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.NO_CONTENT);
            }
            return responseEntity;
        } catch (ValidationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
