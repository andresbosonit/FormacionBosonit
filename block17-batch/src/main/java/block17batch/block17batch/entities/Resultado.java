package block17batch.block17batch.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idResultado;
    private String localidad;
    private Integer mes;
    private Integer anio;
    private Integer numeroMediciones;
    private float temperaturaMedia;
    private String riesgo;
}
