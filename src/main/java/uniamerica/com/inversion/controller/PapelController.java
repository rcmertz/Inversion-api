package uniamerica.com.inversion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uniamerica.com.inversion.entity.Papel;
import uniamerica.com.inversion.service.PapelService;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/api/papel")
public class PapelController {

    @Autowired
    PapelService papelService;

    @GetMapping("/{idPapel}")
    public ResponseEntity<Papel> findyById(@PathVariable("idPapel") Long idPapel){
        return ResponseEntity.ok().body(this.papelService.findById(idPapel));
    }

    @GetMapping
    public ResponseEntity<Page<Papel>> listByAllPage(Pageable pageable) {
        return ResponseEntity.ok().body(this.papelService.listAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Papel papelService) {
        try {
            this.papelService.insert(papelService);
            return ResponseEntity.ok().body(papelService);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{idPapel}")
    public ResponseEntity<?> update(@PathVariable Long idPapel, @RequestBody Papel papel) {
        try {
            this.papelService.update(idPapel, papel);
            return ResponseEntity.ok().body(papel);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/desativar/{idPapel}")
    public ResponseEntity<?> desativar(@PathVariable Long idPapel, @RequestBody Papel papel) {
        try {
            this.papelService.desativar(idPapel, papel);
            return ResponseEntity.ok().body(papel);
        }catch (Exception e) {
            Map<String, Object> response = new HashMap<String, Object>();
            response.put("status", "500");
            response.put("status", "error");
            response.put("erro", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
