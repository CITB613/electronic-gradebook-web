package bg.nbu.gradebook.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import bg.nbu.gradebook.commons.utils.Mapper;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public Mapper mapper() {
        ModelMapper modelMapper = new ModelMapper();

        return new Mapper(modelMapper);
    }

}