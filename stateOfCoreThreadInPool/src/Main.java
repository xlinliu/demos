import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3,
                5,
                2,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10));

	executor.allowCoreThreadTimeOut(true);
	if(executor.allowsCoreThreadTimeOut()){
		System.out.println("allow core thread time out!");

	}
        executor.execute(() -> System.out.println("Hello World!"));
        executor.execute(() -> System.out.println("Hello World!"));
        executor.execute(() -> {
		while(true)
		    System.out.println("Hello World!");
	});
    }
}
