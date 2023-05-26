package implementacao.test;

import implementacao.source.ForcaBruta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ForcaBrutaTest {

    private static final int MAX_ITERATIONS = 70;
    private static final int TIMEOUT_SECONDS = 240;

    @Test
    public void testTempoMedioSolucao() {
        int vertices = 5;
        int nMinusOne = 0;
        long totalTime;
        boolean minusFourMin = true;

        while (minusFourMin) {
            long elapsedTime = 0;
            totalTime = 0;

            for (int i = 0; i < MAX_ITERATIONS; i++) {
                int[][] grafo = ForcaBruta.grafoCompletoPonderado(vertices);

                long startTime = System.currentTimeMillis();
                ForcaBruta caixeiroViajante = new ForcaBruta();
                caixeiroViajante.encontrarCaminhoMinimo(grafo);
                long endTime = System.currentTimeMillis();

                elapsedTime = endTime - startTime;
                totalTime += elapsedTime;

                if (elapsedTime > 240000) {
                    minusFourMin = false;
                }
            }

            double averageTime = totalTime / 70.0;
            System.out.println("Tempo médio de solução: " + averageTime + "ms");

            if(!minusFourMin) {
                nMinusOne = vertices - 1;
            } else
                vertices++;

        }
            System.out.println("Número N-1: " + nMinusOne);
    }
}