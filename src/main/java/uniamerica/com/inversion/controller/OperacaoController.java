package com.uniamerica.inversion.controller;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.uniamerica.inversion.entity.Operacao;
import com.uniamerica.inversion.service.OperacaoService;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/api/operacoes")
public class OperacaoController {

    @Autowired
    OperacaoService operacaoService;

    @GetMapping("{idOperacao}")
    public ResponseEntity<Operacao> findById(@PathVariable("idOperacao") Long idOperacao) {
        return ResponseEntity.ok().body(this.operacaoService.findById(idOperacao));
    }
    @GetMapping
    public ResponseEntity<Page<Operacao>> listByAllPage(Pageable pageable) {
        return ResponseEntity.ok().body(this.operacaoService.listAll(pageable));
    }
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Operacao operacao) {
        try {
            this.operacaoService.insert(operacao);
            return ResponseEntity.ok().body(operacao);
        } catch (HibernateException e) {
            return ResponseEntity.badRequest().body(e.toString());
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");       // usar para tratar tudo com JSON *******
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{idOperacao}")
    public ResponseEntity<?> update(@PathVariable Long idOperacao, @RequestBody Operacao operacao) {
        try {
            this.operacaoService.update(idOperacao, operacao);
            return ResponseEntity.ok().body(operacao);
        }catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");       // usar para tratar tudo com JSON *******
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PutMapping("/desativar/{idOperacao}")
    public ResponseEntity<?> desativar(@PathVariable Long idOperacao, @RequestBody Operacao operacao) {
        try {
            this.operacaoService.desativar(idOperacao, operacao);
            return ResponseEntity.ok().body(operacao);
        }catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");       // usar para tratar tudo com JSON *******
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

}
