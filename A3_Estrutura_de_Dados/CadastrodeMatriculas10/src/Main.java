
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    /*
     * QUESTAO 10 - TABELA HASH
     *
     * Estrutura utilizada: Tabela Hash com encadeamento.
     *
     * Busca: O(1) em media.
     * Insercao: O(1) em media, considerando redimensionamentos.
     * Remocao: O(1) em media.
     * Consulta do total: O(1).
     *
     * A tabela dobra de tamanho quando o fator de carga
     * ultrapassa 0.75.
     */

    static class Aluno {

        long matricula;
        String nome;
        Aluno prox;

        Aluno(long matricula, String nome) {
            this.matricula = matricula;
            this.nome = nome;
            this.prox = null;
        }
    }

    static class TabelaHash {

        private Aluno[] tabela;
        private int quantidade;

        TabelaHash() {

            // Capacidade inicial da tabela.
            tabela = new Aluno[16];

            quantidade = 0;
        }

        // Calcula a posicao da matricula no vetor.
        private int calcularIndice(long matricula) {

            int hash = Long.hashCode(matricula);

            return Math.floorMod(hash, tabela.length);
        }

        // Procura um aluno pela matricula.
        private Aluno encontrar(long matricula) {

            int indice = calcularIndice(matricula);

            Aluno atual = tabela[indice];

            while (atual != null) {

                if (atual.matricula == matricula) {
                    return atual;
                }

                atual = atual.prox;
            }

            return null;
        }

        // Cadastra um aluno ou atualiza seu nome.
        public boolean cadastrar(long matricula, String nome) {

            int indice = calcularIndice(matricula);

            Aluno atual = tabela[indice];

            // Verifica se a matricula ja existe.
            while (atual != null) {

                if (atual.matricula == matricula) {

                    atual.nome = nome;

                    return false;
                }

                atual = atual.prox;
            }

            // Insere um novo aluno no inicio da lista.
            Aluno novo = new Aluno(matricula, nome);

            novo.prox = tabela[indice];

            tabela[indice] = novo;

            quantidade++;

            // Verifica se o fator de carga ultrapassou 0.75.
            double fatorCarga = (double) quantidade / tabela.length;

            if (fatorCarga > 0.75) {

                redimensionar();
            }

            return true;
        }

        // Busca o nome do aluno pela matricula.
        public String buscar(long matricula) {

            Aluno aluno = encontrar(matricula);

            if (aluno == null) {
                return null;
            }

            return aluno.nome;
        }

        // Remove um aluno da tabela.
        public boolean remover(long matricula) {

            int indice = calcularIndice(matricula);

            Aluno atual = tabela[indice];

            Aluno anterior = null;

            while (atual != null) {

                if (atual.matricula == matricula) {

                    // O elemento removido esta no inicio.
                    if (anterior == null) {

                        tabela[indice] = atual.prox;

                    } else {

                        // O elemento removido esta no meio
                        // ou no final da lista.
                        anterior.prox = atual.prox;
                    }

                    quantidade--;

                    return true;
                }

                anterior = atual;

                atual = atual.prox;
            }

            return false;
        }

        // Dobra a capacidade e redistribui os registros.
        private void redimensionar() {

            Aluno[] tabelaAntiga = tabela;

            tabela = new Aluno[tabelaAntiga.length * 2];

            // Redistribui todos os alunos nas novas posicoes.
            for (int i = 0; i < tabelaAntiga.length; i++) {

                Aluno atual = tabelaAntiga[i];

                while (atual != null) {

                    Aluno proximo = atual.prox;

                    int novoIndice = calcularIndice(atual.matricula);

                    atual.prox = tabela[novoIndice];

                    tabela[novoIndice] = atual;

                    atual = proximo;
                }
            }
        }

        // Retorna a quantidade de alunos cadastrados.
        public int total() {

            return quantidade;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );

        int n = Integer.parseInt(br.readLine().trim());

        TabelaHash cadastro = new TabelaHash();

        StringBuilder saida = new StringBuilder();

        for (int i = 0; i < n; i++) {

            StringTokenizer st = new StringTokenizer(br.readLine());

            String comando = st.nextToken();

            switch (comando) {

                case "CADASTRAR": {

                    long matricula = Long.parseLong(st.nextToken());

                    String nome = st.nextToken();

                    boolean cadastrado = cadastro.cadastrar(
                            matricula,
                            nome
                    );

                    if (cadastrado) {
                        saida.append("CADASTRADO\n");
                    } else {
                        saida.append("ATUALIZADO\n");
                    }

                    break;
                }

                case "BUSCAR": {

                    long matricula = Long.parseLong(st.nextToken());

                    String nome = cadastro.buscar(matricula);

                    if (nome == null) {
                        saida.append("NAO ENCONTRADO\n");
                    } else {
                        saida.append(nome).append("\n");
                    }

                    break;
                }

                case "REMOVER": {

                    long matricula = Long.parseLong(st.nextToken());

                    boolean removido = cadastro.remover(matricula);

                    if (removido) {
                        saida.append("REMOVIDO\n");
                    } else {
                        saida.append("NAO ENCONTRADO\n");
                    }

                    break;
                }

                case "TOTAL": {

                    saida.append(cadastro.total()).append("\n");

                    break;
                }
            }
        }

        System.out.print(saida);
    }
}