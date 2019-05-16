package config;

import beans.Animal;
import beans.AnimalStore;
import beans.Cat;
import beans.Store;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {Animal.class})
public class Config {
    @Bean
    public Cat c4at3(){
        return new Cat("Hello Kitty");
    }
    @Bean
    public Store store(){
        return new AnimalStore();
    }

}
