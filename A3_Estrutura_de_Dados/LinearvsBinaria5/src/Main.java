import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
/*
 * Estrutura: vetor de inteiros ja ordenado.
 * Busca linear: O(N) comparacoes por consulta.
 * Busca binaria: O(log N) comparacoes por consulta.
 *
 * Na terceira consulta o alvo e 3, o primeiro elemento. A busca linear
 * acerta na primeira verificacao (1 comparacao); a binaria ainda percorre
 * cerca de log2(N) passos (3 comparacoes). Ela foi melhor nesse caso
 * porque o elemento esta no inicio.
 * Isso nao contradiz a analise de complexidade. O(N) e O(log N) descrevem
 * o crescimento no pior caso conforme N aumenta. Um caso favoravel
 * isolado (alvo na posicao 0) nao muda o pior caso nem o caso medio:
 * a busca linear continua podendo examinar o vetor inteiro, e a binaria
 * permanece limitada por O(log N).
 */
class LinearContraBinaria {
    static void main() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine().trim());
        int[] v = new int[n];
        String[] partes = in.readLine().trim().split(" ");
        for (int i = 0; i < n; i++) {
            v[i] = Integer.parseInt(partes[i]);
        }
        int q = Integer.parseInt(in.readLine().trim());
        StringBuilder saida = new StringBuilder();
        for (int c = 0; c < q; c++) {
            int alvo = Integer.parseInt(in.readLine().trim());
            int posLinear = -1;
            int compLinear = 0;
            for (int i = 0; i < n; i++) {
                compLinear++;
                if (v[i] == alvo) {
                    posLinear = i;
                    break;
                }
            }
            int compBinaria = 0;
            int esq = 0;
            int dir = n - 1;
            while (esq <= dir) {
                int meio = (esq + dir) / 2;
                compBinaria++;
                if (v[meio] == alvo) {
                    break;
                } else if (v[meio] < alvo) {
                    esq = meio + 1;
                } else {
                    dir = meio - 1;
                }
            }
            saida.append(posLinear).append(' ')
                    .append(compLinear).append(' ')
                    .append(compBinaria).append('\n');
        }
        System.out.print(saida);
    }
}