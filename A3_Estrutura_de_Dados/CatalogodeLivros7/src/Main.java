import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.StringTokenizer;

/*
 * QUESTÃO 7 - CATÁLOGO DE LIVROS
 * Estrutura escolhida: Árvore Binária de Busca (ABB).
 *
 * Complexidades:
 * Inserção: O(h)
 * Busca: O(h)
 * Remoção: O(h)
 * EmOrdem: O(n)
 * PreOrdem: O(n)
 * Altura: O(n)
 *
 * Espaço: O(n).
 * No pior caso, h = n, tornando inserção, busca
 * e remoção O(n).
 */

static class Node {
    int valor;
    Node esquerda;
    Node direita;

    Node(int valor) {
        this.valor = valor;
        this.esquerda = null;
        this.direita = null;
    }
}

static class ArvoreBinariaBusca {

    private Node raiz;
    private int tamanho;

    // INSERIR: adiciona um valor, ignorando duplicados
    public void inserir(int valor) {

        Node novo = new Node(valor);

        if (raiz == null) {
            raiz = novo;
            tamanho++;
            return;
        }

        Node atual = raiz;
        Node pai = null;

        while (atual != null) {

            pai = atual;

            if (valor < atual.valor) {
                atual = atual.esquerda;
            } else if (valor > atual.valor) {
                atual = atual.direita;
            } else {
                return; // Valor repetido: não insere
            }
        }

        if (valor < pai.valor) {
            pai.esquerda = novo;
        } else {
            pai.direita = novo;
        }

        tamanho++;
    }

    // BUSCAR: mostra o caminho percorrido
    public String buscar(int valor) {

        StringBuilder resultado = new StringBuilder();
        Node atual = raiz;

        if (raiz == null) {
            return "NAO";
        }

        while (atual != null) {

            // Adiciona espaço entre os valores visitados
            if (!resultado.isEmpty()) {
                resultado.append(" ");
            }

            resultado.append(atual.valor);

            if (valor == atual.valor) {
                resultado.append(" SIM");
                return resultado.toString();
            }

            if (valor < atual.valor) {
                atual = atual.esquerda;
            } else {
                atual = atual.direita;
            }
        }

        resultado.append(" NAO");

        return resultado.toString();
    }

    // REMOVER: remove um nó da árvore
    public boolean remover(int valor) {

        Node atual = raiz;
        Node pai = null;

        // Procura o nó que será removido
        while (atual != null && atual.valor != valor) {

            pai = atual;

            if (valor < atual.valor) {
                atual = atual.esquerda;
            } else {
                atual = atual.direita;
            }
        }

        // Valor não encontrado
        if (atual == null) {
            return false;
        }

        // Caso 1: nó possui dois filhos
        if (atual.esquerda != null && atual.direita != null) {

            // Procura o sucessor: menor valor da subárvore direita
            Node paiSucessor = atual;
            Node sucessor = atual.direita;

            while (sucessor.esquerda != null) {
                paiSucessor = sucessor;
                sucessor = sucessor.esquerda;
            }

            // Substitui o valor do nó pelo sucessor
            atual.valor = sucessor.valor;

            // Remove o nó sucessor da posição original
            if (paiSucessor == atual) {
                paiSucessor.direita = sucessor.direita;
            } else {
                paiSucessor.esquerda = sucessor.direita;
            }

        } else {

            // Caso 2: nó possui zero ou um filho
            Node filho;

            if (atual.esquerda != null) {
                filho = atual.esquerda;
            } else {
                filho = atual.direita;
            }

            // Remove a raiz
            if (pai == null) {
                raiz = filho;
            } else if (pai.esquerda == atual) {
                pai.esquerda = filho;
            } else {
                pai.direita = filho;
            }
        }

        tamanho--;
        return true;
    }

    // EMORDEM: esquerda, raiz, direita
    public String emOrdem() {

        if (raiz == null) {
            return "VAZIA";
        }

        StringBuilder resultado = new StringBuilder();

        Node[] pilha = new Node[tamanho];
        int topo = 0;

        Node atual = raiz;

        while (atual != null || topo > 0) {

            while (atual != null) {
                pilha[topo++] = atual;
                atual = atual.esquerda;
            }

            atual = pilha[--topo];

            adicionarValor(resultado, atual.valor);

            atual = atual.direita;
        }

        return resultado.toString();
    }

    // PREORDEM: raiz, esquerda, direita
    public String preOrdem() {

        if (raiz == null) {
            return "VAZIA";
        }

        StringBuilder resultado = new StringBuilder();

        Node[] pilha = new Node[tamanho];
        int topo = 0;

        pilha[topo++] = raiz;

        while (topo > 0) {

            Node atual = pilha[--topo];

            adicionarValor(resultado, atual.valor);

            // Direita primeiro para processar a esquerda antes
            if (atual.direita != null) {
                pilha[topo++] = atual.direita;
            }

            if (atual.esquerda != null) {
                pilha[topo++] = atual.esquerda;
            }
        }

        return resultado.toString();
    }

    // ALTURA: árvore vazia = -1; folha = 0
    public int altura() {

        if (raiz == null) {
            return -1;
        }

        // Percorre os níveis utilizando uma fila própria
        Node[] fila = new Node[tamanho];

        int inicio = 0;
        int fim = 0;
        int altura = -1;

        fila[fim++] = raiz;

        while (inicio < fim) {

            int quantidadeNivel = fim - inicio;
            altura++;

            for (int i = 0; i < quantidadeNivel; i++) {

                Node atual = fila[inicio++];

                if (atual.esquerda != null) {
                    fila[fim++] = atual.esquerda;
                }

                if (atual.direita != null) {
                    fila[fim++] = atual.direita;
                }
            }
        }

        return altura;
    }

    // Adiciona valores separados por espaço
    private void adicionarValor(StringBuilder resultado, int valor) {

        if (!resultado.isEmpty()) {
            resultado.append(" ");
        }

        resultado.append(valor);
    }
}

// Leitura rápida da entrada
static class FastScanner {

    BufferedReader br;
    StringTokenizer st;

    FastScanner() {
        br = new BufferedReader(new InputStreamReader(System.in));
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

void main() throws IOException {

    FastScanner entrada = new FastScanner();

    BufferedWriter saida = new BufferedWriter(
            new OutputStreamWriter(System.out)
    );

    int n = Integer.parseInt(entrada.next());

    ArvoreBinariaBusca arvore = new ArvoreBinariaBusca();

    for (int i = 0; i < n; i++) {

        String comando = entrada.next();

        switch (comando) {

            case "INSERIR": {
                int valor = Integer.parseInt(entrada.next());
                arvore.inserir(valor);
                break;
            }

            case "BUSCAR": {
                int valor = Integer.parseInt(entrada.next());
                saida.write(arvore.buscar(valor));
                saida.newLine();
                break;
            }

            case "REMOVER": {
                int valor = Integer.parseInt(entrada.next());

                if (!arvore.remover(valor)) {
                    saida.write("ERRO");
                    saida.newLine();
                }

                break;
            }

            case "EMORDEM": {
                saida.write(arvore.emOrdem());
                saida.newLine();
                break;
            }

            case "PREORDEM": {
                saida.write(arvore.preOrdem());
                saida.newLine();
                break;
            }

            case "ALTURA": {
                saida.write(String.valueOf(arvore.altura()));
                saida.newLine();
                break;
            }
        }
    }

    saida.flush();
}
