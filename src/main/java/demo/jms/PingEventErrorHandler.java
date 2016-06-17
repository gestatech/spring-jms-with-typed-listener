package demo.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Slf4j
@Component
public class PingEventErrorHandler implements ErrorHandler {

    public void handleError(Throwable t) {
        if (log.isDebugEnabled()) {
            log.error("On Event {}", t);
        } else {
            log.error("On Event {}", t.getMessage());
        }
    }
}
