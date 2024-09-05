package br.com.librigate.model.service.actions.factory;

import br.com.librigate.model.entity.actions.Restock;
import br.com.librigate.model.entity.people.Employee;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RestockFactory {

    public Restock createRestock(Employee employee, double totalPrice) {
        var restock = new Restock();
        restock.setEmployee(employee);
        restock.setRestockDate(LocalDate.now());
        restock.setPrice(totalPrice);
        return restock;
    }


}
