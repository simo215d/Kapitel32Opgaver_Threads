package listings;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SyncInstanceMethod {

    private Car car = new Car();

    public void start(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new MyTask(1));
        executorService.execute(new MyTask(2));
        executorService.shutdown();
        while (!executorService.isTerminated()){}
        System.out.println("program done!");
    }

    public static void main(String[] args) {
        SyncInstanceMethod syncInstanceMethod = new SyncInstanceMethod();
        syncInstanceMethod.start();
    }

    private class MyTask implements Runnable{

        private int id;

        private MyTask(int id){
            this.id=id;
        }
        @Override
        public void run() {
            synchronized (car){
                System.out.println("im task: "+id+" and i locked the car");
                car.setWheels(30);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    System.out.println("im task: "+id+" and i un-locked the car");
                }
            }
        }
    }

    private class Car{
        public int getWheels() {
            return wheels;
        }

        public void setWheels(int wheels) {
            this.wheels = wheels;
        }

        private int wheels;
    }
}
