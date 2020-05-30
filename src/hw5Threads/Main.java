package hw5Threads;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        MyArray arr = new MyArray();
        long a = System.currentTimeMillis();
        arr.countValue();
        System.out.println("Первый медот считает:" + (System.currentTimeMillis() - a));
        a = System.currentTimeMillis();
        arr.countValueViaSeparation();
        System.out.println("Второй медот считает:" + (System.currentTimeMillis() - a));
    }
}
