package com.tp1;

import java.util.ArrayList;

public class Bar extends Thread{
    private int numCliente;
    private int numGarcom;
    private int capacidadeGarcom;
    private int numRodadas;
    private int contadorDeAtendimento;
    private boolean aberto;
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<Garcom> garcoms = new ArrayList<>();
    public Bar(int numCliente, int numGarcom, int capacidadeGarcom, int numRodadas){
        this.numCliente = numCliente;
        this.numGarcom = numGarcom;
        this.capacidadeGarcom = capacidadeGarcom;
        this.numRodadas = numRodadas;
        this.contadorDeAtendimento = 0;
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
                    System.out.println("############");
                    System.out.println("Começando nova rodada!!!");
                    System.out.println("############");
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
        for (Cliente cliente: this.clientes) {
            if(cliente.getFezPedido() == false){
                return cliente;
            }
        }
        return null;
    }
    public ArrayList<Cliente> getClientes(){
        return this.clientes;
    }
    public boolean controleRodada(){
        System.out.print("");
        if(contadorDeAtendimento >= numCliente){
            System.out.println("teste");
            numRodadas--;
            contadorDeAtendimento = 0;
            return true;
        }
        return false;
    }
    public synchronized void incContadorDeAtendimento(){
        this.contadorDeAtendimento++;
    }
    public int getContadorDeAtendimento(){
        return this.contadorDeAtendimento;
    }
    public boolean isAberto() {
        return aberto;
    }
}
