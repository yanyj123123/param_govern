package com.ghrk.consumer.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfiguration {

    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String formatValue;

    @Bean(name = "format")
    public DateTimeFormatter format() {
        return DateTimeFormatter.ofPattern(formatValue);
    }

    @Bean
    public ObjectMapper serializingObjectMapper(@Qualifier("format") DateTimeFormatter format) {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(serializerAndDeserializerModule());
        return mapper;
    }

    /**
     * 序列化与反序列化
     * @return SimpleModule
     */
    public SimpleModule serializerAndDeserializerModule(){
        SimpleModule module = new SimpleModule();

        module.addDeserializer(Long.class, LongJsonDeserializer.instance);
        module.addDeserializer(Long.TYPE, LongJsonDeserializer.instance);

        module.addDeserializer(Integer.class, IntegerJsonDeserializer.instance);
        module.addDeserializer(Integer.TYPE, IntegerJsonDeserializer.instance);

        return module;
    }

    public static class LongJsonDeserializer extends JsonDeserializer<Long> {
        public static final LongJsonDeserializer instance = new LongJsonDeserializer();
        @Override
        public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String value = jsonParser.getText();
            try {
                return value == null ? null : Long.parseLong(value);
            } catch (NumberFormatException e) {
                throw e;
            }
        }
    }

    public static class IntegerJsonDeserializer extends JsonDeserializer<Integer> {
        public static final IntegerJsonDeserializer instance = new IntegerJsonDeserializer();
        @Override
        public Integer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String value = jsonParser.getText();
            try {
                return value == null ? null : Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw e;
            }
        }
    }
}
