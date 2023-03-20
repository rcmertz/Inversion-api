package uniamerica.com.inversion.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uniamerica.com.inversion.entity.Carteira;

@Service
public class CarteiraService {
    @Autowired
    private CarteiraRepository carteiraRepository;

    public Carteira findById(Long id){
        return this.carteiraRepository.findById(id).orElse(new Carteira());
    }

    public Page<Carteira> listAll(Pageable pageable){
        return this.carteiraRepository.findAll(pageable);
    }

    @Transactional
    public void insert(Carteira carteira){
        this.validarCadastro(carteira);
        this.carteiraRepository.save(carteira);
    }

    @Transactional
    public void update (Long id, Carteira carteira){
        if (id == carteira.getId()){
            this.carteiraRepository.save(carteira);
        }
        else{
            throw new RuntimeException("Falha ao Atualizar a Carteira");
        }
    }

    @Transactional
    public void desativar(Long id, Carteira carteira){
        if (id == carteira.getId()){
            this.carteiraRepository.desativar(carteira.getId());
        }else {
            throw new RuntimeException("Falha ao Desativar a Carteira");
        }
    }


}
