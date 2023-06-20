package implementacao.source;

import java.util.ArrayList;
import java.util.List;

public class Backtracking {

    private List<Integer> caminhoMinimo;
    private int menorCusto = Integer.MAX_VALUE;
    private int custoTotal = 0;

    public void encontrarCaminhoMinimo(int[][] grafo) {
        this.custoTotal = 0;
        int n = grafo.length;
        caminhoMinimo = new ArrayList<>();

        List<Integer> caminhoAtual = new ArrayList<>();
        caminhoAtual.add(0);

        boolean[] visitados = new boolean[n];
        visitados[0] = true;

        encontrarCaminhoMinimo(grafo, 1, caminhoAtual, visitados, 0);

        caminhoMinimo.add(0);
    }

    private void encontrarCaminhoMinimo(int[][] grafo, int nivel, List<Integer> caminhoAtual, boolean[] visitados,
            int custoAtual) {
        if (nivel == grafo.length) {
            int custoTotal = custoAtual + grafo[caminhoAtual.get(caminhoAtual.size() - 1)][0];

            if (custoTotal < menorCusto) {
                menorCusto = custoTotal;
                this.custoTotal = custoTotal;
                caminhoMinimo = new ArrayList<>(caminhoAtual);
            }

            return;
        }

        for (int i = 0; i < grafo.length; i++) {
            if (!visitados[i] && custoAtual + grafo[caminhoAtual.get(caminhoAtual.size() - 1)][i] < menorCusto) {

                caminhoAtual.add(i);
                visitados[i] = true;

                encontrarCaminhoMinimo(grafo, nivel + 1, caminhoAtual, visitados,
                        custoAtual + grafo[caminhoAtual.get(caminhoAtual.size() - 2)][i]);

                caminhoAtual.remove(caminhoAtual.size() - 1);
                visitados[i] = false;
            }
        }
    }

    public List<Integer> getCaminhoMinimo() {
        return caminhoMinimo;
    }

    public int getMenorCusto() {
        return menorCusto;
    }

    public int getCustoTotal() {
        return custoTotal;
    }
}
