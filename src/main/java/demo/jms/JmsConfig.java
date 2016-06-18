package demo.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.ConnectionFactory;

@Configuration
public class JmsConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    MessageConverter document2MessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTypeIdPropertyName("DocumentType");
        converter.setObjectMapper(objectMapper);
        return converter;

    }

    @Bean
    JmsOperations myJmsOperations(JmsTemplate jmsTemplate) {
        jmsTemplate.setMessageConverter(document2MessageConverter());
        return jmsTemplate;
    }

    @Bean
    JmsListenerContainerFactory myJmsContainerFactory(DefaultJmsListenerContainerFactoryConfigurer configurer,
                                                      ConnectionFactory connectionFactory,
                                                      PingEventErrorHandler pingEventErrorHandler) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setErrorHandler(pingEventErrorHandler);
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(document2MessageConverter());
        return factory;
    }

}
