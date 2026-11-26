import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
/*
 * Estruturas: fila circular (vetor) para normais, pilha (vetor) para urgentes
 * e vetor para o historico.
 * Enfileirar, desenfileirar, empilhar, desempilhar e atender: O(1).
 * Relatorio: O(numero de atendimentos).
 */
class CentralSuporte {
    static void main() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine().trim());
        int[] fila = new int[n];
        int ini = 0, fim = 0, qtdFila = 0;
        int[] pilha = new int[n];
        int topo = 0;
        int[] hist = new int[n];
        int h = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            String[] p = in.readLine().trim().split("\\s+");
            String cmd = p[0];
            switch (cmd) {
                case "NORMAL" -> {
                    fila[fim] = Integer.parseInt(p[1]);
                    fim++;
                    if (fim == n) fim = 0;
                    qtdFila++;
                }
                case "URGENTE" -> pilha[topo++] = Integer.parseInt(p[1]);
                case "ATENDER" -> {
                    int codigo;
                    if (topo > 0) {
                        codigo = pilha[--topo];
                    } else if (qtdFila > 0) {
                        codigo = fila[ini];
                        ini++;
                        if (ini == n) ini = 0;
                        qtdFila--;
                    } else {
                        sb.append("VAZIA\n");
                        continue;
                    }
                    hist[h++] = codigo;
                    sb.append(codigo).append('\n');
                }
                default -> {
                    if (h == 0) {
                        sb.append("VAZIO\n");
                    } else {
                        for (int k = 0; k < h; k++) {
                            if (k > 0) sb.append(' ');
                            sb.append(hist[k]);
                        }
                        sb.append('\n');
                    }
                }
            }
        }
        System.out.print(sb);
    }
}