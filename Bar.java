package com.tp1;

import java.util.ArrayList;

public class Bar extends Thread{
    private int numCliente;
    private int numGarcom;
    private int capacidadeGarcom;
    private int numRodadas;
    private boolean aberto;
    private int numAtendimentos;
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<Garcom> garcoms = new ArrayList<>();
    private Object monitorRodada = new Object();
    public Bar(int numCliente, int numGarcom, int capacidadeGarcom, int numRodadas){
        this.numCliente = numCliente;
        this.numGarcom = numGarcom;
        this.capacidadeGarcom = capacidadeGarcom;
        this.numRodadas = numRodadas;
        this.aberto = true;
        this.numAtendimentos = 0;
    }
    @Override
    public void run(){
        System.out.println("############");
        System.out.println("O bar começou o atendimento");
        System.out.println("############");
        for(int i=0;i<numCliente;i++){
            clientes.add(new Cliente(i,new MonitorCliente(i),this));
        }
        for(int i=0;i<numGarcom;i++){
            garcoms.add(new Garcom(i,capacidadeGarcom,this,new Object()));
        }
        for (int i=0;i<numCliente;i++) {
            clientes.get(i).start();
        }
        for (int i=0;i<numGarcom;i++) {
                garcoms.get(i).start();
            }
        while(numRodadas>0) {
            if (numAtendimentos >= numCliente) {
                synchronized (monitorRodada) {
                    try {
                        monitorRodada.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    for (Cliente cliente : clientes) {
                        cliente.setAtendidoRodada(false);
                    }
                    if (this.numRodadas >= 1) {
                        this.numAtendimentos = 0;
                        numRodadas--;
                        System.out.println("############");
                        System.out.println("Começando nova rodada!!!");
                        System.out.println("############");
                    }
                }
            }
        }
        this.aberto = false;
    }

    public int getNumRodadas() {
        return numRodadas;
    }

    synchronized public Cliente getProximoCliente() {
        for (Cliente cliente:clientes) {
            if(cliente.getAtendidoRodada()==false){
                numAtendimentos++;
                cliente.setAtendidoRodada(true);
                return cliente;
            }
        }
        return null;
    }
    public synchronized Object getMonitorRodada(){
        return  this.monitorRodada;
    }
    public boolean isAberto() {
        return aberto;
    }
}
