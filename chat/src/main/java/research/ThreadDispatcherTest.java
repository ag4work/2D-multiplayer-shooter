package research;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadDispatcherTest {

    int N = 9;
    AtomicInteger cntToWait = new AtomicInteger(2);
    CyclicBarrier barrier = new CyclicBarrier(3, new TwoThreadProcessor() );
    ConcurrentLinkedDeque<String> threads = new ConcurrentLinkedDeque<>();


    public void register() throws BrokenBarrierException, InterruptedException {
        String name = Thread.currentThread().getName();
        System.out.println(name + " came to register. Left to wait:" + cntToWait.getAndDecrement());
        threads.addFirst(name);
        barrier.await();
    }


    public static void main(String[] args) {
        new ThreadDispatcherTest().runThreads();
    }

    private void runThreads() {
        for (int i = 0; i < N; i++) {
            runThread();
        }

    }

    private void runThread() {
        new Thread() {
            @Override
            public void run() {
                try {
                    register();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    class TwoThreadProcessor implements Runnable{
        @Override
        public void run() {
            System.out.println("Process:" + threads.removeLast() + ", " + threads.removeLast() +
                    ", " + threads.removeLast());
            cntToWait.set(2);
        }
    }
}
