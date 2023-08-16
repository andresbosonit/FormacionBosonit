package block12kafkaproducer.block12kafkaproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        kafkaTemplate.send("test_topic", message);
    }

    @KafkaListener(topics = "consumer_topic", groupId = "producer_group_id")
    public void listenResponse(String message) {
        System.out.println("La respuesta es: " + message);
    }
}
