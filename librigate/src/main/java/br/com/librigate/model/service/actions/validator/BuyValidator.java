package br.com.librigate.model.service.actions.validator;

import br.com.librigate.exception.ValidationException;
import br.com.librigate.model.entity.actions.Buy;
import org.springframework.stereotype.Component;

@Component
public class BuyValidator {

    public void validatePayment(Buy buy){

        if (buy.getStatus().equals("APPROVED"))
            throw new ValidationException("Payment already processed");

        if (buy.getStatus().equals("CANCELED"))
            throw new ValidationException("Purchase already canceled");
    }

    public void validatePaymentCancel(Buy buy){

        if (buy.getStatus().equals("CANCELED")) {
            throw new ValidationException("Purchase already canceled");
        }

        if (buy.getStatus().equals("APPROVED")) {
            throw new ValidationException("Purchase already approved");
        }
    }


}
