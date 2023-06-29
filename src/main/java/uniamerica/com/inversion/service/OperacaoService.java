package uniamerica.com.inversion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uniamerica.com.inversion.entity.*;
import uniamerica.com.inversion.repository.OperacaoRepository;
import uniamerica.com.inversion.repository.UsuarioRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OperacaoService {

    @Autowired
    private OperacaoRepository operacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    //** PARA PEGAR UMA OPERACAO POR ID **//
    public Operacao findById(Long id, Usuario usuario){
        return this.operacaoRepository.findByIdAndUsuario(id, usuario).orElse(new Operacao());
    }

    //** PARA FILTRAR POR UM INVESTIMENTO E POR RANGE DE DATA**//
    public Page<Operacao> listAll(Pageable pageable, Usuario usuario, Optional<Investimento> investimento, Optional<LocalDateTime> dataStart, Optional<LocalDateTime> dataEnd){
        if (investimento.isPresent() && dataStart.isPresent() && dataEnd.isPresent()) {
            return this.operacaoRepository.findByUsuarioAndInvestimentoAndDataBetween(usuario, investimento.get(), dataStart.get(), dataEnd.get(), pageable);
        }else if (investimento.isPresent()){
            return this.operacaoRepository.findByUsuarioAndInvestimento(usuario, investimento.get(), pageable);
        }else{
            return this.operacaoRepository.findByUsuario(usuario, pageable);
        }
    }

    //** PARA PEGAR TODAS OPERACOES **//
    public Page<Operacao> listAllOperacao(Pageable pageable, Usuario usuario){
        return this.operacaoRepository.findByUsuario(usuario, pageable);
    }

    //** PARA TRAZER TODAS OPERACOES POR CARTEIRA, USADO PARA PAGINAR E PODE SER USADO COM RANGE DE DATA  **//
    public Page<Operacao> listAllByCarteira(Long carteira, Usuario usuario, Optional<LocalDateTime> dataStart, Optional<LocalDateTime> dataEnd, Pageable pageable){
        if (dataStart.isPresent() && dataEnd.isPresent()){
            return this.operacaoRepository.findByInvestimento_CarteiraIdAndUsuarioAndDataBetween(carteira, usuario, dataStart.get(), dataEnd.get(), pageable);
        }else if (dataStart.isEmpty() && dataEnd.isEmpty()){
            return this.operacaoRepository.findByInvestimento_CarteiraIdAndUsuario(carteira, usuario, pageable);
        }else {
            throw new RuntimeException("Data Start e Data End precisa ser preenchido ambos");
        }

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
    public void update (Long id, Operacao operacao, Usuario usuario) {
        if (checarDono(operacao, usuario)) {
            if (id == operacao.getId() && this.validarRequest(operacao) == true) {
                this.operacaoRepository.save(operacao);
            } else {
                throw new RuntimeException("Falha ao Atualizar a operacao");
            }
        }else {
            throw new RuntimeException("Voce nao tem acesso a atualizar esta Operacao");
        }
    }

    @Transactional
    public void desativar (Long id, Operacao operacao, Usuario usuario) {
        if (checarDono(operacao, usuario)) {
            if (id == operacao.getId() && this.validarRequest(operacao) == true) {
                this.operacaoRepository.save(operacao);
            } else {
                throw new RuntimeException("Falha ao Desativar a operacao");
            }
        }else {
            throw new RuntimeException("Voce nao tem acesso a desativar esta Operacao");
        }
    }

    //** Validacao da Operacao **//

    public Boolean checarDono(Operacao operacao, Usuario usuario) {
        Optional<Operacao> operacaoAux = this.operacaoRepository.findById(operacao.getId());
        return operacaoAux.isPresent() && operacaoAux.get().getUsuario().getId().equals(usuario.getId());
    }

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
