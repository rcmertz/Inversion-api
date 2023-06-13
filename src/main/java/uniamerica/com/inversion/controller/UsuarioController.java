package uniamerica.com.inversion.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uniamerica.com.inversion.entity.Usuario;
import uniamerica.com.inversion.service.UsuarioService;

import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@CrossOrigin
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Usuario> findById(@PathVariable("idUsuario") Long idUsuario) {
        return ResponseEntity.ok().body(this.usuarioService.findById(idUsuario));
    }
    @RequestMapping(method = GET, value = "/me")
    public ResponseEntity<Usuario> getCurrentUser() {
        UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok().body(
                (Usuario) currentAuth.getPrincipal()
        );
    }

    @GetMapping
    public ResponseEntity<Page<Usuario>> listByAllPage(Pageable pageable) {
        return ResponseEntity.ok().body(this.usuarioService.listAll(pageable));
    }
    @PostMapping("/cadastro")
    public ResponseEntity<?> insert(@RequestBody Usuario usuario) {
        try {
            this.usuarioService.insert(usuario);
            return ResponseEntity.ok().body(usuario);
        } catch (HibernateException e) {
            return ResponseEntity.badRequest().body(e.toString());
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");       // usar para tratar tudo com JSON *******
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
            // return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/{idUsuario}")
    public ResponseEntity<?> update(@PathVariable Long idUsuario, @RequestBody Usuario usuario) {
        try {
            this.usuarioService.update(idUsuario, usuario);
            return ResponseEntity.ok().body(usuario);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");       // usar para tratar tudo com JSON *******
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/desativar/{idUsuario}")
    public ResponseEntity<?> desativar(@PathVariable Long idUsuario, @RequestBody Usuario usuario) {
        try {
            this.usuarioService.desativar(idUsuario, usuario);
            return ResponseEntity.ok().body(usuario);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");       // usar para tratar tudo com JSON *******
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

}
