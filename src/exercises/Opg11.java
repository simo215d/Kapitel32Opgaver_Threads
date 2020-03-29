package exercises;

public class Opg11 {

    private static Object spoon = new Object();
    private static Object bowl = new Object();

    public static void main(String[] args) {
        Thread cook1 = new Thread(() -> {
            synchronized (spoon){
                System.out.println("cook 1 has locked spoon");
                System.out.println("cook 1 is waiting for bowl to be unlocked");
                synchronized (bowl){
                    System.out.println("cook 1 has locked bowl");
                }
            }
        });
        Thread cook2 = new Thread(() -> {
            synchronized (bowl){
                System.out.println("cook 2 has locked bowl");
                System.out.println("cook 2 is waiting for spoon to be unlocked");
                synchronized (spoon){
                    System.out.println("cook 2 has locked spoon");
                }
            }
        });
        //try to run the code many times until deadlock occurs
        //sometimes the second thread is too slow to lock the bowl
        cook1.start();
        cook2.start();
    }
}
