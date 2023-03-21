package uniamerica.com.inversion.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uniamerica.com.inversion.entity.TipoPapel;
import uniamerica.com.inversion.service.TipoPapelService;

@Controller
@CrossOrigin
@RequestMapping("/api/TIPO_PAPEL")
public class TipoPapelController {

    @Autowired
    TipoPapelService tipoPapelService;

    @GetMapping("/{idTIPO_PAPEL}")
    public ResponseEntity<TipoPapel> findyById(@PathVariable("idTIPO_PAPEL") Long idTipoPapelService){
        return ResponseEntity.ok().body(this.tipoPapelService.findById(idTipoPapelService));
    }

    @GetMapping("/{idTIPO_PAPEL}")
    public ResponseEntity<TipoPapel> findById(@PathVariable("idTIPO_PAPEL") Long idTipoPapelService) {
        return ResponseEntity.ok().body(this.tipoPapelService.findById(idTipoPapelService));
    }

    @GetMapping
    public ResponseEntity<Page<TipoPapel>> listByAllPage(Pageable pageable) {
        return ResponseEntity.ok().body(this.tipoPapelService.listAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody TipoPapel tipoPapelService) {
        try {
            this.tipoPapelService.insert(tipoPapelService);
            return ResponseEntity.ok().body("cadastrado com sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idTIPO_PAPEL}")
    public ResponseEntity<?> update(@PathVariable Long idTipoPapel, @RequestBody TipoPapel tipoPapel) {
        try {
            this.tipoPapelService.update(idTipoPapel, tipoPapel);
            return ResponseEntity.ok().body("atualizado com sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/desativar/{idTIPO_PAPEL}")
    public ResponseEntity<?> desativar(@PathVariable Long idTipoPapel, @RequestBody TipoPapel tipoPapel) {
        try {
            this.tipoPapelService.desativar(idTipoPapel, tipoPapel);
            return ResponseEntity.ok().body(" desativado com sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
