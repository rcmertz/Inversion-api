package uniamerica.com.inversion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "rendimentos", schema = "public")
public class Rendimento extends AbstractEntity {

    @Getter
    @Setter
    @Column(name = "DIV_PRECO")
    private Double preco;

    @Getter
    @Setter
    @Column(name = "DIV_QTDE")
    private Double quantidade;

    @Getter
    @Setter
    @Column(name = "DIV_DATA")
    private LocalDate data;

    @Getter
    @Setter
    @Column(name = "DIV_DESCRICAO")
    private String descricao;

    public Rendimento(Double preco, Double quantidade, LocalDate data, String descricao) {
        this.preco = preco;
        this.quantidade = quantidade;
        this.data = data;
        this.descricao = descricao;
    }
}
