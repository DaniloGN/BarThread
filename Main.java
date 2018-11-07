package com.tp1;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o numero de clientes");
        int numClientes = scanner.nextInt();
        System.out.println("Digite o numero de garcons");
        int numGarcom = scanner.nextInt();
        System.out.println("Digite a capacidade dos garcons");
        int capacidadeGarcom= scanner.nextInt();
        System.out.println("Digite o numero de rodadas");
        int numRodadas = scanner.nextInt();
        Bar bar = new Bar(numClientes,numGarcom,capacidadeGarcom,numRodadas);
        bar.start();
        try {
            bar.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("############");
        System.out.println("Acabou as rodadas gratuitas!");
        System.out.println("############");
    }
}
