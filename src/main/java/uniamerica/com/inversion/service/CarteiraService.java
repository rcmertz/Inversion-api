package uniamerica.com.inversion.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uniamerica.com.inversion.entity.Carteira;
import uniamerica.com.inversion.entity.Papel;
import uniamerica.com.inversion.entity.Usuario;
import uniamerica.com.inversion.repository.CarteiraRepository;

import java.math.BigDecimal;


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
        if (carteira.getDescricao() == null || carteira.getDescricao().isEmpty()) {
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

    public Boolean isDescricaoNotNull(Carteira carteira) {
        if (carteira.getDescricao() == null || carteira.getDescricao().isEmpty()) {
            throw new RuntimeException("Favor inserir uma descricao.");
        } else {
            return true;
        }
    }

    public Boolean isRecebidoPositivo(Carteira carteira){
        if (carteira.getValor().compareTo(BigDecimal.valueOf(0.0)) != -1) {
            return true;
        } else {
            throw new RuntimeException("O valor inserido é negativo, favor insira um valor válido.");
        }
    }

    public Boolean isRecebidoCaracter(Carteira carteira) {
        char[] charSearch = {'[', '@', '_', '!', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '?', '/', '|', '}', '{', '~', ':', ']'};
        for (int i = 0; i < carteira.getValor().toString().length(); i++) {
            char chr = carteira.getValor().toString().charAt(i);
            for (int j = 0; j < charSearch.length; j++) {
                if (charSearch[j] == chr) {
                    throw new RuntimeException("O valor inserido não é válido, favor insira um valor sem caracter especial.");
                }
            }
        }
        return true;
    }

    public Boolean isRecebidoNotNull(Carteira carteira){
        if (carteira.getValor() != null) {
            return true;
        } else {
            throw new RuntimeException("O valor inserido não foi fornecido, favor insira um valor válido.");
        }
    }


}
