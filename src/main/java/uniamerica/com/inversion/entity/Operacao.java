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
@Table(name = "operacao", schema = "public")
public class Operacao  extends AbstractEntity{

    @Getter @Setter
    @Column(name = "valor")
    private String valor;

    @Getter @Setter
    @Column(name = "data")
    private LocalDate data;

    @Getter @Setter
    @Column(name = "tipo_operacao")
    private TipoOperacao tipoOperacao;
}
