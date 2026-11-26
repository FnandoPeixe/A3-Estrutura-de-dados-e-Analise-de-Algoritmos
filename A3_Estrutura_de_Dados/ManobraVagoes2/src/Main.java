import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
/*
 * Estrutura: pilha implementada com vetor.
 * Empilhar e desempilhar: O(1). Cada caso: O(N).
 */
class ManobraVagoes {
    static void main() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(in.readLine().trim());
        StringBuilder sb = new StringBuilder();
        for (int caso = 0; caso < t; caso++) {
            int n = Integer.parseInt(in.readLine().trim());
            String[] partes = in.readLine().trim().split("\\s+");
            int[] desejada = new int[n];
            for (int i = 0; i < n; i++) desejada[i] = Integer.parseInt(partes[i]);
            int[] pilha = new int[n];
            int topo = 0;
            int proximo = 1;
            boolean ok = true;
            for (int i = 0; i < n && ok; i++) {
                int alvo = desejada[i];
                while ((topo == 0 || pilha[topo - 1] != alvo) && proximo <= n) {
                    pilha[topo++] = proximo++;
                }
                if (topo > 0 && pilha[topo - 1] == alvo) topo--;
                else ok = false;
            }
            sb.append(ok ? "SIM" : "NAO").append('\n');
        }
        System.out.print(sb);
    }
}