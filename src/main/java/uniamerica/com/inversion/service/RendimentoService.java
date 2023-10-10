package uniamerica.com.inversion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uniamerica.com.inversion.entity.Carteira;
import uniamerica.com.inversion.entity.Rendimento;
import uniamerica.com.inversion.entity.Usuario;
import uniamerica.com.inversion.repository.RendimentoRepository;
import uniamerica.com.inversion.repository.UsuarioRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class RendimentoService {


    @Autowired
    private RendimentoRepository rendimentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Rendimento findById(Long id, Usuario usuario) {
        return this.rendimentoRepository.findByIdAndUsuario(id, usuario).orElse(new Rendimento());
    }

    public Page<Rendimento> listAll(Pageable pageable, Usuario usuario) {
        return this.rendimentoRepository.findByUsuario(usuario, pageable);
    }

    @Transactional
    public void insert(Rendimento rendimento) {
        if (this.validarRequest(rendimento)) {
            this.rendimentoRepository.save(rendimento);
        } else {
            throw new RuntimeException("Falha ao Cadastrar o Rendimento");
        }
    }

    @Transactional
    public void update(Long id, Rendimento rendimento, Usuario usuario) {
        if (checarDono(rendimento, usuario)) {
            if (id == rendimento.getId() && this.validarRequest(rendimento)) {
                this.rendimentoRepository.save(rendimento);
            } else {
                throw new RuntimeException("Falha ao Atualizar o Rendimento");
            }
        }else {
            throw new RuntimeException("Voce nao tem acesso a atualizar este Rendimento");
        }
    }

    @Transactional
    public void desativar(Long id, Rendimento rendimento, Usuario usuario) {
        if (checarDono(rendimento, usuario)) {
            if (id == rendimento.getId() && this.validarRequest(rendimento)) {
                this.rendimentoRepository.desativar(rendimento.getId());
            } else {
                throw new RuntimeException("Falha ao Desativar o Rendimento");
            }
        }else {
            throw new RuntimeException("Voce nao tem acesso a desativar este Rendimento");
        }
    }



    //** VALIDAÇÕES RENDIMENTO **//

    public Boolean checarDono(Rendimento rendimento, Usuario usuario) {
        Optional<Rendimento> rendimentoAux = this.rendimentoRepository.findById(rendimento.getId());
        return rendimentoAux.isPresent() && rendimentoAux.get().getUsuario().getId().equals(usuario.getId());
    }

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
        return this.isPrecoNotNull(rendimento) &&
                this.isQuantidadeNotNull(rendimento) &&
                this.isPrecoCaracter(rendimento) &&
                this.isQuantidadeCaracter(rendimento);
    }

}
