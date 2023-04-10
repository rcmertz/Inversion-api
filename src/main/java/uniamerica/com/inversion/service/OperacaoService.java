package uniamerica.com.inversion.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uniamerica.com.inversion.entity.Dividendo;
import uniamerica.com.inversion.entity.Operacao;
import uniamerica.com.inversion.repository.OperacaoRepository;

@Service
public class OperacaoService {
    @Autowired
    private OperacaoRepository operacaoRepository;

    public Operacao findById(Long id){
        return this.operacaoRepository.findById(id).orElse(new Operacao());
    }

    public Page<Operacao> listAll(Pageable pageable){
        return this.operacaoRepository.findAll(pageable);
    }

    @Transactional
    public void insert(Operacao operacao){
        if (this.validarCadastro(operacao) == true) {
            this.operacaoRepository.save(operacao);
        }else {
            throw new RuntimeException("Falha ao cadastrar uma Operação");
        }
    }

    @Transactional
    public void update (Long id, Operacao operacao){
        if (id == operacao.getId()){
            this.operacaoRepository.save(operacao);
        }
        else{
            throw new RuntimeException("Falha ao Atualizar a Operação");
        }
    }

    @Transactional
    public void desativar(Long id, Operacao operacao){
        if (id == operacao.getId()){
            this.operacaoRepository.desativar(operacao.getId());
        }else {
            throw new RuntimeException("Falha ao Desativar a Operação");
        }
    }

    public Boolean isValorEmpty(Operacao operacao) {
        if (operacao.getValor() == null || operacao.getValor().isEmpty()) {
            throw new RuntimeException("O Valor está vazio, favor insira um valor valido.");
        } else {
            return true;
        }
    }
    public boolean validarCadastro(Operacao operacao){
        if(this.isValorEmpty(operacao) == true)
        {
            return true;
        }else {
            return false;
        }
    }
}
