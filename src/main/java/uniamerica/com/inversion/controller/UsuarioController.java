package uniamerica.com.inversion.controller;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uniamerica.com.inversion.entity.Usuario;
import uniamerica.com.inversion.service.UsuarioService;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping
    public ResponseEntity<Page<Usuario>> listByAllPage(Pageable pageable) {
        return ResponseEntity.ok().body(this.usuarioService.listAll(pageable));
    }
    @PostMapping
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
            return ResponseEntity.ok().body("Usuario atualizado com sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/desativar/{idUsuario}")
    public ResponseEntity<?> desativar(@PathVariable Long idUsuario, @RequestBody Usuario usuario) {
        try {
            this.usuarioService.desativar(idUsuario, usuario);
            return ResponseEntity.ok().body("Usuario desativado com sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
