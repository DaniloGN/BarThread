package com.tp1;

import java.util.Random;

public class Cliente extends Thread {
    private int id;
    private boolean fezPedido;
    private boolean estaComPedido;
    private boolean prontoParaPedir;
    MonitorCliente monitor;
    Bar bar;
    public Cliente (int id, MonitorCliente monitor, Bar bar) {
        this.id = id;
        this.fezPedido = false;
        this.estaComPedido = false;
        this.prontoParaPedir = false;
        this.monitor = monitor;
        this.bar = bar;
    }
    @Override
    public void run() {
        while (fezPedido == false && bar.isAberto()){
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
        try {
            sleep(new Random().nextInt(4000)+1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Sou o cliente "+id+" e terminei de consumir");
        this.fezPedido = false;
    }

    public boolean getFezPedido(){
        return this.fezPedido;
    }
    public void setFezPedido(boolean pedido){
        this.fezPedido = pedido;
    }
    public int getClienteId(){
        return this.id;
    }
    public void setEstaComPedido(boolean pedido){
        this.estaComPedido = pedido;
    }
    public boolean getProntoParaPedir(){
        return this.prontoParaPedir;
    }

    public synchronized MonitorCliente getMonitor() {
        return this.monitor;
    }
}