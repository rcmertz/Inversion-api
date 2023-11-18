package uniamerica.com.inversion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uniamerica.com.inversion.entity.Carteira;
import uniamerica.com.inversion.entity.Meta;
import uniamerica.com.inversion.entity.Operacao;
import uniamerica.com.inversion.entity.Usuario;
import uniamerica.com.inversion.repository.CarteiraRepository;
import uniamerica.com.inversion.repository.MetaRepository;
import uniamerica.com.inversion.repository.UsuarioRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class MetaService {

    @Autowired
    private MetaRepository metaRepository;

    @Autowired
    private CarteiraRepository carteiraRepository;

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
        if (this.validarRequest(meta) && this.isMetaExist(meta, usuario)) {
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
                throw new RuntimeException("Falha ao Atualizar a meta");
            }
        }else {
            throw new RuntimeException("Voce nao tem acesso a atualizar esta Meta");
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

    public Boolean validarRequest(Meta meta){
        return this.isValorCaracter(meta) &&
                this.isValorMeta(meta) &&
                this.isDataMeta(meta);
    }

    public Map<String, Double> calcularAporteNecessario(Meta meta) {
        double valorRealizado = meta.getCarteira().getValorCarteira();
        double valorMeta = meta.getValorMeta();
        LocalDate dataMeta = meta.getDataMeta();
        LocalDate hoje = LocalDate.now();

        // Calcula a diferença em anos, meses e dias
        Period periodo = Period.between(hoje, dataMeta);
        int anosRestantes = periodo.getYears();
        int mesesRestantes = periodo.getMonths();

        double rentabilidade = meta.getRentabilidade() / 100.0;

        // Converte o tempo total em meses
        int mesesTotais = anosRestantes * 12 + mesesRestantes;

        // Formula Juros Composto
        double valorAporteMensal = (valorMeta - valorRealizado) / ((Math.pow(1 + rentabilidade, mesesTotais) - 1) / rentabilidade);

        Map<String, Double> resultado = new HashMap<>();
        resultado.put("dataRestante", (double) mesesTotais); // Meses restantes
        resultado.put("aporteMensal", Math.round(valorAporteMensal * 100.0) / 100.0); //2 casas decimais
        resultado.put("valorMeta", valorMeta); // Valor da meta
        resultado.put("valorRealizado", valorRealizado); // Valor realizado
        resultado.put("rentabilidade", meta.getRentabilidade()); // Taxa de rentabilidade

        return resultado;
    }

    //** VALIDAÇÕES META **//

    public Boolean checarDono(Meta meta, Usuario usuario) {
        Optional<Meta> metaAux = this.metaRepository.findById(meta.getId());
        return metaAux.isPresent() && metaAux.get().getUsuario().getId().equals(usuario.getId());
    }

    public Boolean isValorCaracter(Meta meta) {
        char[] charSearch = {'[', '@', '_', '!', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '?', '/', '|', '}', '{', '~', ':', ']'};
        for (int i = 0; i < meta.getValorMeta().toString().length(); i++) {
            char chr = meta.getValorMeta().toString().charAt(i);
            for (int j = 0; j < charSearch.length; j++) {
                if (charSearch[j] == chr) {
                    throw new RuntimeException("O valor da meta inserido não é válido, favor insira um valor sem caracter especial.");
                }
            }
        }
        return true;
    }

    public Boolean isValorMeta(Meta meta){
        Double valorMeta = meta.getValorMeta();
        Double valorCarteira = carteiraRepository.findValorCarteiraById(meta.getCarteira().getId());
        if (valorCarteira != null && valorMeta > valorCarteira) {
            return true;
        } else {
            throw new RuntimeException("O valor inserido é menor que o valor da carteira, favor insira um valor válido na meta.");
        }
    }

    public Boolean isDataMeta(Meta meta){
        LocalDateTime dataCadastroCarteira = carteiraRepository.findCadastroCarteiraById(meta.getCarteira().getId());
        if (meta.getDataMeta().isAfter(dataCadastroCarteira.toLocalDate())) {
            return true;
        } else {
            throw new RuntimeException("A data inserida para sua meta é menor que a data de criação de sua carteira, favor insirir uma data válida na meta.");
        }
    }

    public Boolean isMetaExist(Meta meta, Usuario usuario) {
        if (meta.getDescricaoMeta() == null || meta.getDescricaoMeta().isEmpty()) {
            throw new RuntimeException("O nome da meta não foi fornecido, favor inserir um nome.");
        } else {
            Meta metaExistente = metaRepository.findByDescricaoMeta(meta.getDescricaoMeta(), usuario.getId());

            if (metaExistente != null) {
                if (metaExistente.isAtivo()) {
                    throw new RuntimeException("Já existe uma meta ativa com o mesmo nome.");
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
    }

}
