package com.tp1;

import java.util.ArrayList;

public class Garcom extends Thread {
    private int id;
    private int capacidade;
    private Bar bar;
    private ArrayList<MonitorCliente> pedidos = new ArrayList<MonitorCliente>();
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
                if (cliente == null) {
                    pedidos.add(null);
                } else {
                    registraPedidos(cliente);
                }
            }
            System.out.println("Sou o garcom " + this.id + " e vou começar a entregar os pedidos!");
            entregaPedidos(bar.getClientes());
        }
    }
    public void entregaPedidos(ArrayList<Cliente> clientes) {
        while(!pedidos.isEmpty()) {
            if (pedidos.get(0) != null) {
                MonitorCliente clienteMonitor = pedidos.remove(0);
                synchronized (clienteMonitor) {
                    clientes.get(clienteMonitor.getId()).setEstaComPedido(true);
                    System.out.println("Sou o garcom " + this.id + " e entreguei o pedido do cliente " + clienteMonitor.getId());
                    clienteMonitor.notify();
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
            pedidos.add(cliente.getMonitor());
        }
    }
}
