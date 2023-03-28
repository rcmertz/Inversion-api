package uniamerica.com.inversion.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uniamerica.com.inversion.entity.Carteira;
import uniamerica.com.inversion.entity.Usuario;
import uniamerica.com.inversion.repository.CarteiraRepository;



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
        if (this.validarCadastro(carteira) == true) {
            this.carteiraRepository.save(carteira);
        }else {
            throw new RuntimeException("Falha ao cadastrar uma carteira");
        }
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

    //** Validacao do Carteira **//

    //Valida se Nome da carteira nao e vazio ou nulo
    public Boolean isCarteiraNotNull(Carteira carteira) {
        if (carteira.getCarDescricao() == null || carteira.getCarDescricao().isEmpty()) {
            throw new RuntimeException("A descrição da carteira não foi fornecido, favor inserir uma descriçao.");
        } else {
            return true;
        }
    }

    public boolean validarCadastro(Carteira carteira){
        if(this.isCarteiraNotNull(carteira) == true)
        {
            return true;
        }else {
            return false;
        }
    }


}
