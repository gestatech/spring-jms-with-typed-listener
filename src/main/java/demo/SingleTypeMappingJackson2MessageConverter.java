package demo;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.JMSException;
import javax.jms.Message;

public class SingleTypeMappingJackson2MessageConverter extends MappingJackson2MessageConverter {

    private final JavaType type;

    public SingleTypeMappingJackson2MessageConverter(Class clazz, ObjectMapper objectMapper) {
        super();
        setObjectMapper(objectMapper);
        setTargetType(MessageType.TEXT);
        this.type = objectMapper.constructType(clazz);
    }

    @Override
    protected JavaType getJavaTypeForMessage(Message message) throws JMSException {
        return type;
    }
}