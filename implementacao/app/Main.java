package implementacao.app;

import java.util.List;

import implementacao.source.ForcaBruta;

public class Main {
    public static void main(String[] args) {
        int vertices = 10; // Número de vértices do grafo
        int[][] grafo = ForcaBruta.grafoCompletoPonderado(vertices);

        imprimirMatriz(grafo);

        ForcaBruta forcaBruta = new ForcaBruta();
        forcaBruta.encontrarCaminhoMinimo(grafo);

        List<Integer> caminhoMinimo = forcaBruta.getCaminhoMinimo();
        int custoMinimo = forcaBruta.getMenorCusto();

        System.out.println("Caminho de menor custo: ");
        caminhoMinimo.forEach(vertice -> System.out.println(vertice));
        System.out.println("\nCusto mínimo: " + custoMinimo);
    }

    private static void imprimirMatriz(int[][] matriz) {
        int tamanho = matriz.length;

        // Imprimir cabeçalho com os números das colunas
        System.out.print("    ");
        for (int i = 0; i < tamanho; i++) {
            System.out.printf("%2d ", i);
        }
        System.out.println();

        // Imprimir linhas da matriz com números das linhas e elementos
        for (int i = 0; i < tamanho; i++) {
            System.out.printf("%2d: ", i);
            for (int j = 0; j < tamanho; j++) {
                System.out.printf("%2d ", matriz[i][j]);
            }
            System.out.println();
        }
    }
}
