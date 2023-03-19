package uniamerica.com.inversion.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "CARTEIRAS", schema = "public")
public class Carteira extends AbstractEntity{
}
