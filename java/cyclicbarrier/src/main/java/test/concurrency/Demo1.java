package test.concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier的回调函数直接执行，不适用线程池。
 * 循环使用n次，查看回调函数执行的次数
 */
public class Demo1 {

    private void sayHello() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("hello world");

    }

    private void process(CyclicBarrier cyclicBarrier) {
        final int n = 100;
        Runnable worker= new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < n; i++) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        int arrival_index=cyclicBarrier.await();
                        if(1==arrival_index){
                            System.out.println("last arrival Thread in this iteration is: "
                                    +Thread.currentThread().getId());
                        }
                    } catch (BrokenBarrierException | InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                System.out.println("Worker is done");
                System.out.println("Thread of Worker is "+ Thread.currentThread().getId());
            }
        };

            Thread t1 = new Thread(worker);
            Thread t2 = new Thread(worker);
            t1.start();
            t2.start();
    }

    /**
     * 执行100次CyclicBarrier
     */
    public void executeDirectly() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, this::sayHello);
        process(cyclicBarrier);

    }


    public void executeWithExcutor() {
        Executor executor = Executors.newFixedThreadPool(10);
        CyclicBarrier cyclicBarrier =new CyclicBarrier(2,() -> {
            executor.execute(this::sayHello);
        });
        process(cyclicBarrier);
    }

    public void showInfThreadWhenDirectly(){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () ->
                System.out.println("[Directly] Thread in invert call function is"
                        + Thread.currentThread().getId()));
        process(cyclicBarrier);
        System.out.println("[Directly] main Thread is "+ Thread.currentThread().getId());
    }

    public void showInfThreadWhenWithExecutor(){
        Executor executor = Executors.newFixedThreadPool(1);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () ->{
               executor.execute( () ->System.out.println("[With Executor] Thread in invert call function is"
                        + Thread.currentThread().getId()));

        });
        process(cyclicBarrier);
        System.out.println("[With Executor] main Thread is "+ Thread.currentThread().getId());
    }
}
