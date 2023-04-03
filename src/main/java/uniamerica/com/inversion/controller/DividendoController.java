package uniamerica.com.inversion.controller;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uniamerica.com.inversion.entity.Dividendo;
import uniamerica.com.inversion.service.DividendoService;

import java.util.HashMap;
import java.util.Map;

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
            return ResponseEntity.ok().body(dividendo);
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

    @PutMapping("/{idDividendo}")
    public ResponseEntity<?> update(@PathVariable Long idDividendo, @RequestBody Dividendo dividendo) {
        try {
            this.dividendoService.update(idDividendo, dividendo);
            return ResponseEntity.ok().body(dividendo);
        }catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");       // usar para tratar tudo com JSON *******
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PutMapping("/desativar/{idDividendo}")
    public ResponseEntity<?> desativar(@PathVariable Long idDividendo, @RequestBody Dividendo dividendo) {
        try {
            this.dividendoService.desativar(idDividendo, dividendo);
            return ResponseEntity.ok().body(dividendo);
        }catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");       // usar para tratar tudo com JSON *******
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


}
