package uniamerica.com.inversion.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uniamerica.com.inversion.entity.Carteira;
import uniamerica.com.inversion.service.CarteiraService;

@Controller
@CrossOrigin
@RequestMapping("/api/carteira")
public class CarteiraController {

    @Autowired
    CarteiraService carteiraService;

    @GetMapping("{idCarteira}")
    public ReponseEntity<carteira> findById(@PathVariable("idCarteira") Long idCarteira) {
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
            return ResponseEntity.ok().body("Carteira cadastrada com sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{idCarteira}")
    public ResponseEntity<?> update(@PathVariable Long idCarteira, @RequestBody Carteira carteira) {
        try {
            this.carteiraService.update(idCarteira, carteira);
            return ResponseEntity.ok().body("Carteira atualizada com sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/desativar/{idCarteira}")
    public ResponseEntity<?> desativar(@PathVariable Long idCarteira, @RequestBody Carteira carteira) {
        try {
            this.carteiraService.desativar(idCarteira, carteira);
            return ResponseEntity.ok().body("Carteira desativado com sucesso.");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //DESCRICAO
    //VALOR
    //DATA_CRIACAO
    //TIPO

}
