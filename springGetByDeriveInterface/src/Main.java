import beans.Animal;
import beans.Store;
import config.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Config.class);
        context.refresh();
        Store store=(Store)context.getBean(Store.class);
        System.out.println(store.getAnimal().getName());
    }
}
