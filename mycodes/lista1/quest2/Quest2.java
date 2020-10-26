package com.company.quest2;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Quest2 {
    static class Candidato {
        public int id_inscricao;
        public String curso;
        public String cpf = "none";
        public String dataNascimento;
        public String sexo;
        public String email;
        public String opcaoQuadro;
    }

    public static void main (String[] args) {
        File entrada = new File("/home/dd2t/IdeaProjects/lista-1-oea/src/com/company/quest2/lista-candidatos.dat");
        File saida = new File("/home/dd2t/IdeaProjects/lista-1-oea/src/com/company/quest2/candidatos-validos.dat");
        List<Candidato> validos = new ArrayList<>();

        try {
            Scanner myReader = new Scanner(entrada);
            List<Candidato> temp = new ArrayList<>();
            String line = myReader.nextLine();
            Candidato c1 = makeCandidato(line);

            do {
                line = myReader.nextLine();
                Candidato c2 = makeCandidato(line);

                if (!c1.cpf.equals(c2.cpf)) {
                    // Adiciona c1 aos válidos
                    validos.add(c1);
                    c1 = c2;
                }
                else {
                    do {
                        temp.add(c2);
                        if (!myReader.hasNextLine()) break;
                        line = myReader.nextLine();
                        c2 = makeCandidato(line);
                    } while (c1.cpf.equals(c2.cpf));

                    // Nesse ponto é garantido que a seq de
                    // cpfs iguais está em temp e c1 != c2
                    temp.add(c1);

                    Candidato inscricaoValida = inscricaoComMaiorId(temp);
                    // Go registro válido
                    validos.add(inscricaoValida);

                    temp.clear();

                    // Se não houver mais linhas e o ult é diff
                    if (!myReader.hasNextLine() && !c1.cpf.equals(c2.cpf)) {
                        validos.add(c2);
                    }
                    c1 = c2;
                }
            } while (myReader.hasNextLine());

        }
        catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        writeCandidato(validos, saida);
    }

    public static Candidato makeCandidato (String a) {
        String[] arr = new String[7];

        arr[0] = a.substring(0,4);
        arr[1] = a.substring(4,24);
        arr[2] = a.substring(24,39);
        arr[3] = a.substring(39,50);

        arr[4] = a.substring(50,52);
        arr[5] = a.substring(52,91);
        arr[6] = a.substring(91);

        Candidato c = new Candidato();
        c.id_inscricao = Integer.parseInt(arr[0]);
        c.curso = arr[1];
        c.cpf = arr[2];
        c.dataNascimento = arr[3];
        c.sexo = arr[4];
        c.email = arr[5];
        c.opcaoQuadro = arr[6];

        return c;
    }

    public static Candidato inscricaoComMaiorId (List<Candidato> list) {
        int maiorId = -1;
        Candidato c = null;
        for (Candidato i : list) {
            if (i.id_inscricao > maiorId) {
                c = i;
                maiorId = i.id_inscricao;
            }
        }
        return c;
    }

    public static void writeCandidato (List<Candidato> list, File f){
        try {
            RandomAccessFile raf = new RandomAccessFile(f, "rw");
            Charset enc = StandardCharsets.UTF_8;

            for (Candidato c : list) {
                raf.write(String.format("%04d", c.id_inscricao).getBytes(enc));
                raf.write(c.curso.getBytes(enc));
                raf.write(c.cpf.getBytes(enc));
                raf.write(c.dataNascimento.getBytes(enc));

                raf.write(c.sexo.getBytes(enc));
                raf.write(c.email.getBytes(enc));
                raf.write(c.opcaoQuadro.getBytes(enc));
                raf.write("\n".getBytes(enc));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
