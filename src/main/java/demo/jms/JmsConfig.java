package demo.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.PingDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import javax.jms.ConnectionFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JmsConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    MappingJackson2MessageConverter pingDocumentMappingJackson2MessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("JMSType", PingDocument.class);
        converter.setTypeIdMappings(typeIdMappings);
        converter.setTypeIdPropertyName("JMSType");
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Bean
    JmsOperations myJmsOperations(JmsTemplate jmsTemplate) {
        jmsTemplate.setMessageConverter(pingDocumentMappingJackson2MessageConverter());
        return jmsTemplate;
    }

    @Bean
    JmsListenerContainerFactory myJmsContainerFactory(DefaultJmsListenerContainerFactoryConfigurer configurer,
                                                      ConnectionFactory connectionFactory,
                                                      PingEventErrorHandler pingEventErrorHandler) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setErrorHandler(pingEventErrorHandler);
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(pingDocumentMappingJackson2MessageConverter());
        return factory;
    }

}
