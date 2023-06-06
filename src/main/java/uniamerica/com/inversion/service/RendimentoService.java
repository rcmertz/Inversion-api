package uniamerica.com.inversion.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uniamerica.com.inversion.entity.Rendimento;
import uniamerica.com.inversion.repository.RendimentoRepository;

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
        if (this.validarCadastro(rendimento) == true) {
            this.rendimentoRepository.save(rendimento);
        } else {
            throw new RuntimeException("Falha ao Cadastrar o Rendimento");
        }
    }

    @Transactional
    public void update(Long id, Rendimento rendimento) {
        if (id == rendimento.getId()) {
            this.rendimentoRepository.save(rendimento);
        } else {
            throw new RuntimeException("Falha ao Atualizar o Rendimento");
        }
    }

    @Transactional
    public void desativar(Long id, Rendimento rendimento) {
        if (id == rendimento.getId()) {
            this.rendimentoRepository.desativar(rendimento.getId());
        } else {
            throw new RuntimeException("Falha ao Desativar o Rendimento");
        }
    }

    public Boolean isPrecoNotNull(Rendimento rendimento) {
        if (rendimento.getPreco() == null || rendimento.getPreco().toString().isEmpty()) {
            throw new RuntimeException("O preço não foi fornecido, favor insira um preço valido.");
        } else {
            return true;
        }
    }

    public Boolean isQuantidadeNotNull(Rendimento rendimento) {
        if (rendimento.getQuantidade() == null || rendimento.getQuantidade().toString().isEmpty()) {
            throw new RuntimeException("A Quantidade não foi fornecida, favor insira uma quantidade valida.");
        } else {
            return true;
        }
    }

    public Boolean isPrecoCaracter(Rendimento rendimento) {
        char[] charSearch = { '[', '@', '_', '!', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '?', '/', '|', '}',
                '{', '~', ':', ']' };
        for (int i = 0; i < rendimento.getPreco().toString().length(); i++) {
            char chr = rendimento.getPreco().toString().charAt(i);
            for (int j = 0; j < charSearch.length; j++) {
                if (charSearch[j] == chr) {
                    throw new RuntimeException(
                            "O preço fornecido não é valido, favor insira um preço sem caracter especial.");
                }
            }
        }
        return true;
    }

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

    public boolean validarCadastro(Rendimento rendimento) {
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
