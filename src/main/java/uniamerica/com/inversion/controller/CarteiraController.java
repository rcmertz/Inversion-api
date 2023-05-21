package com.uniamerica.inversion.controller;


import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.uniamerica.inversion.entity.Carteira;
import com.uniamerica.inversion.service.CarteiraService;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/api/carteiras")
public class CarteiraController {

    @Autowired
    CarteiraService carteiraService;

    @GetMapping("{idCarteira}")
    public ResponseEntity<Carteira> findById(@PathVariable("idCarteira") Long idCarteira) {
        return ResponseEntity.ok().body(this.carteiraService.findById(idCarteira));
    }
    @GetMapping
    public ResponseEntity<Page<Carteira>> listByAllPage(Pageable pageable) {
        return ResponseEntity.ok().body(this.carteiraService.listAll(pageable));
    }
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Carteira carteira) {
        try {
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
            this.carteiraService.update(idCarteira, carteira);
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
            this.carteiraService.update(idCarteira, carteira);
            return ResponseEntity.ok().body(carteira);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    //DESCRICAO
    //VALOR
    //DATA_CRIACAO
    //TIPO

}
