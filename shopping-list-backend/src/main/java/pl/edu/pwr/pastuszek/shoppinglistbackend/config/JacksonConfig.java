package pl.edu.pwr.pastuszek.shoppinglistbackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;

public class JacksonConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
//                .disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
                .build();
    }
}
  