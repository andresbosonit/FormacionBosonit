package block17batch.block17batch;

import javax.sql.DataSource;

import block17batch.block17batch.entities.Resultado;
import block17batch.block17batch.entities.Tiempo;
import block17batch.block17batch.entities.TiempoRiesgo;
import block17batch.block17batch.listener.JobListener;
import block17batch.block17batch.mapper.ResultadoRowMapper;
import block17batch.block17batch.mapper.TiempoRowMapper;
import block17batch.block17batch.processor.ComprobacionesItemProcessor;
import block17batch.block17batch.processor.ResultadoItemProcessor;
import block17batch.block17batch.processor.TiempoItemProcessor;
import block17batch.block17batch.processor.TiempoRiesgoItemProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private int ultimaFila = 0;

    // Tiempo
    @Bean
    public FlatFileItemReader<Tiempo> tiempoReader() {
        log.info("--> Iniciando lectura de registros desde el archivo sample-data.csv");
        return new FlatFileItemReaderBuilder<Tiempo>()
                .name("tiempoReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names(new String[]{"localidad", "fecha", "temperatura"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Tiempo>() {{
                    setTargetType(Tiempo.class);
                }})
                .build();
    }

    @Bean
    public ComprobacionesItemProcessor comprobacionesItemProcessor() {
        return new ComprobacionesItemProcessor();
    }
    @Bean
    public TiempoItemProcessor tiempoProcessor() {
        return new TiempoItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Tiempo> tiempoWriter(DataSource dataSource) {
        log.info("--> Insertando los datos de sample-data.csv en la base de datos");
        ultimaFila = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tiempo", Integer.class);
        return new JdbcBatchItemWriterBuilder<Tiempo>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO tiempo (fecha, localidad, temperatura) VALUES (:fecha, :localidad, :temperatura)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importTiempoJob(JobListener listener, Step tiempoStep1, Step comprobacionesStep) {
        return jobBuilderFactory.get("importTiempoJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(comprobacionesStep)
                .next(tiempoStep1)
                .end()
                .build();
    }

    @Bean
    public Step comprobacionesStep(JdbcBatchItemWriter<Tiempo> writer) {
        return stepBuilderFactory.get("comprobacionesStep")
                .<Tiempo, Tiempo> chunk(10)
                .reader(tiempoReader())
                .processor(comprobacionesItemProcessor())
                .writer(writer)
                .build();
    }

    @Bean
    public Step tiempoStep1(JdbcBatchItemWriter<Tiempo> writer) {
        return stepBuilderFactory.get("tiempoStep1")
                .<Tiempo, Tiempo> chunk(10)
                .reader(tiempoReader())
                .processor(tiempoProcessor())
                .writer(writer)
                .build();
    }

    // Tiempo riesgo

    @Bean
    public JdbcCursorItemReader<Tiempo> tiempoReaderDataBase(){
        log.info("--> Lectura de datos tiempo nuevos que se añaden a la base de datos");
        JdbcCursorItemReader<Tiempo> reader = new JdbcCursorItemReader<>();
        reader.setSql("SELECT t.* FROM tiempo t WHERE t.id_tiempo > " + ultimaFila);
        reader.setDataSource(dataSource);
        reader.setFetchSize(100);
        reader.setRowMapper(new TiempoRowMapper());
        return reader;
    }

    @Bean
    public TiempoRiesgoItemProcessor tiempoRiesgoProcessor() {
        return new TiempoRiesgoItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<TiempoRiesgo> tiempoRiesgoWriter(DataSource dataSource) {
        log.info("--> Insertando los datos de tiempo en la tabla tiempo-riesgo");

        return new JdbcBatchItemWriterBuilder<TiempoRiesgo>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO tiempo_riesgo (fecha_prediccion, riesgo, id_tiempo) VALUES (:fechaPrediccion, :riesgo, :tiempo.idTiempo)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importTiempoRiesgoJob(JobListener listener, Step tiempoRiesgoStep1) {
        return jobBuilderFactory.get("importTiempoRiesgoJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(tiempoRiesgoStep1)
                .end()
                .build();
    }

    @Bean
    public Step tiempoRiesgoStep1(JdbcBatchItemWriter<TiempoRiesgo> writer) {
        return stepBuilderFactory.get("tiempoRiesgoStep1")
                .<Tiempo, TiempoRiesgo> chunk(10)
                .reader(tiempoReaderDataBase())
                .processor(tiempoRiesgoProcessor())
                .writer(writer)
                .build();
    }

    // Resultado
    @Bean
    public JdbcCursorItemReader<Resultado> agrupandoDatos(){
        log.info("--> Agrupando los datos de la BD");
        jdbcTemplate.update("DELETE FROM Resultado");
        jdbcTemplate.update("ALTER TABLE Resultado ALTER COLUMN id_resultado RESTART WITH 1");
        JdbcCursorItemReader<Resultado> reader = new JdbcCursorItemReader<>();
        String sql = "SELECT YEAR(t.fecha) as anio,localidad, MONTH(t.fecha) as mes,count(*) as numero_mediciones,riesgo,AVG(t.temperatura) as temperatura_media FROM tiempo t " +
                "JOIN tiempo_riesgo tr ON t.id_tiempo = tr.id_tiempo " +
                "GROUP BY t.localidad, MONTH(t.fecha)";
        reader.setSql(sql);
        reader.setDataSource(dataSource);
        reader.setFetchSize(100);
        reader.setRowMapper(new ResultadoRowMapper());
        return reader;
    }
    @Bean
    public ResultadoItemProcessor resultadoProcessor() {
        return new ResultadoItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Resultado> resultadoWriter(DataSource dataSource) {
        log.info("--> Añadiendo los resultados!!");
        return new JdbcBatchItemWriterBuilder<Resultado>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO resultado (anio,localidad,mes,numero_mediciones,riesgo,temperatura_media) VALUES (:anio, :localidad, :mes, :numeroMediciones, :riesgo, :temperaturaMedia)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importResultadoJob(JobListener listener, Step resultadoStep1) {
        return jobBuilderFactory.get("importResultadoJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(resultadoStep1)
                .end()
                .build();
    }

    @Bean
    public Step resultadoStep1(JdbcBatchItemWriter<Resultado> writer) {
        return stepBuilderFactory.get("resultadoStep1")
                .<Resultado, Resultado> chunk(10)
                .reader(agrupandoDatos())
                .processor(resultadoProcessor())
                .writer(writer)
                .build();
    }
}
