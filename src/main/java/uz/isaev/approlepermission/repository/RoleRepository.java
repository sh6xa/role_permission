package uz.isaev.approlepermission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.isaev.approlepermission.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
Optional<Role> findByName(String name);
boolean existsByName(String name);
}
