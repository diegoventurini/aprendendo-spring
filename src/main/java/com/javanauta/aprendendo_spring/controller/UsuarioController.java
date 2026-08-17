package com.javanauta.aprendendo_spring.controller;

import com.javanauta.aprendendo_spring.business.UsuarioService;
import com.javanauta.aprendendo_spring.controller.dtos.UsuarioDTO;
import com.javanauta.aprendendo_spring.infrastructure.entity.Usuario;
import com.javanauta.aprendendo_spring.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

// Controladora
@RestController

// Aponta URI da controller
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    // Injeção de dependências
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

   /* -> ## Injecção de dependencia usando construtor padrão

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    */

    // POST -> CREATE (Padrão HTTP <- Padrão Rest)
    @PostMapping
    public ResponseEntity<Usuario> salvaUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuario));
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(),
                        usuarioDTO.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    // @GET -> BUSCAR (Padrão HTTP -> Padrão Rest)
    @GetMapping
    public ResponseEntity<Usuario> buscaUsuarioPorEmail (@RequestParam("email") String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email) {
        usuarioService.deletarUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

}
