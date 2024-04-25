package Zadanie1;

import java.util.ArrayList;
import java.util.Random;

public class Zadanie1 {
    private static int result;
    private final int time;
    private final ArrayList<Thread> threads;

    public Zadanie1(int numberOfThreads, int time) {
        result = 0;
        this.time = time;
        threads = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < numberOfThreads; i++) {
            threads.add(new MyThread());
        }
    }

    private synchronized void changeResult(int value) {
        result += value;
        //System.out.println(result);
    }

    public void run() {
        threads.forEach(Thread::start);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threads.forEach(Thread::interrupt);

        System.out.println("Result: " + result);
    }

    private class MyThread extends Thread {
        private final int value;

        public MyThread() {
            Random random = new Random();
            this.value = random.nextInt() % 2 == 0 ? 1 : -1;
        }

        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    return;
                }

                changeResult(value);
            }
        }
    }
}
