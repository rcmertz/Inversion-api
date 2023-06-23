package uniamerica.com.inversion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uniamerica.com.inversion.entity.Operacao;
import uniamerica.com.inversion.repository.OperacaoRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class OperacaoService {

    @Autowired
    private OperacaoRepository operacaoRepository;

    public Operacao findById(Long id) { return this.operacaoRepository.findById(id).orElse(new Operacao());}

    public Page<Operacao> listAll(Pageable pageable){
        return this.operacaoRepository.findAll(pageable);
    }

    @Transactional
    public Operacao insert(Operacao operacao) {
        if (this.validarRequest(operacao) == true) {
            this.operacaoRepository.save(operacao);
            return operacao;
        } else {
            throw new RuntimeException("Falha ao cadastrar a operacao");
        }
    }

    @Transactional
    public void update (Long id, Operacao operacao) {
        if (id == operacao.getId() && this.validarRequest(operacao) == true){
            this.operacaoRepository.save(operacao);
        } else {
            throw new RuntimeException("Falha ao Atualizar a operacao");
        }
    }

    @Transactional
    public void desativar (Long id, Operacao operacao) {
        if (id == operacao.getId() && this.validarRequest(operacao) == true){
            this.operacaoRepository.save(operacao);
        } else {
            throw new RuntimeException("Falha ao Desativar a operacao");
        }
    }

    //** Validacao do Operacao **//

    //Valida se a quantidade do operacao nao foi inserido vazio ou nulo
    public Boolean isOperacaoNotNull(Operacao operacao) {
        if (operacao.getQuantidade() == null) {
            throw new RuntimeException("A quantidade da operacao esta vazia, favor insira a quantidade da operacao");
        } else {
            return true;
        }
    }

    //Valida se o valor inserido na operação é negativo
    public Boolean isValorNegativo(Operacao operacao){
        if (operacao.getValor().compareTo(BigDecimal.valueOf(0.0)) != -1) {
            return true;
        } else {
            throw new RuntimeException("O valor inserido é negativo, favor insira um valor válido.");
        }
    }

    //Valida se o campo valor chegou com caracter especial
    public Boolean isValorCaracter(Operacao operacao) {
        char[] charSearch = {'[', '@', '_', '!', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '?', '/', '|', '}', '{', '~', ':', ']'};
        for (int i = 0; i < operacao.getValor().toString().length(); i++) {
            char chr = operacao.getValor().toString().charAt(i);
            for (int j = 0; j < charSearch.length; j++) {
                if (charSearch[j] == chr) {
                    throw new RuntimeException("O valor inserido não é válido, favor insira um valor sem caracter especial.");
                }
            }
        }
        return true;
    }

    public Boolean validarRequest(Operacao operacao){
        if (this.isOperacaoNotNull(operacao) == true &&
                this.isValorNegativo(operacao) == true &&
                this.isValorCaracter(operacao) == true)
        {
            return true;
        } else {
            return false;
        }
    }

}
