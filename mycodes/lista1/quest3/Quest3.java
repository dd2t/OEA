package com.company.quest3;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Quest3
{
    /*
        n1 = cpf1.length
        n2 = cpf2.length
        lg -> log na base 2
        Eu acho que a complexidade é O(n1 . lg n2)
    */
    public static void main (String[] args) {
        argsNotNull(args);

        File arq1 = new File(args[0]);
        File arq2 = new File(args[1]);

        try {
            Scanner myReader1 = new Scanner(arq1);
            Scanner myReader2 = new Scanner(arq2);

            // Colocar todos os cpfs do arq2 na memória
            // Para poder fazer a busca binária
            List<Integer> cpfList2 = new ArrayList<>();
            while (myReader2.hasNextLine()) {
                String data = myReader2.nextLine();
                String[] a = data.split(",");
                cpfList2.add(Integer.parseInt(a[0]));
            }

            while (myReader1.hasNextLine()) {
                String data = myReader1.nextLine();
                String[] a = data.split(",");

                // Busca binária no arq2
                if (bsearch(Integer.parseInt(a[0]), cpfList2)) {
                    System.out.println("CPF: " + a[0] + "\t" + "E-mail: " + a[1]);
                }
            }
            myReader1.close();

        }
        catch (IOException ex) {
            System.err.println("Erro na manipulação dos arquivos");
            ex.printStackTrace();
            System.exit(1);
        }

    }
    public static void argsNotNull(String[] args) {
        if (args[0] == null || args[1] == null)
        {
            System.err.println("Sem argumentos necessários");
            System.exit(1);
        }
    }

    public static boolean bsearch (int target, List<Integer> list) {
        int aux = 0;

        if (list.size() == 1) {
            aux = list.get(0);
            return aux == target;
        }

        int midList = list.size() / 2;
        aux = list.get(midList);

        if (target == aux) {
            return true;
        }
        else if (target < aux) {
            return bsearch(target, list.subList(0, midList));
        }
        else {
            return bsearch(target, list.subList(midList, list.size()));
        }
    }
}
