package uniamerica.com.inversion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "CARTEIRA", schema = "public")
public class Carteira extends AbstractEntity{

    @Getter @Setter
    @Column(name = "CAR_DESCRICAO", length = 100)
    private String descricao;

    @Getter @Setter
    @Column(name = "CAR_VALOR")
    private BigDecimal valor;

//    @Getter @Setter
//    @Column(name = "CAR_DATA_CRIACAO")
//    private LocalDate dataCriacao;

    @Getter @Setter
    @Column(name = "CAR_TIPO", length = 100)
    private String tipo;

}
