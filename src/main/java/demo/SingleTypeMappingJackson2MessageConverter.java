package demo;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;

public class SingleTypeMappingJackson2MessageConverter extends MappingJackson2MessageConverter {

    private final JavaType type;

    public SingleTypeMappingJackson2MessageConverter(Class clazz) {
        super();
        this.type = new ObjectMapper().constructType(clazz);
       }

    @Override
    protected JavaType getJavaTypeForMessage(Message message) throws JMSException {
        return type;
    }
}