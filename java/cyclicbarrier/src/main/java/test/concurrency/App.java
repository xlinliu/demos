package test.concurrency;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        Demo1 demo1 = new Demo1();
//        demo1.executeDirectly();
//        demo1.executeWithExcutor();
        demo1.showInfThreadWhenDirectly();
//        demo1.showInfThreadWhenWithExecutor();
    }
}
