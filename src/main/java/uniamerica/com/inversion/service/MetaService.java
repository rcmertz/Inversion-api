package uniamerica.com.inversion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uniamerica.com.inversion.entity.Meta;
import uniamerica.com.inversion.entity.Usuario;
import uniamerica.com.inversion.repository.MetaRepository;
import uniamerica.com.inversion.repository.UsuarioRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class MetaService {

    @Autowired
    private MetaRepository metaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Meta findById(Long id, Usuario usuario){
        return this.metaRepository.findByIdAndUsuario(id, usuario).orElse(new Meta());
    }

    public Page<Meta> listAll(Pageable pageable, Usuario usuario){
        return this.metaRepository.findByUsuario(usuario, pageable);
    }

    @Transactional
    public Meta insert(Meta meta, Usuario usuario) {
        if (this.validarRequest(meta) &&
                this.isMetaExist(meta, usuario)) {
            this.metaRepository.save(meta);
            return meta;
        } else {
            throw new RuntimeException("Falha ao cadastrar a meta");
        }
    }

    @Transactional
    public void update (Long id, Meta meta, Usuario usuario) {
        if (checarDono(meta, usuario)) {
            if (id == meta.getId() && this.validarRequest(meta)) {
                this.metaRepository.save(meta);
            } else {
                throw new RuntimeException("Falha ao Atualizar o meta");
            }
        }else {
            throw new RuntimeException("Voce nao tem acesso a atualizar este Meta");
        }
    }

    @Transactional
    public void desativar (Long id, Meta meta, Usuario usuario) {
        if (checarDono(meta, usuario)) {
            if (id == meta.getId() && this.validarRequest(meta)) {
                this.metaRepository.save(meta);
            } else {
                throw new RuntimeException("Falha ao Desativar a meta");
            }
        }else {
            throw new RuntimeException("Voce nao tem acesso a desativar esta Meta");
        }
    }

    //** VALIDAÇÕES META **//

    public Boolean checarDono(Meta meta, Usuario usuario) {
        Optional<Meta> metaAux = this.metaRepository.findById(meta.getId());
        return metaAux.isPresent() && metaAux.get().getUsuario().getId().equals(usuario.getId());
    }

    public Boolean isOrcadoCaracter(Meta meta) {
        char[] charSearch = {'[', '@', '_', '!', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '?', '/', '|', '}', '{', '~', ':', ']'};
        for (int i = 0; i < meta.getOrcadoMeta().toString().length(); i++) {
            char chr = meta.getOrcadoMeta().toString().charAt(i);
            for (int j = 0; j < charSearch.length; j++) {
                if (charSearch[j] == chr) {
                    throw new RuntimeException("O valor orcado inserido não é válido, favor insira um valor sem caracter especial.");
                }
            }
        }
        return true;
    }

    public Boolean isRealizadoCaracter(Meta meta) {
        char[] charSearch = {'[', '@', '_', '!', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '?', '/', '|', '}', '{', '~', ':', ']'};
        for (int i = 0; i < meta.getRealizadoMeta().toString().length(); i++) {
            char chr = meta.getRealizadoMeta().toString().charAt(i);
            for (int j = 0; j < charSearch.length; j++) {
                if (charSearch[j] == chr) {
                    throw new RuntimeException("O valor realizado inserido não é válido, favor insira um valor sem caracter especial.");
                }
            }
        }
        return true;
    }

    public Boolean isMetaExist(Meta meta, Usuario usuario) {
        if (meta.getDescricaoMeta() == null || meta.getDescricaoMeta().isEmpty()) {
            throw new RuntimeException("A descrição da meta não foi fornecido, favor inserir um nome.");
        } else {
            // Verificar se já existe uma meta com a mesma descricao
            Meta metaExistente = metaRepository.findByDescricaoMeta(meta.getDescricaoMeta(), usuario.getId());

            if (metaExistente != null) {
                // Verificar se o meta existente está ativo
                if (metaExistente.isAtivo()) {
                    throw new RuntimeException("Já existe uma meta ativa com a mesma descricao.");
                } else {
                    // Permitir a inserção caso a meta existente esteja inativa
                    return true;
                }
            } else {
                return true;
            }
        }
    }

    public Boolean validarRequest(Meta meta){
        if (this.isOrcadoCaracter(meta) &&
            this.isRealizadoCaracter(meta)) {
            return true;
        } else {
            return false;
        }
    }

}
