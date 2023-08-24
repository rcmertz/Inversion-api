package uniamerica.com.inversion.controller;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uniamerica.com.inversion.entity.Meta;
import uniamerica.com.inversion.entity.Usuario;
import uniamerica.com.inversion.service.MetaService;
import uniamerica.com.inversion.service.UsuarioService;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/api/metas")
public class MetaController {

    @Autowired
    MetaService metaService;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/{idMeta}")
    public ResponseEntity<Meta> findyById(@PathVariable("idMeta") Long idMeta){
        UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) currentAuth.getPrincipal();
        return ResponseEntity.ok().body(this.metaService.findById(idMeta, usuario));
    }

    @GetMapping
    public ResponseEntity<Page<Meta>> listByAllPage(Pageable pageable) {
        UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) currentAuth.getPrincipal();
        return ResponseEntity.ok().body(this.metaService.listAll(pageable, usuario));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Meta meta) {
        try {
            UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = this.usuarioService.findById(((Usuario) currentAuth.getPrincipal()).getId());
            meta.setUsuario(usuario);
            this.metaService.insert(meta, usuario);
            return ResponseEntity.ok().body(meta);
        } catch (HibernateException e) {
            return ResponseEntity.badRequest().body(e.toString());
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500"); // usar para tratar tudo com JSON *******
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{idMeta}")
    public ResponseEntity<?> update(@PathVariable Long idMeta, @RequestBody Meta meta) {
        try {
            UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = (Usuario) currentAuth.getPrincipal();
            meta.setUsuario(usuario);
            this.metaService.update(idMeta, meta, usuario);
            return ResponseEntity.ok().body(meta);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/desativar/{idMeta}")
    public ResponseEntity<?> desativar(@PathVariable Long idMeta, @RequestBody Meta meta) {
        try {
            UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = (Usuario) currentAuth.getPrincipal();
            meta.setUsuario(usuario);
            this.metaService.update(idMeta, meta, usuario);
            return ResponseEntity.ok().body(meta);
        }catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
