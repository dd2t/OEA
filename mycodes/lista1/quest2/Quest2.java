import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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
        InputStream entrada = null;
        File saida = null;
        byte buffer[] = new byte[92];
        int qtd;

        // Chamada do comando
        if (args.length != 2) {
            if (args.length != 2) {
                System.err.println("Erro: Chamada incorreta do comando.");
                // Nas versões mais recentes do java deixar o trabalho de compilar para ele.
                System.err.println("Uso correto: java Quest22.java [ARQUIVO ALVO] [ARQUIVO DESTINO].");
                System.exit(1);
            }
        }

        // Abrindo o arquivo alvo e destino
        try {
            entrada = new FileInputStream(args[0]);
        }
        catch (IOException ex) {
            System.err.println("Erro: Não foi possível abrir o "+ args[0] +" corretamente.");
            System.exit(1);
        }
        try {
            saida = new File(args[1]);
        }
        catch (Exception ex) {
            System.err.println("Erro: Não foi possível abrir o "+ args[1] +" corretamente.");
            ex.printStackTrace();
            System.exit(1);
        }

        try {
            qtd = entrada.read(buffer);
            String aux;

            // Checar se o arquivo está vazio
            if (qtd < 1) {
                System.out.println("Alerta: Arquivo alvo vazio");
                System.exit(0);
            }

            // Cria o primeiro candidato e captura o segundo
            aux = new String(buffer);
            Candidato c1 = criaCandidato(aux);
            Candidato c2;

            do {
                qtd = entrada.read(buffer);

                if (qtd < 1) {
                    escreveCandidato(saida, c1);
                    break;
                }

                aux = new String(buffer);
                c2 = criaCandidato(aux);

                if (!c1.cpf.equals(c2.cpf)) {
                    escreveCandidato(saida, c1);
                    c1 = c2;
                }
                else {
                    if (c1.id_inscricao > c2.id_inscricao) {
                        continue;
                    }
                    else {
                        c1 = c2;
                    }
                }
            } while (qtd > 0);
        }
        catch (IOException ex) {
            System.err.println("Erro: Não foi possível ler o "+ args[0] +" corretamente.");
            System.exit(1);
        }

        try {
            entrada.close();
        }
        catch (IOException ex) {
            System.err.println("Erro: Não foi possível fechar o arquivo alvo corretamente.");
            System.exit(1);
        }
    }

    public static Candidato criaCandidato(String a) {
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

    public static void escreveCandidato (File f, Candidato c){
        try {
            RandomAccessFile raf = new RandomAccessFile(f, "rw");
            Charset enc = StandardCharsets.UTF_8;
            raf.seek(raf.length());

            raf.write(String.format("%04d", c.id_inscricao).getBytes(enc));
            raf.write(c.curso.getBytes(enc));
            raf.write(c.cpf.getBytes(enc));
            raf.write(c.dataNascimento.getBytes(enc));
            raf.write(c.sexo.getBytes(enc));
            raf.write(c.email.getBytes(enc));
            raf.write(c.opcaoQuadro.getBytes(enc));

            raf.close();
        }
        catch (IOException e) {
            System.err.println("Erro: Não possível escrever no arquivo destino com exito.");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
