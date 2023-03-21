package uniamerica.com.inversion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uniamerica.com.inversion.entity.Dividendo;
import uniamerica.com.inversion.service.DividendoService;

@Controller
@CrossOrigin
@RequestMapping("/api/dividendos")
public class DividendoController {

    @Autowired
    DividendoService dividendoService;

    @GetMapping("{idDividendo}")
    public ResponseEntity<Dividendo> findById(@PathVariable("idDividendo") Long idDividendo) {
        return ResponseEntity.ok().body(this.dividendoService.findById(idDividendo));
    }
    @GetMapping
    public ResponseEntity<Page<Dividendo>> listByAllPage(Pageable pageable) {
        return ResponseEntity.ok().body(this.dividendoService.listAll(pageable));
    }
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Dividendo dividendo) {
        try {
            this.dividendoService.insert(dividendo);
            return ResponseEntity.ok().body("Dividendo cadastrada com sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{idDividendo}")
    public ResponseEntity<?> update(@PathVariable Long idDividendo, @RequestBody Dividendo dividendo) {
        try {
            this.dividendoService.update(idDividendo, dividendo);
            return ResponseEntity.ok().body("Dividendo atualizada com sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/desativar/{idDividendo}")
    public ResponseEntity<?> desativar(@PathVariable Long idDividendo, @RequestBody Dividendo dividendo) {
        try {
            this.dividendoService.desativar(idDividendo, dividendo);
            return ResponseEntity.ok().body("Dividendo desativado com sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
