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
import uniamerica.com.inversion.entity.Rendimento;
import uniamerica.com.inversion.entity.Usuario;
import uniamerica.com.inversion.service.RendimentoService;
import uniamerica.com.inversion.service.UsuarioService;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/api/rendimentos")
public class RendimentoController {

    @Autowired
    RendimentoService rendimentoService;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("{idRendimento}")
    public ResponseEntity<Rendimento> findById(@PathVariable("idRendimento") Long idRendimento) {
        UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) currentAuth.getPrincipal();
        return ResponseEntity.ok().body(this.rendimentoService.findById(idRendimento, usuario));
    }

    @GetMapping
    public ResponseEntity<Page<Rendimento>> listByAllPage(Pageable pageable) {
        UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) currentAuth.getPrincipal();
        return ResponseEntity.ok().body(this.rendimentoService.listAll(pageable, usuario));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Rendimento rendimento) {
        try {
            UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = this.usuarioService.findById(((Usuario) currentAuth.getPrincipal()).getId());
            rendimento.setUsuario(usuario);
            this.rendimentoService.insert(rendimento);
            return ResponseEntity.ok().body(rendimento);
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

    @PutMapping("/{idRendimento}")
    public ResponseEntity<?> update(@PathVariable Long idRendimento, @RequestBody Rendimento rendimento) {
        try {
            UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = (Usuario) currentAuth.getPrincipal();
            rendimento.setUsuario(usuario);
            this.rendimentoService.update(idRendimento, rendimento, usuario);
            return ResponseEntity.ok().body(rendimento);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500"); // usar para tratar tudo com JSON *******
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/desativar/{idRendimento}")
    public ResponseEntity<?> desativar(@PathVariable Long idRendimento, @RequestBody Rendimento rendimento) {
        try {
            UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = (Usuario) currentAuth.getPrincipal();
            rendimento.setUsuario(usuario);
            this.rendimentoService.desativar(idRendimento, rendimento, usuario);
            return ResponseEntity.ok().body(rendimento);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500"); // usar para tratar tudo com JSON *******
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

}
