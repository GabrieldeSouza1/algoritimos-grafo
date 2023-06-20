package implementacao.app;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import implementacao.source.*;

public class Main {
    private static final double MAX_ITERATIONS_FB = 70.0;
    private static final double MAX_ITERATIONS_GLOBAL = 1000.0;

    public static void main(String[] args) {
        int countSameAnswer = 0;
        long totalTimeFB = 0, totalTimeG = 0, totalTimeBackTranking = 0, totalTimePD = 0;

        ForcaBruta forcaBruta = new ForcaBruta();
        Guloso guloso = new Guloso();
        Backtracking backtracking = new Backtracking();
        ProgramacaoDinamica programacaoDinamica = new ProgramacaoDinamica();
        StringBuilder inputBuilder = new StringBuilder();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Digite o grafo de entrada:");
            String line = null;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();

                if (line.equals("FIM")) {
                    break;
                }

                inputBuilder.append(line);
                inputBuilder.append("\n");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String input = inputBuilder.toString().trim();

        int[][] grafo = gerarGrafoPonderado(input);

        // * Força Bruta

        long startTime = System.currentTimeMillis();
        System.out.println(startTime);
        forcaBruta.encontrarCaminhoMinimo(grafo);
        long endTime = System.currentTimeMillis();

        List<Integer> caminhoMinimoFB = forcaBruta.getCaminhoMinimo();

        long elapsedTime = endTime - startTime;
        totalTimeFB += elapsedTime;

        // * Guloso

        startTime = System.currentTimeMillis();
        guloso.encontrarCaminhoMinimo(grafo);
        endTime = System.currentTimeMillis();

        List<Integer> caminhoMinimoG = guloso.getCaminhoMinimo();

        elapsedTime = endTime - startTime;
        totalTimeG += elapsedTime;

        // * Backtracking

        startTime = System.currentTimeMillis();
        backtracking.encontrarCaminhoMinimo(grafo);
        endTime = System.currentTimeMillis();

        List<Integer> caminhoMinimoBacktracking = backtracking.getCaminhoMinimo();

        elapsedTime = endTime - startTime;
        totalTimeBackTranking += elapsedTime;

        // * Programação Dinâmica

        startTime = System.currentTimeMillis();
        programacaoDinamica.encontrarCaminhoMinimo(grafo);
        endTime = System.currentTimeMillis();

        List<Integer> caminhoMinimoPD = programacaoDinamica.getCaminhoMinimo();

        elapsedTime = endTime - startTime;
        totalTimePD += elapsedTime;

        if (compararListas(caminhoMinimoFB, caminhoMinimoG,
                caminhoMinimoBacktracking, caminhoMinimoPD)) {
            countSameAnswer++;
        }

        double averageTimeFB = totalTimeFB / MAX_ITERATIONS_GLOBAL;
        System.out.println("Tempo total das iterações FB: " + totalTimeFB + "ms");
        System.out.println("Tempo médio das iterações FB: " + averageTimeFB + "ms");

        System.out.println();

        double averageTimeG = totalTimeG / MAX_ITERATIONS_GLOBAL;
        System.out.println("Tempo total das iterações G: " + totalTimeG + "ms");
        System.out.println("Tempo médio das iterações G: " + averageTimeG + "ms");

        System.out.println();

        double averageTimebacktracking = totalTimeBackTranking /
                MAX_ITERATIONS_GLOBAL;
        System.out.println("Tempo total das iterações Backtracking: " +
                totalTimeBackTranking + "ms");
        System.out.println("Tempo médio das iterações Backtracking: " +
                averageTimebacktracking + "ms");

        System.out.println();

        double averageTimePD = totalTimePD / MAX_ITERATIONS_GLOBAL;
        System.out.println("Tempo total das iterações PD: " + totalTimePD + "ms");
        System.out.println("Tempo médio das iterações PD: " + averageTimePD + "ms");

        System.out.println("\nQuantidade de soluções iguais obtidas: " + countSameAnswer);

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

                // * Força Bruta

                long startTime = System.currentTimeMillis();
                forcaBruta.encontrarCaminhoMinimo(grafo);
                long endTime = System.currentTimeMillis();

                List<Integer> caminhoMinimoFB = forcaBruta.getCaminhoMinimo();

                long elapsedTime = endTime - startTime;
                totalTimeFB += elapsedTime;

                // * Guloso

                startTime = System.currentTimeMillis();
                guloso.encontrarCaminhoMinimo(grafo);
                endTime = System.currentTimeMillis();

                List<Integer> caminhoMinimoG = guloso.getCaminhoMinimo();

                elapsedTime = endTime - startTime;
                totalTimeG += elapsedTime;

                // * Backtracking

                startTime = System.currentTimeMillis();
                backtracking.encontrarCaminhoMinimo(grafo);
                endTime = System.currentTimeMillis();

                List<Integer> caminhoMinimoBacktracking = backtracking.getCaminhoMinimo();

                elapsedTime = endTime - startTime;
                totalTimeBackTranking += elapsedTime;

                // * Programação Dinâmica

                startTime = System.currentTimeMillis();
                programacaoDinamica.encontrarCaminhoMinimo(grafo);
                endTime = System.currentTimeMillis();

                List<Integer> caminhoMinimoPD = programacaoDinamica.getCaminhoMinimo();

                elapsedTime = endTime - startTime;
                totalTimePD += elapsedTime;

                if (compararListas(caminhoMinimoFB, caminhoMinimoG,
                        caminhoMinimoBacktracking, caminhoMinimoPD)) {
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

            double averageTimebacktracking = totalTimeBackTranking /
                    MAX_ITERATIONS_GLOBAL;
            System.out.println("Tempo total das iterações Backtracking: " +
                    totalTimeBackTranking + "ms");
            System.out.println("Tempo médio das iterações Backtracking: " +
                    averageTimebacktracking + "ms");

            System.out.println();

            double averageTimePD = totalTimePD / MAX_ITERATIONS_GLOBAL;
            System.out.println("Tempo total das iterações PD: " + totalTimePD + "ms");
            System.out.println("Tempo médio das iterações PD: " + averageTimePD + "ms");

            System.out.println("\nQuantidade de soluções iguais obtidas: " + countSameAnswer);
        } catch (Exception err) {
            err.printStackTrace();
            System.err.println(err.getMessage());
        }


        // Testes plano convexo

        ArrayList<Long> tempoNoThread = new ArrayList<Long>();
        ArrayList<Long> tempoOnThread = new ArrayList<Long>();
        for (int i = 0; i < 50; i++) {
            ArrayList<Point2D> listaDePontos = planoConvexo.gerarPontos(1000000);
            ArrayList<ArrayList<Point2D>> listaTriangulo = planoConvexo.gerarTriagulos(listaDePontos);
            long comeco = 0;
            long fim = 0;
            comeco = System.currentTimeMillis();
            planoConvexo.onThread(listaTriangulo);
            fim = System.currentTimeMillis();

            tempoOnThread.add(fim - comeco);

            comeco = System.currentTimeMillis();
            planoConvexo.noThread(listaTriangulo);
            fim = System.currentTimeMillis();

            tempoNoThread.add(fim - comeco);

        }
        long soma = 0;
        for (Long numero : tempoNoThread) {
            soma += numero;
        }

        // Calcule a média
        double media = 0;
        if (!tempoNoThread.isEmpty()) {
            media = (double) soma / tempoNoThread.size();
        }
        System.out.println("Tempo medio das iterações sem thread: " + media);

        long somaCom = 0;
        for (Long numero : tempoOnThread) {
            somaCom += numero;
        }

        // Calcule a média
        double mediaCom = 0;
        if (!tempoNoThread.isEmpty()) {
            mediaCom = (double) somaCom / tempoNoThread.size();
        }
        System.out.println("Tempo medio das iterações com thread: " + mediaCom);

    }

    public static boolean compararListas(List<Integer> lista1, List<Integer> lista2, List<Integer> lista3,
            List<Integer> lista4) {
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
            }
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

    public static int[][] gerarGrafoPonderado(String input) {
        String[] rows = input.split("\\n");
        int rowCount = rows.length;
        int[][] matrix = new int[rowCount][];

        for (int i = 0; i < rowCount; i++) {
            String[] columns = rows[i].split(";");
            int columnCount = columns.length;
            matrix[i] = new int[columnCount];

            for (int j = 0; j < columnCount; j++) {
                matrix[i][j] = Integer.parseInt(columns[j]);
            }
        }

        return matrix;
    }
}