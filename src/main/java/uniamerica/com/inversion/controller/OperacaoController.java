package uniamerica.com.inversion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uniamerica.com.inversion.entity.*;
import uniamerica.com.inversion.repository.InvestimentoRepository;
import uniamerica.com.inversion.service.OperacaoService;
import uniamerica.com.inversion.service.UsuarioService;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@CrossOrigin
@RequestMapping("/api/operacao")
public class OperacaoController {

    @Autowired
    OperacaoService operacaoService;

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    private InvestimentoRepository investimentoRepository;

    @GetMapping("/{idOperacao}")
    public ResponseEntity<Operacao> findyById(@PathVariable("idOperacao") Long idOperacao){
        UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) currentAuth.getPrincipal();
        return ResponseEntity.ok().body(this.operacaoService.findById(idOperacao, usuario));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<Operacao>> listAllByCarteira(Pageable pageable, @RequestParam(required = true) Long carteira, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> dataStart, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> dataEnd) {
        UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) currentAuth.getPrincipal();
        return ResponseEntity.ok().body(this.operacaoService.listAllByCarteira(carteira, usuario, dataStart, dataEnd, pageable));
    }


    @GetMapping
    public ResponseEntity<Page<Operacao>> listByAllPage(Pageable pageable , @RequestParam(required = false) Long investimentoId) {
        UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) currentAuth.getPrincipal();
        Optional<Investimento> investimento = investimentoRepository.findById(investimentoId);
        return ResponseEntity.ok().body(this.operacaoService.listAll(pageable, usuario, investimento));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Operacao operacao) {
        try {
            UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = this.usuarioService.findById(((Usuario) currentAuth.getPrincipal()).getId());
            operacao.setUsuario(usuario);
            this.operacaoService.insert(operacao);
            return ResponseEntity.ok().body(operacao);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{idOperacao}")
    public ResponseEntity<?> update(@PathVariable Long idOperacao, @RequestBody Operacao operacao) {
        try {
            UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = (Usuario) currentAuth.getPrincipal();
            operacao.setUsuario(usuario);
            this.operacaoService.update(idOperacao, operacao, usuario);
            return ResponseEntity.ok().body(operacao);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/desativar/{idOperacao}")
    public ResponseEntity<?> desativar(@PathVariable Long idOperacao, @RequestBody Operacao operacao) {
        try {
            UsernamePasswordAuthenticationToken currentAuth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = (Usuario) currentAuth.getPrincipal();
            operacao.setUsuario(usuario);
            this.operacaoService.desativar(idOperacao, operacao, usuario);
            return ResponseEntity.ok().body(operacao);
        }catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
