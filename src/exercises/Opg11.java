package exercises;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Opg11 {

    Lock lock1 = new ReentrantLock();
    Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        Opg11 opg11 = new Opg11();
        opg11.start();
    }

    public void start(){
        ExecutorService executor = Executors.newCachedThreadPool();
        System.out.println("program begun");
        for (int i = 0; i < 10; i++) {
            executor.execute(new MyTask(1));
            executor.execute(new MyTask(2));
        }
        executor.shutdown();
        while (!executor.isTerminated()) { }
        System.out.println("program ended");
    }

    private class MyTask implements Runnable{

        private int methodToRun;

        MyTask(int n){
            methodToRun=n;
        }
        @Override
        public void run() {
            if (methodToRun==1){
                System.out.println("im "+methodToRun+"!");
                synchronized (lock1) {
                    lock2.lock();
                    System.out.println("thread"+methodToRun + " har fået låst lock2.");
                    synchronized (lock2) {
                        lock1.lock();
                        System.out.println("thread"+methodToRun + " har fået låst lock1.");
                        lock1.unlock();
                        lock2.unlock();
                    }
                }
            } else {
                System.out.println("im "+methodToRun+"!");
                synchronized (lock2) {
                    lock1.lock();
                    System.out.println("thread"+methodToRun + " har fået låst lock2.");
                    synchronized (lock1) {
                        lock2.lock();
                        System.out.println("thread"+methodToRun + " har fået låst lock1.");
                        lock2.unlock();
                        lock1.unlock();
                    }
                }
            }
        }
    }
}
