package uniamerica.com.inversion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uniamerica.com.inversion.entity.Papel;
import uniamerica.com.inversion.repository.PapelRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class PapelService {

    @Autowired
    private PapelRepository papelRepository;

    public Papel findById(Long id) { return this.papelRepository.findById(id).orElse(new Papel());}

    public Page<Papel> listAll(Pageable pageable){
        return this.papelRepository.findAll(pageable);
    }

    @Transactional
    public Papel insert(Papel papel) {
        if (this.validarCadastro(papel) == true) {
            this.papelRepository.save(papel);
            return papel;
        } else {
            throw new RuntimeException("Falha ao cadastrar o Papel");
        }
    }

    @Transactional
    public void update (Long id, Papel papel) {
        if (id == papel.getId()) {
            this.papelRepository.save(papel);
        } else {
            throw new RuntimeException("Falha ao Atualizar o Papel");
        }
    }

    @Transactional
    public void desativar (Long id, Papel papel) {
        if (id == papel.getId()) {
            this.papelRepository.save(papel);
        } else {
            throw new RuntimeException("Falha ao Desativar o usuario");
        }
    }

    //** Validacao do Papel **//

    //Valida se a quantidade do papel nao foi inserido vazio ou nulo

    public Boolean isPapelNotNull(Papel papel) {
        if (papel.getQuantidade() == null) {
            throw new RuntimeException("A quantidade do Papel esta vazia, favor insira a quantidade do Papel");
        } else {
            return true;
        }
    }

    public Boolean validarCadastro(Papel papel){
        if (this.isPapelNotNull(papel) == true)
        {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isRecebidoPositivo(Papel papel){
        if (papel.getValor().compareTo(BigDecimal.valueOf(0.0)) != -1) {
            return true;
        } else {
            throw new RuntimeException("O valor inserido é negativo, favor insira um valor válido.");
        }
    }

    public Boolean isRecebidoCaracter(Papel papel) {
        char[] charSearch = {'[', '@', '_', '!', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '?', '/', '|', '}', '{', '~', ':', ']'};
        for (int i = 0; i < papel.getValor().toString().length(); i++) {
            char chr = papel.getValor().toString().charAt(i);
            for (int j = 0; j < charSearch.length; j++) {
                if (charSearch[j] == chr) {
                    throw new RuntimeException("O valor inserido não é válido, favor insira um valor sem caracter especial.");
                }
            }
        }
        return true;
    }

    public Boolean isRecebidoNotNull(Papel papel){
        if (papel.getValor() != null) {
            return true;
        } else {
            throw new RuntimeException("O valor inserido não foi fornecido, favor insira um valor válido.");
        }
    }

}
