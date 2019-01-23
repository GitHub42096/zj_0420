package utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Runnable a = new Runnable() {
            @Override
            public void run() {
                System.out.println("a");
            }
        };
        Runnable b = new Runnable() {
            @Override
            public void run() {
                System.out.println("b");
            }
        };
        for (int i = 0; i < 1000 ; i ++){
            executorService.execute(a);
            executorService.execute(b);
        }

        executorService.shutdown();



        }
}
