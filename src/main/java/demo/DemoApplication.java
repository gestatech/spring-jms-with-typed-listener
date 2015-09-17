package demo;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableJms
@EnableScheduling
public class DemoApplication {


    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

    }

    @Scheduled(fixedRate = 5000)
    public void start() {
        MessageCreator messageCreator = session -> {
            try {
                String message = objectMapper.writeValueAsString(new PingDocument("ping"));
                System.out.println("::: " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(message));
                return session.createTextMessage(message);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("json error", e);
            }
        };
        System.out.println("Sending a new message.");
        jmsTemplate.send("mailbox-destination", messageCreator);
    }
}
