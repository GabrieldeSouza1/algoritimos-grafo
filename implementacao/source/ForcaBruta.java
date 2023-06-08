package implementacao.source;

import java.util.*;

public class ForcaBruta {

    private List<Integer> caminhoMinimo;
    private int menorCusto = Integer.MAX_VALUE;

    public void encontrarCaminhoMinimo(int[][] grafo) {
        int n = grafo.length;
        caminhoMinimo = new ArrayList<>();

        List<Integer> caminhoAtual = new ArrayList<>();
        caminhoAtual.add(0);

        boolean[] visitados = new boolean[n];
        visitados[0] = true;

        encontrarCaminhoMinimo(grafo, 1, caminhoAtual, visitados, 0);

        caminhoMinimo.add(0);
    }

    private void encontrarCaminhoMinimo(int[][] grafo, int nivel, List<Integer> caminhoAtual, boolean[] visitados, int custoAtual) {
        if (nivel == grafo.length) {
            int custoTotal = custoAtual + grafo[caminhoAtual.get(caminhoAtual.size() - 1)][0];

            if (custoTotal < menorCusto) {
                menorCusto = custoTotal;
                caminhoMinimo = new ArrayList<>(caminhoAtual);
            }

            return;
        }

        for (int i = 0; i < grafo.length; i++) {
            if (!visitados[i]) {

                caminhoAtual.add(i);
                visitados[i] = true;

                encontrarCaminhoMinimo(grafo, nivel + 1, caminhoAtual, visitados, custoAtual + grafo[caminhoAtual.get(caminhoAtual.size() - 2)][i]);

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
}
