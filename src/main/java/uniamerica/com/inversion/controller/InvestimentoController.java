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
import uniamerica.com.inversion.entity.Investimento;
import uniamerica.com.inversion.entity.Rendimento;
import uniamerica.com.inversion.entity.Usuario;
import uniamerica.com.inversion.service.InvestimentoService;
import uniamerica.com.inversion.service.UsuarioService;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/api/investimentos")
public class InvestimentoController {

    @Autowired
    InvestimentoService investimentoService;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/{idInvestimento}")
    public ResponseEntity<Investimento> findyById(@PathVariable("idInvestimento") Long idInvestimento){
        UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) currentAuth.getPrincipal();
        return ResponseEntity.ok().body(this.investimentoService.findById(idInvestimento, usuario));
    }

    @GetMapping
    public ResponseEntity<Page<Investimento>> listByAllPage(Pageable pageable) {
        UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) currentAuth.getPrincipal();
        return ResponseEntity.ok().body(this.investimentoService.listAll(pageable, usuario));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Investimento investimento) {
        try {
            UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = this.usuarioService.findById(((Usuario) currentAuth.getPrincipal()).getId());
            investimento.setUsuario(usuario);
            this.investimentoService.insert(investimento, usuario);
            return ResponseEntity.ok().body(investimento);
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

    @PutMapping("/{idInvestimento}")
    public ResponseEntity<?> update(@PathVariable Long idInvestimento, @RequestBody Investimento investimento) {
        try {
            UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = (Usuario) currentAuth.getPrincipal();
            investimento.setUsuario(usuario);
            this.investimentoService.update(idInvestimento, investimento, usuario);
            return ResponseEntity.ok().body(investimento);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/desativar/{idInvestimento}")
    public ResponseEntity<?> desativar(@PathVariable Long idInvestimento, @RequestBody Investimento investimento) {
        try {
            UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = (Usuario) currentAuth.getPrincipal();
            investimento.setUsuario(usuario);
            this.investimentoService.update(idInvestimento, investimento, usuario);
            return ResponseEntity.ok().body(investimento);
        }catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
