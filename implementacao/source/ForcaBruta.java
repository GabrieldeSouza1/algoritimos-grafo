package implementacao.source;

import java.util.*;

public class ForcaBruta {

    private int[][] grafo; // Matriz de custos de viagem
    private List<Integer> caminhoMinimo; // Lista com o caminho de menor custo
    private int menorCusto = Integer.MAX_VALUE; // Menor custo encontrado

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
            // Chegou ao último vértice, calcula o custo total e verifica se é menor que o menor custo atual
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
                encontrarCaminhoMinimo(nivel + 1, caminhoAtual, visitados, custoAtual + grafo[caminhoAtual.get(caminhoAtual.size() - 2)][i]);

                // Remove o último vértice do caminho atual e marca como não visitado para explorar outras possibilidades
                caminhoAtual.remove(caminhoAtual.size() - 1);
                visitados[i] = false;
            }
        }
    }

    
    // método para gerar a matriz com os custos de viagem
         /**
     * Aleatório "fixo" para geração de testes repetitíveis
     */
    static Random aleatorio = new Random();
    
    /**
     * Retorna uma matriz quadrada de "vertices" x "vertices" com números inteiros,
     * representando um grafo completo. A diagonal principal está preenchida com 
     * valor -1, indicando que não há aresta.
     * @param vertices A quantidade de vértices do grafo.
     * @return Matriz quadrada com custos de movimentação entre os vértices.
     */
    public static int[][] grafoCompletoPonderado(int vertices){
        Random aleatorio = new Random();
        int[][] matriz = new int[vertices][vertices];
        int valor;
        for (int i = 0; i < matriz.length; i++) {
            matriz[i][i]=-1;         
            for (int j = i+1; j < matriz.length; j++) {
                valor = aleatorio.nextInt(25)+1;
                matriz[i][j] = valor;
                matriz[j][i] = valor;
            }
        }  
        return matriz;
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
}
