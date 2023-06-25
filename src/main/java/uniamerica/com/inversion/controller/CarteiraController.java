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
import uniamerica.com.inversion.entity.Carteira;
import uniamerica.com.inversion.entity.Usuario;
import uniamerica.com.inversion.service.CarteiraService;
import uniamerica.com.inversion.service.UsuarioService;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/api/carteiras")
public class CarteiraController {

    @Autowired
    CarteiraService carteiraService;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("{idCarteira}")
    public ResponseEntity<Carteira> findById(@PathVariable("idCarteira") Long idCarteira) {
        UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) currentAuth.getPrincipal();
        return ResponseEntity.ok().body(this.carteiraService.findById(idCarteira, usuario));
    }
    @GetMapping
    public ResponseEntity<Page<Carteira>> listByAllPage(Pageable pageable) {
        UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) currentAuth.getPrincipal();
        return ResponseEntity.ok().body(this.carteiraService.listAll(pageable, usuario));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Carteira carteira) {
        try {
            UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = this.usuarioService.findById(((Usuario) currentAuth.getPrincipal()).getId());
            carteira.setUsuario(usuario);
            this.carteiraService.insert(carteira);
            return ResponseEntity.ok().body(carteira);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PutMapping("/{idCarteira}")
    public ResponseEntity<?> update(@PathVariable Long idCarteira, @RequestBody Carteira carteira) {
        try {
            UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = (Usuario) currentAuth.getPrincipal();
            carteira.setUsuario(usuario);
            this.carteiraService.update(idCarteira, carteira, usuario);
            return ResponseEntity.ok().body(carteira);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PutMapping("/desativar/{idCarteira}")
    public ResponseEntity<?> desativar(@PathVariable Long idCarteira, @RequestBody Carteira carteira) {
        try {
            UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = (Usuario) currentAuth.getPrincipal();
            carteira.setUsuario(usuario);
            this.carteiraService.update(idCarteira, carteira, usuario);
            return ResponseEntity.ok().body(carteira);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
