package com.tp1;

class T1 extends Thread{
    Object monitor;
    public T1(Object ob){
        monitor = ob;
    }

    @Override
    public synchronized void run() {
        synchronized (monitor) {
            System.out.println("teste1");
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("teste2");
            monitor.notify();
        }
    }
}