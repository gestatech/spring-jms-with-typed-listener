package demo;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.lang.invoke.MethodHandles;

@SpringBootApplication
@EnableJms
@EnableScheduling
@Slf4j
public class DemoApplication {

    @Autowired
    JmsOperations myJmsOperations;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Scheduled(fixedRate = 5000)
    public void start() throws Exception {
        log.info("Sending a new message. ");
        myJmsOperations.convertAndSend("mailbox-destination", new PingDocument("ping"));
    }
}
