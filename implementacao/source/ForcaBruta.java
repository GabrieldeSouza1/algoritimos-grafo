package implementacao.source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ForcaBruta {
    private static Integer[] caminhoMinimo;
    private static int menorCusto = Integer.MAX_VALUE;
    private static final int n = 100;

    /**
     * Aleatório "fixo" para geração de testes repetitíveis
     */
    static Random aleatorio = new Random(42);

    public static void main(String[] args) {
        int[][] grafo = grafoCompletoPonderado(n);

        caminhoMinimo = new Integer[n + 1];
        caminhoMinimo[0] = 0;

        List<Integer> vertices = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            for(int j = 1; j < n; j++) {
                vertices.add(grafo[i][j]);
            }
        }

        encontrarCaminhoMinimo(grafo, vertices, 0, 0);

        System.out.print("Caminho de menor custo: ");
        for (int i = 0; i <= n; i++) {
            System.out.print(caminhoMinimo[i] + " ");
        }
        System.out.println("\nCusto mínimo: " + menorCusto);
    }

    private static void encontrarCaminhoMinimo(int[][] grafo, List<Integer> vertices, int posicaoAtual, int custoAtual) {
        if (vertices.isEmpty()) {
            if (grafo[posicaoAtual][0] != 0 && custoAtual + grafo[posicaoAtual][0] < menorCusto) {
                System.out.println(vertices);

                menorCusto = custoAtual + grafo[posicaoAtual][0];
                caminhoMinimo = vertices.toArray(vertices.toArray(new Integer[vertices.size()]));
                caminhoMinimo[caminhoMinimo.length - 1] = 0;
            }
            return;
        }

        for (int i = 0; i < vertices.size(); i++) {
            int novoCusto = custoAtual + grafo[posicaoAtual][vertices.get(i)];
            if (novoCusto < menorCusto) {
                int verticeAtual = vertices.remove(i);
                caminhoMinimo[posicaoAtual + 1] = verticeAtual;
                encontrarCaminhoMinimo(grafo, vertices, verticeAtual, novoCusto);
                vertices.add(i, verticeAtual);
            }
        }
    }

    /**
     * Retorna uma matriz quadrada de "vertices" x "vertices" com números inteiros,
     * representando um grafo completo. A diagonal principal está preenchida com
     * valor -1, indicando que não há aresta.
     * 
     * @param vertices A quantidade de vértices do grafo.
     * @return Matriz quadrada com custos de movimentação entre os vértices.
     */
    public static int[][] grafoCompletoPonderado(int vertices) {
        int[][] matriz = new int[vertices][vertices];
        int valor;
        for (int i = 0; i < matriz.length; i++) {
            matriz[i][i] = -1;
            for (int j = i + 1; j < matriz.length; j++) {
                valor = aleatorio.nextInt(25) + 1;
                matriz[i][j] = valor;
                matriz[j][i] = valor;
            }
        }
        return matriz;
    }
}
