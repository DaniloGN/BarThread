package com.tp1;

import java.util.Random;

public class Cliente extends Thread {
    private int id;
    private boolean fezPedido;
    private boolean atendidoRodada;
    MonitorCliente monitor;
    Bar bar;
    public Cliente (int id, MonitorCliente monitor, Bar bar) {
        this.id = id;
        this.fezPedido = false;
        this.monitor = monitor;
        this.atendidoRodada = false;
        this.bar = bar;
    }
    @Override
    public void run() {
        while (fezPedido == false && bar.isAberto() && this.atendidoRodada==false){
            fazPedido();
        }
    }

    public void fazPedido() {
        synchronized (monitor) {
            System.out.println("Sou o cliente "+id+ " e estou esperando para fazer o pedido!");
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            esperaPedido();
        }
    }
    public void esperaPedido() {
        synchronized (monitor) {
            System.out.println("Sou o cliente "+id+ " e estou esperando meu pedido!");
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            recebePedido();
        }
    }
    public void recebePedido(){
        System.out.println("Sou o cliente "+ this.id+ "e recebi meu pedido");
        consomePedido();
    }
    public void consomePedido(){
        synchronized (this) {
            try {
                wait(new Random().nextInt(1000) + 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Sou o cliente "+id+" e terminei de consumir");
        this.fezPedido = false;
    }

    public void setFezPedido(boolean pedido){
        this.fezPedido = pedido;
    }
    public int getClienteId(){
        return this.id;
    }

    public synchronized MonitorCliente getMonitor() {
        return this.monitor;
    }

    public void setAtendidoRodada(boolean atendidoRodada) {
        this.atendidoRodada = atendidoRodada;
    }
    public boolean getAtendidoRodada(){
        return this.atendidoRodada;
    }
}