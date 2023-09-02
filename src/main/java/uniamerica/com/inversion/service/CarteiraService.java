package uniamerica.com.inversion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uniamerica.com.inversion.entity.Carteira;
import uniamerica.com.inversion.entity.Investimento;
import uniamerica.com.inversion.entity.Usuario;
import uniamerica.com.inversion.repository.CarteiraRepository;
import uniamerica.com.inversion.repository.UsuarioRepository;

import javax.persistence.Id;
import javax.transaction.Transactional;
import java.util.Optional;


@Service
public class CarteiraService {
    @Autowired
    private CarteiraRepository carteiraRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Carteira findById(Long id, Usuario usuario){
        return this.carteiraRepository.findByIdAndUsuario(id, usuario).orElse(new Carteira());
    }

    public Page<Carteira> listAll(Pageable pageable, Usuario usuario){
        return this.carteiraRepository.findByUsuario(usuario, pageable);
    }

    @Transactional
    public void insert(Carteira carteira, Usuario usuario){
        if (this.validarRequest(carteira)  &&
            this.isCarteiraExist(carteira, usuario) ) {
            this.carteiraRepository.save(carteira);
        }else {
            throw new RuntimeException("Falha ao cadastrar uma carteira");
        }
    }

    @Transactional
    public void update (Long id, Carteira carteira, Usuario usuario){
        if (checarDono(carteira, usuario)) {
            if (id == carteira.getId() && this.validarRequest(carteira) ){
                this.carteiraRepository.save(carteira);
            }
            else{
                throw new RuntimeException("Falha ao Atualizar a Carteira");
            }
        }else {
            throw new RuntimeException("Voce nao tem acesso a atualizar esta Carteira");
        }
    }

    @Transactional
    public void desativar(Long id, Carteira carteira, Usuario usuario){
        if (checarDono(carteira, usuario)) {
            if (id == carteira.getId() && this.validarRequest(carteira) ){
                this.carteiraRepository.desativar(carteira.getId());
            }else {
                throw new RuntimeException("Falha ao Desativar a Carteira");
            }
        }else {
            throw new RuntimeException("Voce nao tem acesso a desativar esta Carteira");
        }
    }

    //** VALIDAÇÕES CARTEIRA **//

    public Boolean checarDono(Carteira carteira, Usuario usuario) {
        Optional<Carteira> carteiraAux = this.carteiraRepository.findById(carteira.getId());
        return carteiraAux.isPresent() && carteiraAux.get().getUsuario().getId().equals(usuario.getId());
    }

    public Boolean isCarteiraExist(Carteira carteira, Usuario usuario) {
        if (carteira.getDescricaoCarteira() == null || carteira.getDescricaoCarteira().isEmpty()) {
            throw new RuntimeException("O nome da Carteira não foi fornecido, favor inserir um nome.");
        } else {
            // Verificar se já existe uma carteira com o mesmo nome
            Carteira carteiraExistente = carteiraRepository.findByDescricaoCarteira(carteira.getDescricaoCarteira(), usuario.getId());

            if (carteiraExistente != null) {
                // Verificar se a carteira existente está ativa
                if (carteiraExistente.isAtivo()) {
                    throw new RuntimeException("Já existe uma carteira ativa com o mesmo nome.");
                } else {
                    // Permitir a inserção caso a carteira existente esteja inativo
                    return true;
                }
            } else {
                return true;
            }
        }
    }

    //Valida se Nome da carteira nao e vazio ou nulo
//    public Boolean isCarteiraNotNull(Carteira carteira) {
//        if (carteira.getDescricaoCarteira() == null || carteira.getDescricaoCarteira().isEmpty()) {
//            throw new RuntimeException("A descrição da carteira não foi fornecido, favor inserir uma descriçao.");
//        } else {
//            return true;
//        }
//    }

    //Valida se tem caracter especial no nome da carteira
    public Boolean isValorValid(Carteira carteira) {
        char[] charSearch = {'[', '@', '_', '!', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '?', '/', '|', '}', '{', '~', ':', ']'};
        for (int i = 0; i < carteira.getValorCarteira().toString().length(); i++) {
            char chr = carteira.getValorCarteira().toString().charAt(i);
            for (int j = 0; j < charSearch.length; j++) {
                if (charSearch[j] == chr) {
                    throw new RuntimeException("O valor inserido não é válido, favor insira um valor sem caracter especial.");
                }
            }
        }
        return true;
    }

    public boolean validarRequest(Carteira carteira){
        return this.isValorValid(carteira);
    }

}
