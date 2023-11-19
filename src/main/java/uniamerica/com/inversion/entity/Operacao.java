package uniamerica.com.inversion.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "operacao", schema = "public")
public class Operacao extends AbstractEntity{

    @Getter @Setter
    @JoinColumn(name = "idUsuario")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    @Getter @Setter
    @JoinColumn(name = "idInvestimento")
    @ManyToOne(fetch = FetchType.EAGER)
    private Investimento investimento;

    @Getter @Setter
    @Column(name = "valor")
    private BigDecimal valor;

    @Getter @Setter
    @Min(1)
    @Column(name = "quantidade")
    private Integer quantidade;

    @Getter @Setter
    @Column(name = "data")
    private LocalDateTime data;

    @Getter @Setter
    @Enumerated(EnumType.STRING) // Indica que o campo será persistido como uma string
    @Column(name = "tipo")
    private TipoOperacao tipo;

    @Getter @Setter
    @Column(name = "preco_medio")
    private BigDecimal preco_medio;

    public Operacao(Usuario usuario, Investimento investimento, BigDecimal valor, Integer quantidade, LocalDateTime data, TipoOperacao tipo) {
        this.usuario = usuario;
        this.investimento = investimento;
        this.valor = valor;
        this.quantidade = quantidade;
        this.data = data;
        this.tipo = tipo;
    }
    // Método para definir o preço médio
    public void setPrecoMedio(BigDecimal precoMedio) {
        this.preco_medio = precoMedio;
    }

}
