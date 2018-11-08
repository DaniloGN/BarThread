package com.tp1;

import java.util.ArrayList;

public class Garcom extends Thread {
    private int id;
    private int capacidade;
    private Bar bar;
    private ArrayList<Cliente> pedidos = new ArrayList<Cliente>();
    Object monitor;
    public Garcom (int id, int capacidade, Bar bar, Object monitor) {
        this.id = id;
        this.capacidade = capacidade;
        this.bar = bar;
        this.monitor = monitor;
    }
    @Override
    public void run() {
        while (bar.getNumRodadas() > 0) {
            while (pedidos.size() < capacidade) {
                Cliente cliente = bar.getProximoCliente();
                if(cliente==null && pedidos.isEmpty()) {
                    synchronized (bar.getMonitorRodada()) {
                        try {
                            sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        bar.getMonitorRodada().notifyAll();
                    }
                }
                else if (cliente == null && !pedidos.isEmpty()) {
                    entregaPedidos();
                }
                else {
                    registraPedidos(cliente);
                }
            }
                System.out.println("Sou o garcom " + this.id + " e vou começar a entregar os pedidos!");
                entregaPedidos();
            }
        }
    public void entregaPedidos() {
        while(!pedidos.isEmpty()) {
            if (pedidos.get(0) != null) {
                Cliente cliente = pedidos.remove(0);
                synchronized (cliente.getMonitor()) {
                    System.out.println("Sou o garcom " + this.id + " e entreguei o pedido do cliente " + cliente.getClienteId());
                    cliente.getMonitor().notify();
                }
            }
            else{
                    pedidos.remove(0);
            }
        }
    }

    public void registraPedidos(Cliente cliente) {
        synchronized (cliente.getMonitor()) {
            cliente.setFezPedido(true);
            System.out.println("Sou o garcom " + this.id + " e anotei o pedido do Cliente " + cliente.getClienteId());
            cliente.getMonitor().notify();
            pedidos.add(cliente);
        }
    }
}
