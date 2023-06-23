package uniamerica.com.inversion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uniamerica.com.inversion.entity.Rendimento;
import uniamerica.com.inversion.repository.RendimentoRepository;

import javax.transaction.Transactional;

@Service
public class RendimentoService {


    @Autowired
    private RendimentoRepository rendimentoRepository;

    public Rendimento findById(Long id) {
        return this.rendimentoRepository.findById(id).orElse(new Rendimento());
    }

    public Page<Rendimento> listAll(Pageable pageable) {
        return this.rendimentoRepository.findAll(pageable);
    }

    @Transactional
    public void insert(Rendimento rendimento) {
        if (this.validarRequest(rendimento) == true) {
            this.rendimentoRepository.save(rendimento);
        } else {
            throw new RuntimeException("Falha ao Cadastrar o Rendimento");
        }
    }

    @Transactional
    public void update(Long id, Rendimento rendimento) {
        if (id == rendimento.getId() && this.validarRequest(rendimento) == true){
            this.rendimentoRepository.save(rendimento);
        } else {
            throw new RuntimeException("Falha ao Atualizar o Rendimento");
        }
    }

    @Transactional
    public void desativar(Long id, Rendimento rendimento) {
        if (id == rendimento.getId() && this.validarRequest(rendimento) == true){
            this.rendimentoRepository.desativar(rendimento.getId());
        } else {
            throw new RuntimeException("Falha ao Desativar o Rendimento");
        }
    }

    //** VALIDAÇÕES RENDIMENTO **//

    //Valida se o valor inserido no rendimento não é nulo
    public Boolean isPrecoNotNull(Rendimento rendimento) {
        if (rendimento.getPreco_un() == null || rendimento.getPreco_un().toString().isEmpty()) {
            throw new RuntimeException("O preço unitario não foi fornecido, favor insira um preço unitario valido.");
        } else {
            return true;
        }
    }

    //Valida se a quantida inserida no rendimento não é nulo
    public Boolean isQuantidadeNotNull(Rendimento rendimento) {
        if (rendimento.getQuantidade() == null || rendimento.getQuantidade().toString().isEmpty()) {
            throw new RuntimeException("A Quantidade não foi fornecida, favor insira uma quantidade valida.");
        } else {
            return true;
        }
    }

    //Valida se o preço inserido no rendimento não chegou com caracter especial
    public Boolean isPrecoCaracter(Rendimento rendimento) {
        char[] charSearch = { '[', '@', '_', '!', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '?', '/', '|', '}',
                '{', '~', ':', ']' };
        for (int i = 0; i < rendimento.getPreco_un().toString().length(); i++) {
            char chr = rendimento.getPreco_un().toString().charAt(i);
            for (int j = 0; j < charSearch.length; j++) {
                if (charSearch[j] == chr) {
                    throw new RuntimeException(
                            "O preço fornecido não é valido, favor insira um preço sem caracter especial.");
                }
            }
        }
        return true;
    }

    //Valida se a quantidade inserida no rendimento não chegou com caracter especial
    public Boolean isQuantidadeCaracter(Rendimento rendimento) {
        char[] charSearch = { '[', '@', '_', '!', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '?', '/', '|', '}',
                '{', '~', ':', ']' };
        for (int i = 0; i < rendimento.getQuantidade().toString().length(); i++) {
            char chr = rendimento.getQuantidade().toString().charAt(i);
            for (int j = 0; j < charSearch.length; j++) {
                if (charSearch[j] == chr) {
                    throw new RuntimeException(
                            "A quantidade fornecida não é valida, favor insira uma quantidade sem caracter especial.");
                }
            }
        }
        return true;
    }

    public boolean validarRequest(Rendimento rendimento) {
        if (this.isPrecoNotNull(rendimento) == true &&
                this.isQuantidadeNotNull(rendimento) == true &&
                this.isPrecoCaracter(rendimento) == true &&
                this.isQuantidadeCaracter(rendimento) == true) {
            return true;
        } else {
            return false;
        }
    }

}
