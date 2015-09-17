package demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.lang.invoke.MethodHandles;

@SpringBootApplication
@EnableJms
@EnableScheduling
public class DemoApplication {
    private final static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

    }

    @Scheduled(fixedRate = 5000)
    public void start() throws Exception {
        MessageCreator messageCreator = session -> {
            try {
                String message = objectMapper.writeValueAsString(new PingDocument("ping"));
                LOG.info("Message to send: {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(message));
                return session.createTextMessage(message);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("json error", e);
            }
        };
        LOG.info("Sending a new message. ");
        jmsTemplate.send("mailbox-destination", messageCreator);
    }
}
