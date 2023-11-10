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
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        if (this.validarRequest(operacao)) {
            if (operacao.getTipo().equals(TipoOperacao.venda)){}
            this.operacaoRepository.save(operacao);
            return operacao;
        } else {
            throw new RuntimeException("Falha ao cadastrar a operacao");
        }
    }
    @Transactional
    public void update (Long id, Operacao operacao, Usuario usuario) {
        if (checarDono(operacao, usuario)) {
            if (id == operacao.getId() && this.validarRequest(operacao)) {
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
            if (id == operacao.getId() && this.validarRequest(operacao)) {
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
        return this.isOperacaoNotNull(operacao) &&
                this.isValorNegativo(operacao) &&
                this.isValorCaracter(operacao);
    }

    public BigDecimal calculaPrecoMedio(Usuario usuario, Long idInvestimento){
        var listValor = operacaoRepository.findValorByTipoCompraAndUsuario(usuario, idInvestimento);

        BigDecimal valorTotal = BigDecimal.ZERO;
        Integer quantidadeTotal = 0;
        BigDecimal precoMedio = BigDecimal.ZERO;

        for(Operacao operacao: listValor) {
            valorTotal = valorTotal.add(operacao.getValor());
            quantidadeTotal += operacao.getQuantidade();
            if(quantidadeTotal == 0){
                precoMedio = BigDecimal.ZERO;

            }else{
                precoMedio = valorTotal.divide(new BigDecimal(quantidadeTotal), 2, RoundingMode.HALF_UP);
            }
        };
        return precoMedio;
    }

    public void salvarPrecoMedio(Operacao operacao){

        BigDecimal precoMedio = this.calculaPrecoMedio(operacao.getUsuario(), operacao.getInvestimento().getId());
        if (operacao.getTipo().equals(TipoOperacao.venda)) {
            BigDecimal saldo = operacaoRepository.saldo(operacao.getInvestimento().getId(), operacao.getUsuario());
            int quantidadeVenda = operacao.getQuantidade();

            if (saldo.compareTo(BigDecimal.ZERO) > 0 && saldo.compareTo(new BigDecimal(quantidadeVenda)) >= 0) {
                // Verifique se o saldo é igual a zero ou nulo e redefina o preço médio
                if (saldo.compareTo(BigDecimal.ZERO) == 0 || saldo == null) {
                    BigDecimal resetPrecoMedio = BigDecimal.ZERO;
                    operacao.setPrecoMedio(resetPrecoMedio);
                }

                this.operacaoRepository.save(operacao);
            } else {
                throw new RuntimeException("Saldo insuficiente para realizar a venda");
            }
        } else {
            operacao.setPrecoMedio(precoMedio);
            // Para outros tipos de operação, apenas salve no repositório
            this.operacaoRepository.save(operacao);
        }
    }
}
