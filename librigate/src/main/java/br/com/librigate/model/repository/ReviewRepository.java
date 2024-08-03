package br.com.librigate.model.repository;

import br.com.librigate.model.entity.actions.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>{
}
