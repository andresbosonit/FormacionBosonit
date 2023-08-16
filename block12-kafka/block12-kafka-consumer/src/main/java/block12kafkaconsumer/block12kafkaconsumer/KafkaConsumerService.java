package block12kafkaconsumer.block12kafkaconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "consumer_topic";

    @KafkaListener(topics = "producer_topic", groupId = "group_id")
    public void consume(String message) {
        System.out.println("Mensaje consumido: " + message);
        kafkaTemplate.send(TOPIC, "Respuesta para el mensaje: " + message);
    }
}
