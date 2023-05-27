package implementacao.source;
import java.util.*;
public class Guloso {

    private int[][] grafo;
    private List<Integer> caminhoMinimo;
    private int menorCusto = 0;
    private static int tamanho;

    public void encontrarCaminhoMinimo(int[][] grafo) {
        this.grafo = grafo;
        int n = grafo.length;
        caminhoMinimo = new ArrayList<>();

        boolean[] visitados = new boolean[n];
        visitados[0] = true;

        encontrarCaminhoMinimo(0, caminhoMinimo, visitados, 0);
        caminhoMinimo.add(0);
    }

    private void encontrarCaminhoMinimo(int nivel, List<Integer> caminhoAtual, boolean[] visitados, int custoAtual) {
        if (acabou(visitados)) {
            return;
        }
        int proximo = melhorAtual(nivel, visitados);
        caminhoMinimo.add(nivel);
        visitados[nivel] = true;
        menorCusto += grafo[nivel][proximo];
        encontrarCaminhoMinimo(proximo, caminhoMinimo, visitados, custoAtual);        
    }

    private int melhorAtual(int nivel, boolean[] visitados){
        int melhorAtual = Integer.MAX_VALUE;
        int proximoVertice = 0;
            for(int i=0; i<=tamanho; i++){
                if(grafo[nivel][i] > 0 && grafo[nivel][i]< melhorAtual && !visitados[i]){
                    melhorAtual = grafo[nivel][i];
                    proximoVertice = i;
                }
            }
        return proximoVertice;
    }

    private boolean acabou(boolean[] visitados){
        for (boolean visitado : visitados) {
            if(!visitado){
                return false;
            }
        }
        return true;
    }
    
    // método para gerar a matriz com os custos de viagem
         /**
     * Aleatório "fixo" para geração de testes repetitíveis
     */
    static Random aleatorio = new Random(42);

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
