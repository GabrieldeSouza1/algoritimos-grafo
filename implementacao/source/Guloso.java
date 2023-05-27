package implementacao.source;
import java.util.*;
public class Guloso {

    private List<Integer> caminhoMinimo;
    private int menorCusto = 0;

    public void encontrarCaminhoMinimo(int[][] grafo) {
        caminhoMinimo = new ArrayList<>();

        boolean[] visitados = new boolean[grafo.length];
        visitados[0] = true;

        encontrarCaminhoMinimo(grafo, 0, caminhoMinimo, visitados, 0);
        caminhoMinimo.add(0);
    }

    private void encontrarCaminhoMinimo(int[][] grafo, int nivel, List<Integer> caminhoAtual, boolean[] visitados, int custoAtual) {
        if (acabou(visitados)) {
            return;
        }
        int proximo = melhorAtual(grafo, nivel, visitados);
        caminhoMinimo.add(nivel);
        visitados[nivel] = true;
        menorCusto += grafo[nivel][proximo];
        encontrarCaminhoMinimo(grafo, proximo, caminhoMinimo, visitados, custoAtual);
    }

    private int melhorAtual(int[][] grafo, int nivel, boolean[] visitados){
        int melhorAtual = Integer.MAX_VALUE;

        int proximoVertice = 0;

        for(int i = 0; i < grafo.length; i++){
            if(grafo[nivel][i] > 0 && grafo[nivel][i] < melhorAtual && !visitados[i]){
                melhorAtual = grafo[nivel][i];
                proximoVertice = i;
            }
        }

        return proximoVertice;
    }

    private boolean acabou(boolean[] visitados) {
        for (boolean visitado : visitados) {
            if(!visitado){
                return false;
            }
        }

        return true;
    }

    public List<Integer> getCaminhoMinimo() {
        return caminhoMinimo;
    }

    public int getMenorCusto() {
        return menorCusto;
    }
}
