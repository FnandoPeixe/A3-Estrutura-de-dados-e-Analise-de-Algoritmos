import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
/*
 * Estrutura: fila circular implementada com vetor.
 * Enfileirar, desenfileirar e consultar a frente: O(1).
 */
class SenhasAtendimento {
    static void main() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine().trim());
        int[] fila = new int[n];
        int ini = 0, fim = 0, qtd = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            String[] p = in.readLine().trim().split("\\s+");
            String cmd = p[0];
            if (cmd.equals("CHEGAR")) {
                fila[fim] = Integer.parseInt(p[1]);
                fim++;
                if (fim == n) fim = 0;
                qtd++;
            } else if (qtd == 0) {
                sb.append("VAZIA\n");
            } else if (cmd.equals("ATENDER")) {
                sb.append(fila[ini]).append('\n');
                ini++;
                if (ini == n) ini = 0;
                qtd--;
            } else {
                sb.append(fila[ini]).append('\n');
            }
        }
        System.out.print(sb);
    }
}