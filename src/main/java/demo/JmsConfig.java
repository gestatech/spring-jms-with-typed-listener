package demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;

@Configuration
public class JmsConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    SingleTypeMappingJackson2MessageConverter pingDocumentMappingJackson2MessageConverter(){
        return new SingleTypeMappingJackson2MessageConverter(PingDocument.class, objectMapper);
    };

    @Bean
    JmsListenerContainerFactory<?> myJmsContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(pingDocumentMappingJackson2MessageConverter());
        return factory;
    }

}
