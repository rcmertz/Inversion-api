package uniamerica.com.inversion.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uniamerica.com.inversion.entity.Dividendo;
import uniamerica.com.inversion.repository.DividendoRepository;

@Service
public class DividendoService {

    @Autowired
    private DividendoRepository dividendoRepository;

    public Dividendo findById(Long id){
        return this.dividendoRepository.findById(id).orElse(new Dividendo());
    }

    public Page<Dividendo> listAll(Pageable pageable){
        return this.dividendoRepository.findAll(pageable);
    }

    @Transactional
    public void insert(Dividendo dividendo){
        if (this.validarCadastro(dividendo) == true) {
            this.dividendoRepository.save(dividendo);
        }else {
            throw new RuntimeException("Falha ao Cadastrar o Dividendo");
        }
    }

    @Transactional
    public void update (Long id, Dividendo dividendo){
        if (id == dividendo.getId()){
            this.dividendoRepository.save(dividendo);
        }
        else{
            throw new RuntimeException("Falha ao Atualizar o dividendo");
        }
    }

    @Transactional
    public void desativar(Long id, Dividendo dividendo){
        if (id == dividendo.getId()){
            this.dividendoRepository.desativar(dividendo.getId());
        }else {
            throw new RuntimeException("Falha ao Desativar o dividendo");
        }
    }


    public Boolean isPrecoNotNull(Dividendo dividendo) {
        if (dividendo.getPreco() == null || dividendo.getPreco().toString().isEmpty()) {
            throw new RuntimeException("O preço não foi fornecido, favor insira um preço valido.");
        } else {
            return true;
        }
    }

    public Boolean isQuantidadeNotNull(Dividendo dividendo) {
        if (dividendo.getQuantidade() == null || dividendo.getQuantidade().toString().isEmpty()) {
            throw new RuntimeException("A Quantidade não foi fornecida, favor insira uma quantidade valida.");
        } else {
            return true;
        }
    }

    public Boolean isPrecoCaracter(Dividendo dividendo) {
        char[] charSearch = {'[', '@', '_', '!', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '?', '/', '|', '}', '{', '~', ':', ']'};
        for (int i = 0; i < dividendo.getPreco().toString().length(); i++) {
            char chr = dividendo.getPreco().toString().charAt(i);
            for (int j = 0; j < charSearch.length; j++) {
                if (charSearch[j] == chr) {
                    throw new RuntimeException("O preço fornecido não é valido, favor insira um preço sem caracter especial.");
                }
            }
        }
        return true;
    }

    public Boolean isQuantidadeCaracter(Dividendo dividendo) {
        char[] charSearch = {'[', '@', '_', '!', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '?', '/', '|', '}', '{', '~', ':', ']'};
        for (int i = 0; i < dividendo.getQuantidade().toString().length(); i++) {
            char chr = dividendo.getQuantidade().toString().charAt(i);
            for (int j = 0; j < charSearch.length; j++) {
                if (charSearch[j] == chr) {
                    throw new RuntimeException("A quantidade fornecida não é valida, favor insira uma quantidade sem caracter especial.");
                }
            }
        }
        return true;
    }

    public boolean validarCadastro(Dividendo dividendo){
            if(this.isPrecoNotNull(dividendo) == true &&
                    this.isQuantidadeNotNull(dividendo) == true &&
                    this.isPrecoCaracter(dividendo) == true &&
                    this.isQuantidadeCaracter(dividendo) == true)
            {
                return true;
            }else {
                return false;
            }
        }

}
