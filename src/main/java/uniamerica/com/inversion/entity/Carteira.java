package uniamerica.com.inversion.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "carteira", schema = "public")
public class Carteira extends AbstractEntity{

    @Getter @Setter
    @Column(name = "descricao", length = 100)
    private String descricao;

    @Getter @Setter
    @Column(name = "valor")
    private BigDecimal valor;

    public Carteira(String descricao, BigDecimal valor) {
        this.descricao = descricao;
        this.valor = valor;
    }
}
