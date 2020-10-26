package com.company.quest1;
import java.io.*;
public class Quest1
{
    /*
    Recebe um arquivo de texto e imprime na tela
    o número de linhas e palavras do arquivo.
    */
    public static void main (String args[])
    {
        InputStream entrada = null;
        int qtd;
        byte buffer[] = new byte[4096];
        long tempoInicial = System.currentTimeMillis();
        int linhas = 0;
        int palavras = 0;

        if (args.length != 1)
        {
            System.err.println("Erro: Chamada incorreta do comando.");
            System.err.println("Uso correto: java Quest1 [ARQUIVO ALVO]");
            System.exit(1);
        }

        try
        {
            entrada = new FileInputStream(args[0]);
        }
        catch (IOException ex)
        {
            System.err.println("Erro: Não foi possível abrir o "+ args[0] +" corretamente.");
            System.exit(1);
        }

        try
        {
            qtd = entrada.read(buffer);
            while (qtd > 0)
            {
                byte aux = buffer[0];
                for (byte i : buffer)
                {
                    /*
                      Valores dos bytes na tabela ascii (em decimal):
                         10 - end of line
                         32 - space
                         -1 - eof
                    */

                    // Se tiver "\n" soma linha.
                    if (i == 10)
                    {
                        linhas++;
                    }

                    // Detectando palavras
                    if (i == 32 && (aux != 32 && aux != 10))
                    {
                        palavras++;
                    }
                    else if (i == 10 && (aux != 32 && aux != 10))
                    {
                        palavras++;
                    }

                    aux = i;
                }
                qtd = entrada.read(buffer);
            }

            entrada.close();
        }
        catch (IOException ex)
        {
            System.err.println("Erro: Não foi possível ler o "+ args[0] +" corretamente.");
        }

        long tempoFinal = System.currentTimeMillis();

        System.out.println("Número de linhas: " + linhas);
        System.out.println("Número de palavras: " + palavras);
        System.out.println("Tempo da operação: " + (tempoFinal - tempoInicial) + "ms");

    }
}

