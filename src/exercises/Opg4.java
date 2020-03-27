package exercises;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Opg4 {
    private static Integer sum = 0;
    public void doShit(){
        //create threads
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            Runnable myTask = new MyTask();
            executor.execute(myTask);
        }
        executor.shutdown();
        while (!executor.isTerminated()){}
        System.out.println("Sum: "+sum);
    }

    private synchronized void addSumSync(){
        sum++;
    }

    private void addSumASync(){
        sum++;
    }

    private class MyTask implements Runnable{
        @Override
        public void run() {
            //addSumASync();
            addSumSync();
        }
    }

    public static void main(String[] args) {
        Opg4 opg4 = new Opg4();
        opg4.doShit();
    }
}
