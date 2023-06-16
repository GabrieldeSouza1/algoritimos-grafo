package implementacao.app;

import java.util.List;
import java.util.Random;

import implementacao.source.Backtracking;
import implementacao.source.ForcaBruta;
import implementacao.source.Guloso;
import implementacao.source.ProgramacaoDinamica;

public class Main {
    private static final double MAX_ITERATIONS_FB = 70.0;
    private static final double MAX_ITERATIONS_GLOBAL = 1000.0;

    public static void main(String[] args) {
        ForcaBruta forcaBruta = new ForcaBruta();
        Backtracking backtracking = new Backtracking();
        ProgramacaoDinamica programacaoDinamica = new ProgramacaoDinamica();

        int[][] grafo = grafoCompletoPonderado(10);

        System.out.println("----------------------Força Bruta------------------------");

        long startTimeBrute = System.currentTimeMillis();
        forcaBruta.encontrarCaminhoMinimo(grafo);
        List<Integer> caminhoMinimoBrute = forcaBruta.getCaminhoMinimo();
        caminhoMinimoBrute.forEach(System.out::print);
        long endTimeBrute = System.currentTimeMillis();
        long elapsedTimeBrute = endTimeBrute - startTimeBrute;

        System.out.println("\n----------------------Backtracking------------------------");

        long startTimeBacktracking = System.currentTimeMillis();
        backtracking.encontrarCaminhoMinimo(grafo);
        List<Integer> caminhoMinimoBack = backtracking.getCaminhoMinimo();
        caminhoMinimoBack.forEach(System.out::print);
        long endTimeBacktracking = System.currentTimeMillis();
        long elapsedTimeBacktracking = endTimeBacktracking - startTimeBacktracking;

        System.out.println("\n----------------------Programação Dinâmica------------------------");

        long startTimePD = System.currentTimeMillis();
        programacaoDinamica.encontrarCaminhoMinimo(grafo);
        List<Integer> caminhoMinimoPD = programacaoDinamica.getCaminhoMinimo();
        caminhoMinimoPD.forEach(System.out::print);
        long endTimePD = System.currentTimeMillis();
        long elapsedTimePD = endTimePD - startTimePD;

        System.out.println("\nO tempo do força bruta foi de: " + elapsedTimeBrute + "ms");
        System.out.println("O tempo do backtracking foi de: " + elapsedTimeBacktracking + "ms");
        System.out.println("O tempo da programação dinâmica foi de: " + elapsedTimePD + "ms");

        System.out.println();
        testAlgorithms();
    }

