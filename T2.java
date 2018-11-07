package com.tp1;

class T2 extends Thread{
    Object monitor;
    public T2(Object ob){
        monitor = ob;
    }

    @Override
    public void run() {
        synchronized (monitor) {
            System.out.println("teste3");
            monitor.notify();

            System.out.println("teste4");
        }
    }
}
