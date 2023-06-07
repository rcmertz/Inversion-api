package uniamerica.com.inversion.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "DIVIDENDOS", schema = "public")
public class Dividendo extends AbstractEntity{

    @Getter @Setter
    @Column(name = "DIV_PRECO")
    private Double preco;

    @Getter @Setter
    @Column(name = "DIV_QTDE")
    private Double quantidade;

    @Getter @Setter
    @Column(name = "DIV_DATA")
    private LocalDate data;

    @Getter @Setter
    @Column(name = "DIV_DESCRICAO")
    private String descricao;

    public Dividendo(Double preco, Double quantidade, LocalDate data, String descricao) {
        this.preco = preco;
        this.quantidade = quantidade;
        this.data = data;
        this.descricao = descricao;
    }
}
