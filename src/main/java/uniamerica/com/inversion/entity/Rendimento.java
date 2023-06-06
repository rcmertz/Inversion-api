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
    @Column(name = "preco")
    private Double preco;

    @Getter
    @Setter
    @Column(name = "quantidade")
    private Double quantidade;

    @Getter
    @Setter
    @Column(name = "data")
    private LocalDate data;

    @Getter
    @Setter
    @Column(name = "descricao")
    private String descricao;

    public Rendimento(Double preco, Double quantidade, LocalDate data, String descricao) {
        this.preco = preco;
        this.quantidade = quantidade;
        this.data = data;
        this.descricao = descricao;
    }
}
