package uniamerica.com.inversion.controller;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uniamerica.com.inversion.entity.Rendimento;
import uniamerica.com.inversion.service.RendimentoService;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/api/rendimentos")
public class RendimentoController {

    @Autowired
    RendimentoService rendimentoService;

    @GetMapping("{idRendimento}")
    public ResponseEntity<Rendimento> findById(@PathVariable("idRendimento") Long idRendimento) {
        return ResponseEntity.ok().body(this.rendimentoService.findById(idRendimento));
    }

    @GetMapping
    public ResponseEntity<Page<Rendimento>> listByAllPage(Pageable pageable) {
        return ResponseEntity.ok().body(this.rendimentoService.listAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Rendimento rendimento) {
        try {
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
            this.rendimentoService.update(idRendimento, rendimento);
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
            this.rendimentoService.desativar(idRendimento, rendimento);
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
