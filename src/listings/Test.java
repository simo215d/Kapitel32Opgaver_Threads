package listings;

public class Test implements Runnable {
    public static void main(String[] args) {
        new Test();
    }

    public Test() {
        Thread t = new Thread(this);
        t.start();
        if (t.isAlive()){
            System.out.println("t is live");
        } else t.start();
    }

    public void run() {
        System.out.println("test");
    }

}