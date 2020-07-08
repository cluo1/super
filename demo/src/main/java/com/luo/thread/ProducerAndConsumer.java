package com.luo.thread;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerAndConsumer {

    private final Integer max_len = 10;
    private Queue queue = new LinkedList<Integer>();

    class Producer implements Runnable{

        @Override
        public void run() {
            producer();
        }
        private void producer(){
            while (true){
                synchronized (queue){
                    if (queue.size()==max_len){
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.add(1);
                    queue.notify();

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
   class Consumer implements Runnable{
        private void consumer(){
            while (true){
                synchronized (queue){
                    if(queue.size()==0){
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        queue.poll();
                        queue.notify();

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }
        }

       @Override
       public void run() {
           consumer();
       }
   }

    public static void main(String[] args) {
        ProducerAndConsumer producerAndConsumer = new ProducerAndConsumer();

        Producer producer = producerAndConsumer.new Producer();
        Consumer consumer = producerAndConsumer.new Consumer();
        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
