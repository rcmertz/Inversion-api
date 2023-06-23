package uniamerica.com.inversion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uniamerica.com.inversion.entity.Carteira;
import uniamerica.com.inversion.repository.CarteiraRepository;

import javax.transaction.Transactional;


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
        if (this.validarRequest(carteira) == true) {
            this.carteiraRepository.save(carteira);
        }else {
            throw new RuntimeException("Falha ao cadastrar uma carteira");
        }
    }

    @Transactional
    public void update (Long id, Carteira carteira){
        if (id == carteira.getId() && this.validarRequest(carteira) == true){
            this.carteiraRepository.save(carteira);
        }
        else{
            throw new RuntimeException("Falha ao Atualizar a Carteira");
        }
    }

    @Transactional
    public void desativar(Long id, Carteira carteira){
        if (id == carteira.getId() && this.validarRequest(carteira) == true){
            this.carteiraRepository.desativar(carteira.getId());
        }else {
            throw new RuntimeException("Falha ao Desativar a Carteira");
        }
    }

    //** VALIDAÇÕES CARTEIRA **//

    //Valida se Nome da carteira nao e vazio ou nulo
    public Boolean isCarteiraNotNull(Carteira carteira) {
        if (carteira.getDescricaoCarteira() == null || carteira.getDescricaoCarteira().isEmpty()) {
            throw new RuntimeException("A descrição da carteira não foi fornecido, favor inserir uma descriçao.");
        } else {
            return true;
        }
    }

    //Valida se tem caracter especial no nome da carteira
    public Boolean isValorValid(Carteira carteira) {
        char[] charSearch = {'[', '@', '_', '!', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '?', '/', '|', '}', '{', '~', ':', ']'};
        for (int i = 0; i < carteira.getValorCarteira().toString().length(); i++) {
            char chr = carteira.getValorCarteira().toString().charAt(i);
            for (int j = 0; j < charSearch.length; j++) {
                if (charSearch[j] == chr) {
                    throw new RuntimeException("O valor inserido não é válido, favor insira um valor sem caracter especial.");
                }
            }
        }
        return true;
    }

    public boolean validarRequest(Carteira carteira){
        if(this.isCarteiraNotNull(carteira) == true &&
                this.isValorValid(carteira) == true)
        {
            return true;
        }else {
            return false;
        }
    }

}
