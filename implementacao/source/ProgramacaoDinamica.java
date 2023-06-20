package implementacao.source;

import java.util.ArrayList;
import java.util.List;

public class ProgramacaoDinamica {

    private List<Integer> caminhoMinimo;
    private int[][] grafo;
    private int custoTotal = 0;

    public void encontrarCaminhoMinimo(int[][] grafo) {
        this.custoTotal = 0;
        this.grafo = grafo;
        int n = grafo.length;
        caminhoMinimo = new ArrayList<>();
        int[][] tabela = new int[1 << n][n]; // Tabela de programação dinâmica

        // Inicializar primeira linha da tabela
        for (int i = 0; i < n; i++) {
            tabela[1 << i][i] = grafo[i][0];
        }

        // Preencher a tabela de forma iterativa
        for (int subconjunto = 1; subconjunto < (1 << n); subconjunto++) {
            for (int cidade = 0; cidade < n; cidade++) {
                if ((subconjunto & (1 << cidade)) == 0) {
                    continue; // A cidade não pertence ao subconjunto atual
                }
                int subconjuntoAnterior = subconjunto ^ (1 << cidade);
                tabela[subconjunto][cidade] = Integer.MAX_VALUE;
                for (int ultimaCidade = 0; ultimaCidade < n; ultimaCidade++) {
                    if ((subconjuntoAnterior & (1 << ultimaCidade)) != 0) {
                        int custo = tabela[subconjuntoAnterior][ultimaCidade] + grafo[ultimaCidade][cidade];
                        tabela[subconjunto][cidade] = Math.min(tabela[subconjunto][cidade], custo);
                    }
                }
            }
        }

        int subconjunto = (1 << n) - 1;
        int ultimaCidade = 0;
        while (true) {
            caminhoMinimo.add(ultimaCidade);

            int proximoSubconjunto = subconjunto ^ (1 << ultimaCidade);
            if (proximoSubconjunto == 0) {
                break;
            }
            int cidadeAnterior = ultimaCidade;
            ultimaCidade = -1;
            for (int i = 0; i < n; i++) {
                if ((proximoSubconjunto & (1 << i)) != 0) {
                    if (ultimaCidade == -1 || tabela[proximoSubconjunto][i]
                            + grafo[i][cidadeAnterior] < tabela[proximoSubconjunto][ultimaCidade]
                                    + grafo[ultimaCidade][cidadeAnterior]) {
                        ultimaCidade = i;
                    }
                }
            }
            subconjunto = proximoSubconjunto;
        }

        caminhoMinimo.add(0); // Adicionar a cidade inicial novamente para fechar o ciclo
    }

    public List<Integer> getCaminhoMinimo() {
        return caminhoMinimo;
    }

    public int getCustoTotal() {
        // Calcular o custo total do caminho mínimo
        this.custoTotal = 0;
        for (int i = 0; i < this.caminhoMinimo.size() - 1; i++) {
            int cidadeAtual = caminhoMinimo.get(i);
            int proximaCidade = caminhoMinimo.get(i + 1);
            this.custoTotal += this.grafo[cidadeAtual][proximaCidade];
        }

        return custoTotal;
    }
}
