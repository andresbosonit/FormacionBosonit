package block17batch.block17batch.processor;

import block17batch.block17batch.entities.Tiempo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ComprobacionesItemProcessor implements ItemProcessor<Tiempo,Tiempo> {
    private static final Logger log = LoggerFactory.getLogger(TiempoItemProcessor.class);

    private final String path = "src/main/resources/registros_erroneos.csv";

    private BufferedWriter writer;

    private int registrosFallidos = 0;
    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        try {
            writer = new BufferedWriter(new FileWriter(path, false));
        } catch (IOException e) {
            log.error("No se ha podido abrir el fichero csv");
        }
    }

    @Override
    public Tiempo process(Tiempo tiempo) {
        log.info("--> Comprobando registro de tiempo: " + tiempo);
        if (tiempo.getTemperatura() >= 50 || tiempo.getTemperatura() <= -20) {
            this.registrosFallidos++;
            try {
                writer.write(tiempo.getLocalidad() + ", " + tiempo.getFecha() + ", " + tiempo.getTemperatura());
                writer.newLine();
            } catch (IOException e) {
                log.error("Error al escribir en el archivo de errores: " + e.getMessage());
            }
        }
        if(registrosFallidos == 100){
            log.info("--> Se ha alcanzado el número de 100 registros fallidos, cerrando el programa.");
            cerrarFichero();
            System.exit(1);
        }
        return null;
    }

    @AfterStep
    public void afterStep() {
        cerrarFichero();
    }

    public void cerrarFichero(){
        try {
            writer.close();
        } catch (IOException e) {
            log.error("Error al cerrar el archivo: " + e.getMessage());
        }
    }
}
