package uniamerica.com.inversion.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uniamerica.com.inversion.entity.TipoPapel;
import uniamerica.com.inversion.entity.Usuario;
import uniamerica.com.inversion.repository.TipoPapelRepository;


@Service
public class TipoPapelService {

    @Autowired
    private TipoPapelRepository tipoPapelRepository;

    public TipoPapel findById(Long id){
        return this.tipoPapelRepository.findById(id).orElse(new TipoPapel());
    }

    public Page<TipoPapel> listAll(Pageable pageable){
        return this.tipoPapelRepository.findAll(pageable);
    }

    @Transactional
    public void insert(TipoPapel tipoPapel){
        this.validarCadastro(tipoPapel);
        this.tipoPapelRepository.save(tipoPapel);
    }

    @Transactional
    public void update (Long id, TipoPapel tipoPapel){
        if (id == tipoPapel.getId()){
            this.tipoPapelRepository.save(tipoPapel);
        }
        else{
            throw new RuntimeException("Falha ao Atualizar o tipo do papel");
        }
    }

    @Transactional
    public void desativar(Long id, TipoPapel tipoPapel){
        if (id == tipoPapel.getId()){
            this.tipoPapelRepository.desativar(tipoPapel.getId());
        }else {
            throw new RuntimeException("Falha ao Desativar o tipo de papel");
        }
    }

    public Boolean isTipoNotNull(TipoPapel tipoPapel) {
        if (tipoPapel.getTipo() == null || tipoPapel.getTipo().isEmpty()) {
            throw new RuntimeException("O nome não foi fornecido, favor insira um nome valido.");
        } else {
            return true;
        }
    }

    public Boolean isTipoCaracter(TipoPapel tipoPapel) {
        char[] charSearch = {'[', '@', '_', '!', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '?', '/', '|', '}', '{', '~', ':', ']'};
        for (int i = 0; i < tipoPapel.getTipo().length(); i++) {
            char chr = tipoPapel.getTipo().charAt(i);
            for (int j = 0; j < charSearch.length; j++) {
                if (charSearch[j] == chr) {
                    throw new RuntimeException("O nome fornecido não é valido, favor insira um nome sem caracter especial.");
                }
            }
        }
        return true;
    }

    public boolean validarCadastro(TipoPapel tipoPapel){
        if(this.isTipoNotNull(tipoPapel) == true &&
                this.isTipoCaracter(tipoPapel) == true)
        {
            return true;
        }else{
            return false;
        }
    }
}
