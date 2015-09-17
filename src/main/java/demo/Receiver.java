package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class Receiver {
    private final static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @JmsListener(destination = "mailbox-destination", containerFactory = "myJmsContainerFactory")
    public void receiveMessage(PingDocument message) {
        LOG.warn("Received <{}", message);
    }
}
