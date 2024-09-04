package br.com.librigate.model.repository;

import br.com.librigate.model.entity.people.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String>{

    UserDetails findByLogin(String login);
}
