
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    /*
     * QUESTAO 9 - HEAP
     *
     * Estrutura utilizada: Heap Maximo implementado em vetor.
     *
     * Insercao: O(log n)
     * Remocao: O(log n)
     * Consulta do proximo: O(1)
     *
     * O paciente com maior prioridade e atendido primeiro.
     * Em caso de empate, vence quem chegou primeiro.
     */

    static class Paciente {
        String nome;
        int prioridade;
        long ordemChegada;

        Paciente(String nome, int prioridade, long ordemChegada) {
            this.nome = nome;
            this.prioridade = prioridade;
            this.ordemChegada = ordemChegada;
        }
    }

    static class HeapMaximo {

        private Paciente[] heap;
        private int tamanho;

        HeapMaximo(int capacidade) {
            heap = new Paciente[capacidade];
            tamanho = 0;
        }

        // Retorna true se o primeiro paciente tiver preferencia.
        private boolean temPreferencia(Paciente a, Paciente b) {

            if (a.prioridade != b.prioridade) {
                return a.prioridade > b.prioridade;
            }

            return a.ordemChegada < b.ordemChegada;
        }

        private void trocar(int i, int j) {
            Paciente auxiliar = heap[i];
            heap[i] = heap[j];
            heap[j] = auxiliar;
        }

        // Insere um paciente e reorganiza o heap.
        public void inserir(Paciente paciente) {

            heap[tamanho] = paciente;

            int atual = tamanho;
            tamanho++;

            // Sobe o elemento enquanto ele tiver prioridade
            // sobre seu pai.
            while (atual > 0) {

                int pai = (atual - 1) / 2;

                if (temPreferencia(heap[atual], heap[pai])) {
                    trocar(atual, pai);
                    atual = pai;
                } else {
                    break;
                }
            }
        }

        // Consulta o paciente que sera atendido.
        public Paciente consultarProximo() {

            if (tamanho == 0) {
                return null;
            }

            return heap[0];
        }

        // Remove o paciente de maior prioridade.
        public Paciente removerMaximo() {

            if (tamanho == 0) {
                return null;
            }

            Paciente removido = heap[0];

            tamanho--;

            if (tamanho > 0) {

                heap[0] = heap[tamanho];
                heap[tamanho] = null;

                int atual = 0;

                // Desce o elemento para restaurar o heap.
                while (true) {

                    int esquerda = 2 * atual + 1;
                    int direita = 2 * atual + 2;

                    int maior = atual;

                    if (esquerda < tamanho &&
                            temPreferencia(heap[esquerda], heap[maior])) {

                        maior = esquerda;
                    }

                    if (direita < tamanho &&
                            temPreferencia(heap[direita], heap[maior])) {

                        maior = direita;
                    }

                    if (maior != atual) {
                        trocar(atual, maior);
                        atual = maior;
                    } else {
                        break;
                    }
                }
            } else {
                heap[0] = null;
            }

            return removido;
        }

        public boolean estaVazio() {
            return tamanho == 0;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );

        int n = Integer.parseInt(br.readLine().trim());

        HeapMaximo fila = new HeapMaximo(n);

        long ordem = 0;

        StringBuilder saida = new StringBuilder();

        for (int i = 0; i < n; i++) {

            StringTokenizer st = new StringTokenizer(br.readLine());

            String comando = st.nextToken();

            switch (comando) {

                case "CHEGAR": {

                    String nome = st.nextToken();
                    int prioridade = Integer.parseInt(st.nextToken());

                    Paciente paciente = new Paciente(
                            nome,
                            prioridade,
                            ordem
                    );

                    fila.inserir(paciente);

                    ordem++;

                    break;
                }

                case "PROXIMO": {

                    Paciente proximo = fila.consultarProximo();

                    if (proximo == null) {
                        saida.append("VAZIA\n");
                    } else {
                        saida.append(proximo.nome)
                                .append(" ")
                                .append(proximo.prioridade)
                                .append("\n");
                    }

                    break;
                }

                case "ATENDER": {

                    Paciente atendido = fila.removerMaximo();

                    if (atendido == null) {
                        saida.append("VAZIA\n");
                    } else {
                        saida.append(atendido.nome)
                                .append(" ")
                                .append(atendido.prioridade)
                                .append("\n");
                    }

                    break;
                }
            }
        }

        System.out.print(saida);
    }
}
