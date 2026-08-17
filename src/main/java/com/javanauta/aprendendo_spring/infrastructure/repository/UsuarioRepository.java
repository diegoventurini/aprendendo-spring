package com.javanauta.aprendendo_spring.infrastructure.repository;

import com.javanauta.aprendendo_spring.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    // Evita pegar informações nulas
    Optional<Usuario> findByEmail(String email);

    // deleta sem ter conflitos
    @Transactional
    void deleteByEmail(String email);


}
