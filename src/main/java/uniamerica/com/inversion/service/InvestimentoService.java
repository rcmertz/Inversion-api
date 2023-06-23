package uniamerica.com.inversion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uniamerica.com.inversion.entity.Investimento;
import uniamerica.com.inversion.repository.InvestimentoRepository;

import javax.transaction.Transactional;

@Service
public class InvestimentoService {

    @Autowired
    private InvestimentoRepository investimentoRepository;

    public Investimento findById(Long id) { return this.investimentoRepository.findById(id).orElse(new Investimento());}

    public Page<Investimento> listAll(Pageable pageable){
        return this.investimentoRepository.findAll(pageable);
    }

    @Transactional
    public Investimento insert(Investimento investimento) {
        if (this.validarRequest(investimento) == true &&
            this.isInvestimentoExist(investimento) == true) {
            this.investimentoRepository.save(investimento);
            return investimento;
        } else {
            throw new RuntimeException("Falha ao cadastrar o investimento");
        }
    }

    @Transactional
    public void update (Long id, Investimento investimento) {
        if (id == investimento.getId() && this.validarRequest(investimento) == true){
            this.investimentoRepository.save(investimento);
        } else {
            throw new RuntimeException("Falha ao Atualizar o investimento");
        }
    }

    @Transactional
    public void desativar (Long id, Investimento investimento) {
        if (id == investimento.getId() && this.validarRequest(investimento) == true){
            this.investimentoRepository.save(investimento);
        } else {
            throw new RuntimeException("Falha ao Desativar o investimento");
        }
    }

    //** VALIDAÇÕES INVESTIMENTO **//

    public Boolean isValorCaracter(Investimento investimento) {
        char[] charSearch = {'[', '@', '_', '!', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '?', '/', '|', '}', '{', '~', ':', ']'};
        for (int i = 0; i < investimento.getValorInvestimento().toString().length(); i++) {
            char chr = investimento.getValorInvestimento().toString().charAt(i);
            for (int j = 0; j < charSearch.length; j++) {
                if (charSearch[j] == chr) {
                    throw new RuntimeException("O valor inserido não é válido, favor insira um valor sem caracter especial.");
                }
            }
        }
        return true;
    }

//    public Boolean isInvestimentoNotNull(Investimento investimento) {
//        if (investimento.getNomeInvestimento() == null || investimento.getNomeInvestimento().isEmpty()) {
//            throw new RuntimeException("O nome do investimento não foi fornecido, favor inserir um nome.");
//        } else {
//            return true;
//        }
//    }

    public Boolean isInvestimentoExist(Investimento investimento) {
        if (investimento.getNomeInvestimento() == null || investimento.getNomeInvestimento().isEmpty()) {
            throw new RuntimeException("O nome do investimento não foi fornecido, favor inserir um nome.");
        } else {
            // Verificar se já existe um investimento com o mesmo nome
            Investimento investimentoExistente = investimentoRepository.findByNomeInvestimento(investimento.getNomeInvestimento());

            if (investimentoExistente != null) {
                // Verificar se o investimento existente está ativo
                if (investimentoExistente.isAtivo()) {
                    throw new RuntimeException("Já existe um investimento ativo com o mesmo nome.");
                } else {
                    // Permitir a inserção caso o investimento existente esteja inativo
                    return true;
                }
            } else {
                return true;
            }
        }
    }

    public Boolean validarRequest(Investimento investimento){
        if (this.isValorCaracter(investimento) == true) {
            return true;
        } else {
            return false;
        }
    }

}
