import message.Market_300111;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import serializer.MsgpackDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

public class CustomConsumer implements Runnable {

    Properties properties;
    ArrayList<String> topics = new ArrayList<>();

    public CustomConsumer() {
        properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.37.128:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        topics.add("mdgw");
    }


    @Override
    public void run() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(topics);
        while (true){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ZERO);
            records.forEach(System.out::println);
        }
    }

    public static void main(String[] args) {
        new Thread(new CustomConsumer()).start();
//        new Thread(new CustomConsumer()).start();
//        new Thread(new CustomConsumer()).start();
    }

}
