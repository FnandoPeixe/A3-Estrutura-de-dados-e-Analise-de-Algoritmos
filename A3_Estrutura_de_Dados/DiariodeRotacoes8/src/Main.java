
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {

    // Estrutura: arvore AVL com nos e referencias.
    // Insercao: O(log N)
    // Percurso em pre-ordem: O(N)
    // Consulta da altura: O(1)

    // Representa cada no da arvore
    static class Node {

        int valor;
        int altura;

        Node esquerda;
        Node direita;

        Node(int valor) {
            this.valor = valor;
            this.altura = 0;
            this.esquerda = null;
            this.direita = null;
        }
    }

    static class ArvoreAVL {

        private Node raiz;
        private boolean repetido;
        private String caso;

        // Retorna a altura de um no
        // Uma arvore vazia possui altura -1
        private int altura(Node no) {

            if (no == null) {
                return -1;
            }

            return no.altura;
        }

        // Atualiza a altura de um no
        private void atualizarAltura(Node no) {

            no.altura = 1 + Math.max(
                    altura(no.esquerda),
                    altura(no.direita)
            );
        }

        // Calcula o fator de balanceamento
        private int fatorBalanceamento(Node no) {

            return altura(no.esquerda) - altura(no.direita);
        }

        // Rotacao simples a direita
        private Node rotacaoDireita(Node y) {

            Node x = y.esquerda;
            Node auxiliar = x.direita;

            x.direita = y;
            y.esquerda = auxiliar;

            atualizarAltura(y);
            atualizarAltura(x);

            return x;
        }

        // Rotacao simples a esquerda
        private Node rotacaoEsquerda(Node x) {

            Node y = x.direita;
            Node auxiliar = y.esquerda;

            y.esquerda = x;
            x.direita = auxiliar;

            atualizarAltura(x);
            atualizarAltura(y);

            return y;
        }

        // Insere um valor e identifica o caso corrigido
        public String inserir(int valor) {

            repetido = false;
            caso = "SEM ROTACAO";

            raiz = inserirRecursivo(raiz, valor);

            if (repetido) {
                return "REPETIDO";
            }

            return caso;
        }

        private Node inserirRecursivo(Node atual, int valor) {

            // Encontrou uma posicao vazia
            if (atual == null) {
                return new Node(valor);
            }

            // Procura a posicao correta
            if (valor < atual.valor) {

                atual.esquerda =
                        inserirRecursivo(atual.esquerda, valor);

            } else if (valor > atual.valor) {

                atual.direita =
                        inserirRecursivo(atual.direita, valor);

            } else {

                // Valores repetidos nao sao inseridos
                repetido = true;
                return atual;
            }

            // Se encontrou duplicado, nao altera a arvore
            if (repetido) {
                return atual;
            }

            // Atualiza a altura do no
            atualizarAltura(atual);

            // Verifica o balanceamento
            int fb = fatorBalanceamento(atual);

            // CASO 1: ESQUERDA-ESQUERDA
            if (fb > 1 && valor < atual.esquerda.valor) {

                caso = "ESQUERDA-ESQUERDA";

                return rotacaoDireita(atual);
            }

            // CASO 2: DIREITA-DIREITA
            if (fb < -1 && valor > atual.direita.valor) {

                caso = "DIREITA-DIREITA";

                return rotacaoEsquerda(atual);
            }

            // CASO 3: ESQUERDA-DIREITA
            if (fb > 1 && valor > atual.esquerda.valor) {

                caso = "ESQUERDA-DIREITA";

                atual.esquerda =
                        rotacaoEsquerda(atual.esquerda);

                return rotacaoDireita(atual);
            }

            // CASO 4: DIREITA-ESQUERDA
            if (fb < -1 && valor < atual.direita.valor) {

                caso = "DIREITA-ESQUERDA";

                atual.direita =
                        rotacaoDireita(atual.direita);

                return rotacaoEsquerda(atual);
            }

            // Nenhuma rotacao foi necessaria
            return atual;
        }

        // Percurso PRE-ORDEM: raiz, esquerda, direita
        public String preOrdem() {

            if (raiz == null) {
                return "VAZIA";
            }

            StringBuilder resultado = new StringBuilder();

            preOrdemRecursivo(raiz, resultado);

            return resultado.toString();
        }

        private void preOrdemRecursivo(
                Node atual,
                StringBuilder resultado
        ) {

            if (atual == null) {
                return;
            }

            if (resultado.length() > 0) {
                resultado.append(" ");
            }

            resultado.append(atual.valor);

            preOrdemRecursivo(atual.esquerda, resultado);
            preOrdemRecursivo(atual.direita, resultado);
        }

        // Retorna a altura da arvore
        public int obterAltura() {
            return altura(raiz);
        }
    }

    // Leitura da entrada
    static class FastScanner {

        BufferedReader br;
        StringTokenizer st;

        FastScanner() {
            br = new BufferedReader(
                    new InputStreamReader(System.in)
            );
        }

        String next() throws IOException {

            while (st == null || !st.hasMoreTokens()) {

                String linha = br.readLine();

                if (linha == null) {
                    return null;
                }

                st = new StringTokenizer(linha);
            }

            return st.nextToken();
        }
    }

    public static void main(String[] args) throws IOException {

        FastScanner entrada = new FastScanner();

        StringBuilder saida = new StringBuilder();

        int n = Integer.parseInt(entrada.next());

        ArvoreAVL arvore = new ArvoreAVL();

        for (int i = 0; i < n; i++) {

            String comando = entrada.next();

            switch (comando) {

                case "INSERIR": {

                    int valor = Integer.parseInt(entrada.next());

                    saida.append(arvore.inserir(valor));
                    saida.append("\n");

                    break;
                }

                case "PREORDEM": {

                    saida.append(arvore.preOrdem());
                    saida.append("\n");

                    break;
                }

                case "ALTURA": {

                    saida.append(arvore.obterAltura());
                    saida.append("\n");

                    break;
                }
            }
        }

        System.out.print(saida);
    }
}
