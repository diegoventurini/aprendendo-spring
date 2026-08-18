package com.javanauta.aprendendo_spring.business;

import com.javanauta.aprendendo_spring.infrastructure.entity.Usuario;
import com.javanauta.aprendendo_spring.infrastructure.exceptions.ConflictException;
import com.javanauta.aprendendo_spring.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.aprendendo_spring.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// Quando usar o Service, precisa colocar ele aqui
@Service

// Gera um construtor que inicializa campos com private final - Injecção de dependência
@RequiredArgsConstructor
public class UsuarioService {

    //  @Autowired - > Injecao de dependencia do UsuarioRepository
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

     // Salvar Usuário
    public Usuario salvaUsuario(Usuario usuario) {
       try {
           emailExiste(usuario.getEmail());
           // Seta senha criptografada
           usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
           return usuarioRepository.save(usuario);

       } catch (ConflictException e) {
           throw new ConflictException("Email já cadastrado", e.getCause());
       }
    }

    // Verifica se este email existe
    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado." + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado ", e.getCause());
        }
    }

    // Chamar a funcao existsByEmail(email)
    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não foi encontrado " + email));
    }

    // Deleta usuario por email
    public void deletarUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }
}
