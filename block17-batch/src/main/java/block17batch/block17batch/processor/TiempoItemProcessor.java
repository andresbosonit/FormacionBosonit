package block17batch.block17batch.processor;

import block17batch.block17batch.entities.Tiempo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TiempoItemProcessor implements ItemProcessor<Tiempo,Tiempo> {
    private static final Logger log = LoggerFactory.getLogger(TiempoItemProcessor.class);

    @Override
    public Tiempo process(Tiempo tiempo) {
        log.info("--> Procesando registro de tiempo: " + tiempo);
        if (tiempo.getTemperatura() < 50 && tiempo.getTemperatura() > -20) {
            return tiempo;
        } else {
            return null;
        }
    }
}
