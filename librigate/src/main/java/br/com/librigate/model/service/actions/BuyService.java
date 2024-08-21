package br.com.librigate.model.service.actions;

import java.time.LocalDate;

import br.com.librigate.model.dto.customer.buy.BuyRequest;
import br.com.librigate.model.entity.book.FisicalBook;
import br.com.librigate.model.mapper.actions.BuyMapper;
import br.com.librigate.model.repository.BuyRepository;
import br.com.librigate.model.service.interfaces.IBuyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BuyService implements IBuyService{

    private final BuyRepository buyRepository;

    public BuyService(BuyRepository buyRepository) {
        this.buyRepository = buyRepository;
    }

    @Override
    public ResponseEntity<?> findByPK(Long id) {
        var entity = buyRepository.findById(id);
        if(entity.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entity.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> purchase(BuyRequest request) {
        try {
            var entity = BuyMapper.instance.toEntity(request);
            double totalPrice = entity.getBooks().stream()
                .mapToDouble(FisicalBook::getPrice)
                .sum();
            entity.setTotalPrice(totalPrice);
            entity.setDueDate(LocalDate.now().plusDays(2));
            entity.setStatus("PENDENTE");
            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> processPayment(Long buyId) {
        var entity = buyRepository.findById(buyId);
        if (entity.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var payment = entity.get();
        if (payment.getStatus().equals("APROVADO")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        payment.setStatus("APROVADO");
        return new ResponseEntity<>(payment,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getPurchases(String cpf) {
        var entities = buyRepository.findByCustomerCpf(cpf);
        if (entities.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getPurchaseById(String cpf, Long paymentId) {
        var entity = buyRepository.findByCustomerCpfAndId(cpf, paymentId);
        if (entity.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entity.get(), HttpStatus.OK);
    }
}
