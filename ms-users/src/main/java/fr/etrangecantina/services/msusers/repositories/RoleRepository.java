package fr.etrangecantina.services.msusers.repositories;

import fr.etrangecantina.services.msusers.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
