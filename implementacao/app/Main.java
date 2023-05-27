package implementacao.app;

import java.util.List;
import java.util.Random;

import implementacao.source.ForcaBruta;
import implementacao.source.Guloso;

public class Main {
    private static final double MAX_ITERATIONS_FB = 70.0;
    private static final double MAX_ITERATIONS_GLOBAL = 1000.0;

    public static void main(String[] args) {
        int vertices = obterNMenosUm(), countSameAnswer = 0;
        long totalTimeFB = 0, totalTimeG = 0;
        ForcaBruta forcaBruta = new ForcaBruta();
        Guloso guloso = new Guloso();

        try {
            for(int i = 0; i < MAX_ITERATIONS_GLOBAL; i++) {
                int[][] grafo = grafoCompletoPonderado(vertices);

                long startTime = System.currentTimeMillis();
                forcaBruta.encontrarCaminhoMinimo(grafo);
                long endTime = System.currentTimeMillis();

                List<Integer> caminhoMinimoFB = forcaBruta.getCaminhoMinimo();

                long elapsedTime = endTime - startTime;
                totalTimeFB += elapsedTime;

                startTime = System.currentTimeMillis();
                guloso.encontrarCaminhoMinimo(grafo);
                endTime = System.currentTimeMillis();

                List<Integer> caminhoMinimoG = guloso.getCaminhoMinimo();

                if(caminhoMinimoFB.equals(caminhoMinimoG)) {
                    countSameAnswer++;
                }

                elapsedTime = endTime - startTime;
                totalTimeG += elapsedTime;
            }

            double averageTimeFB = totalTimeFB / MAX_ITERATIONS_GLOBAL;
            System.out.println("Tempo total das iterações FB: " + totalTimeFB + "ms");
            System.out.println("Tempo médio das iterações FB: " + averageTimeFB + "ms");

            System.out.println();

            double averageTimeG = totalTimeG / MAX_ITERATIONS_GLOBAL;
            System.out.println("Tempo total das iterações G: " + totalTimeG + "ms");
            System.out.println("Tempo médio das iterações G: " + averageTimeG + "ms");

            System.out.println();

            System.out.println("Quantidade de soluções obtidas iguais: " + countSameAnswer);
        } catch(Exception err) {
            err.printStackTrace();
            System.out.println(err.getMessage());
        }
    }

    public static int obterNMenosUm() {
        int vertices = 5;
        int nMinusOne = 0;
        long totalTime;
        boolean minusFourMin = true;

        while (minusFourMin) {
            long elapsedTime = 0;
            totalTime = 0;

            for (int i = 0; i < MAX_ITERATIONS_FB; i++) {
                int[][] grafo = grafoCompletoPonderado(vertices);

                long startTime = System.currentTimeMillis();
                ForcaBruta caixeiroViajante = new ForcaBruta();
                caixeiroViajante.encontrarCaminhoMinimo(grafo);
                long endTime = System.currentTimeMillis();

                elapsedTime = endTime - startTime;
                totalTime += elapsedTime;

                if(elapsedTime > 3500) {
                    nMinusOne = vertices - 1;
                    minusFourMin = false;
                    break;
                }
            }

            if(minusFourMin) {
                double averageTime = totalTime / MAX_ITERATIONS_FB;
                System.out.println("Tempo médio das iterações: " + averageTime + "ms");

                vertices++;
            }
        }
        System.out.println("Número N-1: " + nMinusOne);
        System.out.println();

        return nMinusOne;
    }

    /**
     * Retorna uma matriz quadrada de "vertices" x "vertices" com números inteiros,
     * representando um grafo completo. A diagonal principal está preenchida com
     * valor -1, indicando que não há aresta.
     * @param vertices A quantidade de vértices do grafo.
     * @return Matriz quadrada com custos de movimentação entre os vértices.
     */
    public static int[][] grafoCompletoPonderado(int vertices) {
        Random aleatorio = new Random();
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
