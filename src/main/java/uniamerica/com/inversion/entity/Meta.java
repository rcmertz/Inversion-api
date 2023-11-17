package uniamerica.com.inversion.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "meta", schema = "public")
public class Meta extends AbstractEntity{

    @Getter @Setter
    @JoinColumn(name = "idUsuario")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    @Getter @Setter
    @JoinColumn(name = "idCarteira")
    @OneToOne(fetch = FetchType.EAGER)
    private Carteira carteira;

    @Getter @Setter
    @Column(name = "descricao")
    private String descricaoMeta;

    @Getter @Setter
    @Column(name = "realizado")
    private Double realizadoMeta;

    @Getter @Setter
    @Column(name = "meta")
    private Double valorMeta;

    @Getter @Setter
    @Column(name = "data")
    private LocalDate dataMeta;

    @Getter @Setter
    @Column(name = "aporte")
    private Double valorAporte;

    @Getter @Setter
    @Min(0)
    @Column(name = "rentabilidade")
    private Double rentabilidade;

    public Meta(Usuario usuario, Carteira carteira, String descricaoMeta, Double valorMeta, LocalDate dataMeta, Double rentabilidade) {
        this.usuario = usuario;
        this.carteira = carteira;
        this.descricaoMeta = descricaoMeta;
        this.valorMeta = valorMeta;
        this.dataMeta = dataMeta;
        this.rentabilidade = rentabilidade;
    }
}
