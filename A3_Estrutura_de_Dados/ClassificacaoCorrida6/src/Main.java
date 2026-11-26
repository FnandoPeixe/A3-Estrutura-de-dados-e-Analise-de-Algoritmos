import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
/*
 * Algoritmo: Merge Sort.
 * Complexidade no pior caso: O(N log N).
 * Estrutura: vetores paralelos (peito e tempo).
 * Criterio: tempo crescente; empate desempata pelo menor numero de peito.
 */
class ClassificacaoCorrida {
    static int[] peito, tempo, auxPeito, auxTempo;
    static boolean antes(int a, int b) {
        if (tempo[a] != tempo[b]) return tempo[a] < tempo[b];
        return peito[a] < peito[b];
    }
    static void merge(int ini, int meio, int fim) {
        int i = ini, j = meio + 1, k = ini;
        while (i <= meio && j <= fim) {
            if (antes(i, j)) {
                auxPeito[k] = peito[i];
                auxTempo[k] = tempo[i];
                i++;
            } else {
                auxPeito[k] = peito[j];
                auxTempo[k] = tempo[j];
                j++;
            }
            k++;
        }
        while (i <= meio) {
            auxPeito[k] = peito[i];
            auxTempo[k] = tempo[i];
            i++;
            k++;
        }
        while (j <= fim) {
            auxPeito[k] = peito[j];
            auxTempo[k] = tempo[j];
            j++;
            k++;
        }
        for (int t = ini; t <= fim; t++) {
            peito[t] = auxPeito[t];
            tempo[t] = auxTempo[t];
        }
    }
    static void mergeSort(int ini, int fim) {
        if (ini >= fim) return;
        int meio = ini + (fim - ini) / 2;
        mergeSort(ini, meio);
        mergeSort(meio + 1, fim);
        merge(ini, meio, fim);
    }
    static void main() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(in.readLine().trim());
        peito = new int[n];
        tempo = new int[n];
        auxPeito = new int[n];
        auxTempo = new int[n];
        for (int i = 0; i < n; i++) {
            String[] p = in.readLine().trim().split(" ");
            peito[i] = Integer.parseInt(p[0]);
            tempo[i] = Integer.parseInt(p[1]);
        }
        if (n > 1) mergeSort(0, n - 1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(i + 1).append(' ').append(peito[i]).append(' ').append(tempo[i]).append('\n');
        }
        System.out.print(sb);
    }
}