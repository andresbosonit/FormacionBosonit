package block17batch.block17batch.processor;

import block17batch.block17batch.entities.Tiempo;
import block17batch.block17batch.entities.TiempoRiesgo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class TiempoRiesgoItemProcessor implements ItemProcessor<Tiempo,TiempoRiesgo> {
    private static final Logger log = LoggerFactory.getLogger(TiempoItemProcessor.class);
    @Override
    public TiempoRiesgo process(Tiempo tiempo) throws Exception {
        log.info("--> Calculando el riesgo de cada registro");
        TiempoRiesgo tiempoRiesgo = new TiempoRiesgo();
        tiempoRiesgo.setIdTiempoRiesgo(tiempo.getIdTiempo());
        tiempoRiesgo.setTiempo(tiempo);
        tiempoRiesgo.setFechaPrediccion(tiempo.getFecha());
        if(tiempo.getTemperatura() > 36) {
            tiempoRiesgo.setRiesgo("Alto");
        } else if (tiempo.getTemperatura() >= 32 && tiempo.getTemperatura() <= 36) {
            tiempoRiesgo.setRiesgo("Medio");
        } else if(tiempo.getTemperatura() < 32) {
            tiempoRiesgo.setRiesgo("Bajo");
        }
        tiempoRiesgo.setTiempo(tiempo);
        return tiempoRiesgo;
    }
}
