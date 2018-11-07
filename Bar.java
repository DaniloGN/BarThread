package com.tp1;

import java.util.ArrayList;

public class Bar extends Thread{
    private int numCliente;
    private int numGarcom;
    private int capacidadeGarcom;
    private int numRodadas;
    private boolean aberto;
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<Cliente> clientesAtendidos = new ArrayList<>();
    private ArrayList<Garcom> garcoms = new ArrayList<>();
    private Object monitorRodada = new Object();
    public Bar(int numCliente, int numGarcom, int capacidadeGarcom, int numRodadas){
        this.numCliente = numCliente;
        this.numGarcom = numGarcom;
        this.capacidadeGarcom = capacidadeGarcom;
        this.numRodadas = numRodadas;
        this.aberto = true;
    }
    @Override
    public void run(){
        System.out.println("############");
        System.out.println("O bar começou o atendimento");
        System.out.println("############");
        for(int i=0;i<numGarcom;i++){
            garcoms.add(new Garcom(i,capacidadeGarcom,this,new Object()));
        }
        for(int i=0;i<numCliente;i++){
            clientes.add(new Cliente(i,new MonitorCliente(i),this));
        }
        System.out.println("clientes estao ficando sedentos");
        for (int i=0;i<numCliente;i++) {
            clientes.get(i).start();
        }
        for (int i=0;i<numGarcom;i++) {
                garcoms.get(i).start();
            }
        while(numRodadas>0){
                boolean teste = controleRodada();
                if(teste == true && numRodadas>0){
                    synchronized (monitorRodada) {
                        try {
                            monitorRodada.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        clientes.addAll(clientesAtendidos);
                        clientesAtendidos.removeAll(clientesAtendidos);
                        System.out.println("############");
                        System.out.println("Começando nova rodada!!!");
                        System.out.println("############");
                    }
                }
            }
        if(numRodadas==0){
            this.aberto = false;
        }
    }


    public int getNumRodadas() {
        return numRodadas;
    }

    synchronized public Cliente getProximoCliente() {
        if(!clientes.isEmpty()) {
            Cliente cliente = clientes.remove(0);
            clientesAtendidos.add(cliente);
            return cliente;
        }
        else {
            return null;
        }
    }
    public synchronized ArrayList<Cliente> getClientes(){
        return this.clientesAtendidos;
    }
    public boolean controleRodada(){
        System.out.print("");
        if(clientes.isEmpty()){
            System.out.println("teste");
            numRodadas--;
            return true;
        }
        return false;
    }
    public boolean isAberto() {
        return aberto;
    }
}
