package implementacao.source;

import java.util.*;

public class ForcaBruta {

    private int[][] grafo; // Matriz de custos de viagem
    private List<Integer> caminhoMinimo; // Lista com o caminho de menor custo
    private int menorCusto = Integer.MAX_VALUE; // Menor custo encontrado
    private int custoTotal = 0; // Custo Total

    public void encontrarCaminhoMinimo(int[][] grafo) {
        this.grafo = grafo;
        int n = grafo.length; // Tamanho do grafo
        caminhoMinimo = new ArrayList<>(); // Lista com os vértices que irão compor o caminho mínimo

        List<Integer> caminhoAtual = new ArrayList<>();
        caminhoAtual.add(0); // Inicia o caminho pelo vértice 0

        boolean[] visitados = new boolean[n]; // Lista booleana de vértices visitados
        visitados[0] = true; // Marca o vértice 0 como visitado

        encontrarCaminhoMinimo(1, caminhoAtual, visitados, 0);

        caminhoMinimo.add(0); // Adiciona o vértice 0 ao final do caminho mínimos
    }

    private void encontrarCaminhoMinimo(int nivel, List<Integer> caminhoAtual, boolean[] visitados, int custoAtual) {
        if (nivel == grafo.length) {
            // Chegou ao último vértice, calcula o custo total e verifica se é menor que o
            // menor custo atual
            int custoTotal = custoAtual + grafo[caminhoAtual.get(caminhoAtual.size() - 1)][0];
            if (custoTotal < menorCusto) {
                // Atualiza o menor custo e o caminho mínimo encontrado
                menorCusto = custoTotal;
                caminhoMinimo = new ArrayList<>(caminhoAtual);
            }
            return;
        }

        for (int i = 0; i < grafo.length; i++) {
            if (!visitados[i]) {
                // Vértice não visitado, adiciona ao caminho atual e marca como visitado
                caminhoAtual.add(i);
                visitados[i] = true;

                // Recursivamente explora o próximo nível do grafo
                encontrarCaminhoMinimo(nivel + 1, caminhoAtual, visitados,
                        custoAtual + grafo[caminhoAtual.get(caminhoAtual.size() - 2)][i]);

                // Remove o último vértice do caminho atual e marca como não visitado para
                // explorar outras possibilidades
                caminhoAtual.remove(caminhoAtual.size() - 1);
                visitados[i] = false;
            }
        }
    }

    public int[][] getGrafo() {
        return grafo;
    }

    public List<Integer> getCaminhoMinimo() {
        return caminhoMinimo;
    }

    public int getMenorCusto() {
        return menorCusto;
    }

    public int getCustoTotal() {
        this.custoTotal = 0;
        // Calcular o menor custo total
        for (int i = 0; i < caminhoMinimo.size() - 1; i++) {
            int cidadeAtual = caminhoMinimo.get(i);
            int proximaCidade = caminhoMinimo.get(i + 1);
            this.custoTotal += grafo[cidadeAtual][proximaCidade];
        }

        return this.custoTotal;
    }
}
