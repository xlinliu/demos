package beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class AnimalStore implements Store {
    /**
     * 虽然Cat是Animal的子类，但是我们只只配置了Animal的bean，所以，这里的自动装配会失败。
     */
    @Autowired
    private Animal ca2t1;

    @Override
    public Animal getAnimal() {
        return ca2t1;
    }
}
