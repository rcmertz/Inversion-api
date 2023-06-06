package uniamerica.com.inversion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "tipo_papel", schema = "public")
public class TipoPapel extends AbstractEntity{

    @Getter @Setter
    @Column(name = "tipo")
    private String tipo;

    public TipoPapel(String tipo) {
        this.tipo = tipo;
    }
}