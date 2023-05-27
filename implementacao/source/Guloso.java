package implementacao.source;
import java.util.*;
public class Guloso {

    private int[][] grafo; // Matriz de custos de viagem
    private List<Integer> caminhoMinimo; // Lista com o caminho de menor custo
    private int menorCusto = 0; // Menor custo encontrado
    private static int tamanho;

    public void encontrarCaminhoMinimo(int[][] grafo) {
        this.grafo = grafo;
        int n = grafo.length; // Tamanho do grafo
        caminhoMinimo = new ArrayList<>(); // Lista com os vértices que irão compor o caminho mínimo

        boolean[] visitados = new boolean[n]; // Lista booleana de vértices visitados
        visitados[0] = true; // Marca o vértice 0 como visitado

        encontrarCaminhoMinimo(0, caminhoMinimo, visitados, 0);
        caminhoMinimo.add(0); // Adiciona o vértice 0 ao final do caminho mínimos
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
    
    /**
     * Retorna uma matriz quadrada de "vertices" x "vertices" com números inteiros,
     * representando um grafo completo. A diagonal principal está preenchida com 
     * valor -1, indicando que não há aresta.
     * @param vertices A quantidade de vértices do grafo.
     * @return Matriz quadrada com custos de movimentação entre os vértices.
     */
    public static int[][] grafoCompletoPonderado(int vertices){
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
        tamanho = vertices-1; 
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
