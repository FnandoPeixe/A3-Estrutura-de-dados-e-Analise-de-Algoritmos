import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
/*
 * Estrutura: lista encadeada simples com no sentinela.
 * Inserir, remover e consultar na posicao P: O(P). Listar: O(tamanho).
 */
class SequenciaRobos {
    static class No {
        int codigo;
        No prox;
        No(int codigo) { this.codigo = codigo; }
    }
    static void main() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine().trim());
        No cabeca = new No(0);
        int tam = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            String[] p = in.readLine().trim().split("\\s+");
            char cmd = p[0].charAt(0);
            if (cmd == 'L') {
                if (tam == 0) {
                    sb.append("VAZIA\n");
                } else {
                    No atual = cabeca.prox;
                    for (int k = 0; k < tam; k++) {
                        if (k > 0) sb.append(' ');
                        sb.append(atual.codigo);
                        atual = atual.prox;
                    }
                    sb.append('\n');
                }
            } else {
                int pos = Integer.parseInt(p[1]);
                if (cmd == 'I') {
                    int x = Integer.parseInt(p[2]);
                    if (pos < 1 || pos > tam + 1) {
                        sb.append("ERRO\n");
                    } else {
                        No ant = cabeca;
                        for (int k = 1; k < pos; k++) ant = ant.prox;
                        No novo = new No(x);
                        novo.prox = ant.prox;
                        ant.prox = novo;
                        tam++;
                    }
                } else if (cmd == 'R') {
                    if (pos < 1 || pos > tam) {
                        sb.append("ERRO\n");
                    } else {
                        No ant = cabeca;
                        for (int k = 1; k < pos; k++) ant = ant.prox;
                        No rem = ant.prox;
                        ant.prox = rem.prox;
                        tam--;
                        sb.append(rem.codigo).append('\n');
                    }
                } else {
                    if (pos < 1 || pos > tam) {
                        sb.append("ERRO\n");
                    } else {
                        No atual = cabeca.prox;
                        for (int k = 1; k < pos; k++) atual = atual.prox;
                        sb.append(atual.codigo).append('\n');
                    }
                }
            }
        }
        System.out.print(sb);
    }
}