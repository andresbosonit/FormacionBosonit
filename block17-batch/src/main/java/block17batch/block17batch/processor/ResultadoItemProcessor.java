package block17batch.block17batch.processor;

import block17batch.block17batch.entities.Resultado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class ResultadoItemProcessor implements ItemProcessor<Resultado, Resultado> {
    private static final Logger log = LoggerFactory.getLogger(ResultadoItemProcessor.class);

    @Override
    public Resultado process(Resultado resultadoInput) throws Exception {
        log.info("Calculando el riesgo según la temperatura media");
        if(resultadoInput.getTemperaturaMedia() > 36) {
            resultadoInput.setRiesgo("Alto");
        } else if (resultadoInput.getTemperaturaMedia() >= 32 && resultadoInput.getTemperaturaMedia() <= 36) {
            resultadoInput.setRiesgo("Medio");
        } else if (resultadoInput.getTemperaturaMedia() < 32) {
            resultadoInput.setRiesgo("Bajo");
        }
        return resultadoInput;
    }
}
