package block17batch.block17batch.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tiempo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTiempo;
    private String localidad;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    private int temperatura;
    @OneToOne(mappedBy = "tiempo")
    private TiempoRiesgo tiempoRiesgo;

    @Override
    public String toString() {
        return "Localidad: " + this.localidad + " fecha: " + new SimpleDateFormat("dd/MM/yyyy").format(this.fecha) + " temperatura: " + this.temperatura;
    }
}
