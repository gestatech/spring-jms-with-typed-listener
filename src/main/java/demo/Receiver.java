package demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Receiver {

    @JmsListener(destination = "mailbox-destination", containerFactory = "myJmsContainerFactory")
    public void receiveMessage(PingDocument message) {
        log.info("Received <{}", message);
    }
}