    public static void testAlgorithms() {
        int vertices = obterNMenosUm(), countSameAnswer = 0;
        long totalTimeFB = 0, totalTimeG = 0, totalTimeBackTranking = 0, totalTimePD = 0;

        ForcaBruta forcaBruta = new ForcaBruta();
        Guloso guloso = new Guloso();
        Backtracking backtracking = new Backtracking();
        ProgramacaoDinamica programacaoDinamica = new ProgramacaoDinamica();

        try {
            for (int i = 0; i < MAX_ITERATIONS_GLOBAL; i++) {
                int[][] grafo = grafoCompletoPonderado(vertices);

                //* Força Bruta

                long startTime = System.currentTimeMillis();
                forcaBruta.encontrarCaminhoMinimo(grafo);
                long endTime = System.currentTimeMillis();

                List<Integer> caminhoMinimoFB = forcaBruta.getCaminhoMinimo();

                long elapsedTime = endTime - startTime;
                totalTimeFB += elapsedTime;

                //* Guloso

                startTime = System.currentTimeMillis();
                guloso.encontrarCaminhoMinimo(grafo);
                endTime = System.currentTimeMillis();

                List<Integer> caminhoMinimoG = guloso.getCaminhoMinimo();

                elapsedTime = endTime - startTime;
                totalTimeG += elapsedTime;

                //* Backtracking

                startTime = System.currentTimeMillis();
                backtracking.encontrarCaminhoMinimo(grafo);
                endTime = System.currentTimeMillis();

                List<Integer> caminhoMinimoBacktracking = backtracking.getCaminhoMinimo();

                elapsedTime = endTime - startTime;
                totalTimeBackTranking += elapsedTime;

                //* Programação Dinâmica

                startTime = System.currentTimeMillis();
                programacaoDinamica.encontrarCaminhoMinimo(grafo);
                endTime = System.currentTimeMillis();

                List<Integer> caminhoMinimoPD = programacaoDinamica.getCaminhoMinimo();

                elapsedTime = endTime - startTime;
                totalTimePD += elapsedTime;

                if (compararListas(caminhoMinimoFB, caminhoMinimoG, caminhoMinimoBacktracking, caminhoMinimoPD)) {
                    countSameAnswer++;
                }
            }

            double averageTimeFB = totalTimeFB / MAX_ITERATIONS_GLOBAL;
            System.out.println("Tempo total das iterações FB: " + totalTimeFB + "ms");
            System.out.println("Tempo médio das iterações FB: " + averageTimeFB + "ms");

            System.out.println();

            double averageTimeG = totalTimeG / MAX_ITERATIONS_GLOBAL;
            System.out.println("Tempo total das iterações G: " + totalTimeG + "ms");
            System.out.println("Tempo médio das iterações G: " + averageTimeG + "ms");

            System.out.println();

            double averageTimebacktracking = totalTimeBackTranking / MAX_ITERATIONS_GLOBAL;
            System.out.println("Tempo total das iterações Backtracking: " + totalTimeBackTranking + "ms");
            System.out.println("Tempo médio das iterações Backtracking: " + averageTimebacktracking + "ms");

            System.out.println();

            double averageTimePD = totalTimePD / MAX_ITERATIONS_GLOBAL;
            System.out.println("Tempo total das iterações PD: " + totalTimePD + "ms");
            System.out.println("Tempo médio das iterações PD: " + averageTimePD + "ms");

            System.out.println();

            System.out.println("Quantidade de soluções iguais obtidas: " + countSameAnswer);
        } catch (Exception err) {
            err.printStackTrace();
            System.out.println(err.getMessage());
        }
    }

    public static boolean compararListas(List<Integer> lista1, List<Integer> lista2, List<Integer> lista3, List<Integer> lista4) {
        if (lista1.size() != lista2.size() || lista1.size() != lista3.size() || lista1.size() != lista4.size()) {
            return false;
        }

        for (Integer vertice : lista1) {
            if (!lista2.contains(vertice) || !lista3.contains(vertice) || !lista4.contains(vertice)) {
                return false;
            }
        }

        return true;
    }

    public static int obterNMenosUm() {
        int vertices = 5;
        int nMinusOne = 0;
        long totalElapsedTime;
        boolean minusFourMin = true;
        long startTotalTime = System.currentTimeMillis();

        while (minusFourMin) {
            totalElapsedTime = 0;

            for (int i = 0; i < MAX_ITERATIONS_FB; i++) {
                int[][] grafo = grafoCompletoPonderado(vertices);

                long startElapsedTime = System.currentTimeMillis();
                ForcaBruta caixeiroViajante = new ForcaBruta();
                caixeiroViajante.encontrarCaminhoMinimo(grafo);
                long endElapsedTime = System.currentTimeMillis();

                long elapsedTime = endElapsedTime - startElapsedTime;
                totalElapsedTime += elapsedTime;

                if (elapsedTime > 3500) {
                    nMinusOne = vertices - 1;
                    minusFourMin = false;
                }
            }

            double averageElapsedTime = totalElapsedTime / MAX_ITERATIONS_FB;
            System.out.println("Tempo médio das iterações n=" + vertices + ": " + averageElapsedTime + "ms");

            if (minusFourMin) {
                vertices++;
            } else break;
        }

        long endTotalTime = System.currentTimeMillis();
        System.out.println();
        System.out.println("Tempo total para achar n-1: " + (endTotalTime - startTotalTime) + "ms");
        System.out.println();
        System.out.println("Número N-1: " + nMinusOne);
        System.out.println();

        return nMinusOne;
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
        Random aleatorio = new Random();
        int[][] matriz = new int[vertices][vertices];
        int valor;

        for (int i = 0; i < matriz.length; i++) {
            matriz[i][i] = -1;

            for (int j = i + 1; j < matriz.length; j++) {
                valor = aleatorio.nextInt(10) + 1;
                matriz[i][j] = valor;
                matriz[j][i] = valor;
            }
        }

        return matriz;
    }
}