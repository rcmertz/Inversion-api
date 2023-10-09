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
//            // Calcula o novo saldo
//            BigDecimal saldoAtual = operacaoRepository.saldo(operacao.getInvestimento().getId(), operacao.getUsuario());
//
//            BigDecimal valorTotal;
//            var listValor = operacaoRepository.findValorByTipoCompraAndUsuario(usuario, idInvestimento);
//
//            BigDecimal valorTotal = BigDecimal.ZERO;
//            Integer quantidadeTotal = 0;
//
//            for(Operacao operacao: listValor) {
//                valorTotal = valorTotal.add(operacao.getValor());
//                quantidadeTotal += operacao.getQuantidade();
//            }
//            return valorTotal.divide(new BigDecimal(quantidadeTotal));
//            // Calcula o novo preço médio se o saldo for maior que zero
//            BigDecimal novoPrecoMedio = BigDecimal.ZERO;
//            if (novoSaldo.compareTo(BigDecimal.ZERO) > 0) {
//                BigDecimal valorTotal = operacao.getValor().multiply(BigDecimal.valueOf(operacao.getQuantidade()));
//                novoPrecoMedio = valorTotal.divide(novoSaldo, 2, RoundingMode.HALF_UP);
//            }

            // Define o novo saldo e preço médio na operação
//            operacao.setPrecoMedio(novoPrecoMedio);

            // Salva a operação com o novo preço médio e saldo
            operacao = operacaoRepository.save(operacao);

            return operacao;
        } else {
            throw new RuntimeException("Falha ao cadastrar a operação");
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

    public BigDecimal findValorByTipoCompraAndUsuario(Usuario usuario, Long idInvestimento) {
        BigDecimal saldo = operacaoRepository.saldo(idInvestimento, usuario);

        // Se o saldo for zero ou negativo, o preço médio também deve ser zero
        if (saldo.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        var listValorTotal = operacaoRepository.findValorByTipoCompraAndUsuario(usuario, idInvestimento);
        BigDecimal valorTotal = BigDecimal.ZERO;

        for (Operacao operacao : listValorTotal) {
            valorTotal = valorTotal.add(operacao.getValor());
        }

        return valorTotal.divide(saldo, 2, RoundingMode.HALF_UP);
    }


//    private BigDecimal calcularPrecoMedio(Operacao operacao) {
//        // Recupere todas as operações de compra associadas ao mesmo investimento e usuário
//        List<Operacao> operacoesCompra = operacaoRepository.findOperacoesCompraByInvestimentoEUsuario(operacao.getUsuario(), operacao.getInvestimento());
//
//        // Recupere todas as operações de venda associadas ao mesmo investimento e usuário
//        List<Operacao> operacoesVenda = operacaoRepository.findOperacoesVendaByInvestimentoEUsuario(operacao.getUsuario(), operacao.getInvestimento());
//
//        BigDecimal valorTotal = BigDecimal.ZERO;
//        int quantidadeTotal = 0;
//
//        for (Operacao operacao : operacoesDeCompra) {
//            BigDecimal valorOperacao = operacao.getValor();
//            int quantidadeOperacao = operacao.getQuantidade();
//
//            valorTotal = valorTotal.add(valorOperacao.multiply(BigDecimal.valueOf(quantidadeOperacao)));
//            quantidadeTotal += quantidadeOperacao;
//        }
//
//        Map<String, Object> resultado = new HashMap<>();
//
//        resultado.put("quantidadeTotal", quantidadeTotal);
//
//        if (quantidadeTotal > 0) {
//            BigDecimal precoMedio = valorTotal.divide(BigDecimal.valueOf(quantidadeTotal), 2, BigDecimal.ROUND_HALF_UP);
//            resultado.put("precoMedio", precoMedio);
//        } else {
//            // Se a quantidade total for zero, retorne zero como o preço médio.
//            resultado.put("precoMedio", BigDecimal.ZERO);
//        }
//
//        return calcularPrecoMedio;
//    }

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

//    public List<Operacao> findComprasAtivasByInvestimentoAndUsuario(Long idInvestimento, Usuario usuario) {
//        return operacaoRepository.findValorByTipoCompraAndUsuario(usuario, idInvestimento);
//    }
//    public class CalculoPrecoMedio {
//        public static void main(String[] args) {
//            List<Operacao> comprasAtivas = /* Obtenha sua lista de compras aqui */;
//
//            int totalQuantidade = 0;
//            BigDecimal totalValor = BigDecimal.ZERO;
//
//            for (Operacao compra : comprasAtivas) {
//                int quantidade = compra.getQuantidade();
//                BigDecimal valorUnitario = compra.getValor();
//
//                totalQuantidade += quantidade;
//                totalValor = totalValor.add(valorUnitario.multiply(BigDecimal.valueOf(quantidade)));
//            }
//
//            if (totalQuantidade > 0) {
//                BigDecimal precoMedio = totalValor.divide(BigDecimal.valueOf(totalQuantidade), 2, BigDecimal.ROUND_HALF_UP);
//                System.out.println("Preço Médio de Compra: " + precoMedio);
//            } else {
//                System.out.println("Não foi possível calcular o preço médio, pois a quantidade total é zero.");
//            }
//        }
//    }


//    public Map<String, Object> calcularPrecoMedioDeCompra(Usuario usuario, Long idInvestimento) {
//        List<Operacao> operacoesDeCompra = operacaoRepository.findValorByTipoCompraAndUsuario(usuario, idInvestimento);
//
//        BigDecimal valorTotal = BigDecimal.ZERO;
//        int quantidadeTotal = 0;
//
//        for (Operacao operacao : operacoesDeCompra) {
//            BigDecimal valorOperacao = operacao.getValor();
//            int quantidadeOperacao = operacao.getQuantidade();
//
//            valorTotal = valorTotal.add(valorOperacao.multiply(BigDecimal.valueOf(quantidadeOperacao)));
//            quantidadeTotal += quantidadeOperacao;
//        }
//
//        Map<String, Object> resultado = new HashMap<>();
//
//        resultado.put("quantidadeTotal", quantidadeTotal);
//
//        if (quantidadeTotal > 0) {
//            BigDecimal precoMedio = valorTotal.divide(BigDecimal.valueOf(quantidadeTotal), 2, BigDecimal.ROUND_HALF_UP);
//            resultado.put("precoMedio", precoMedio);
//        } else {
//            // Se a quantidade total for zero, retorne zero como o preço médio.
//            resultado.put("precoMedio", BigDecimal.ZERO);
//        }
//
//        return resultado;
//    }

}
