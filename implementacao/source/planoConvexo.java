package implementacao.source;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import javax.swing.*;

public class planoConvexo {

    /*
     * 1º Chamar o gerar pontos que irá retornar uma lista de pontos
     * 2º Chamar o método gerarTriangulos passando a lista de pontos que irá
     * retornar uma lista de listas
     * 3ª Se quiser mostrar os triagulos formados, chamar o metodo
     * desenharTriangulos passando a lista de pontos e a lista de triangulos
     * 4ª Chamar o metodo onThread ou noThread passado a lista de triangulos
     * Exemplo de uso:
     * ArrayList<Point2D> listaDePontos = planoConvexo.gerarPontos(1000000);
     * ArrayList<ArrayList<Point2D>> listaTriangulo =
     * planoConvexo.gerarTriagulos(listaDePontos);
     * 
     * long comecoSem = System.currentTimeMillis();
     * ArrayList<Point2D> resultadoPlanoConvexoNoThread =
     * planoConvexo.onThread(listaTriangulo);
     * long fimSem = System.currentTimeMillis();
     * 
     * long comecoCom = System.currentTimeMillis();
     * ArrayList<Point2D> resultadoPlanoConvexoOnThread =
     * planoConvexo.onThread(listaTriangulo);
     * long fimCom = System.currentTimeMillis();
     * 
     * 
     * System.out.println("Sem thread: " + (fimSem-comecoSem) + "ms");
     * System.out.println("Com thread: " + (fimCom-comecoCom) + "ms");
     * planoConvexo.desenharPontosComLinhas(resultadoPlanoConvexoOnThread,
     * listaDePontos);
     */

    public static ArrayList<Point2D> gerarPontos(int qnt) {
        ArrayList<Point2D> listaPontos = new ArrayList<>();
        HashSet<Point2D> conjuntoPontos = new HashSet<>();
        Random random = new Random();

        // Gerar 10 pontos aleatórios


        for (int i = 0; i < qnt; i++) {
            Point2D ponto = new Point2D.Double(random.nextInt(900000000) + 1, random.nextInt(900000000) + 1);
            if (conjuntoPontos.add(ponto)) {
                listaPontos.add(ponto);
            }
        }

        return listaPontos;
    }

    public static ArrayList<ArrayList<Point2D>> gerarTriagulos(ArrayList<Point2D> listaPontos) {

        ArrayList<ArrayList<Point2D>> listaTriangulos = new ArrayList<>();
        // Ordenar a lista de pontos com base no valor x
        Collections.sort(listaPontos, Comparator.comparingDouble(Point2D::getX));

        for (int i = 0; i < listaPontos.size() - 2; i += 3) {
            Point2D ponto1 = listaPontos.get(i);
            Point2D ponto2 = listaPontos.get(i + 1);
            Point2D ponto3 = listaPontos.get(i + 2);
            // Salvar os pontos utilizados em uma nova sublista
            ArrayList<Point2D> triangulo = new ArrayList<>();
            triangulo.add(ponto1);
            triangulo.add(ponto2);
            triangulo.add(ponto3);
            listaTriangulos.add(triangulo);
        }

        if (listaPontos.size() % 3 == 1) {
            ArrayList<Point2D> triangulo = new ArrayList<>();
            triangulo.add(listaPontos.get(listaPontos.size() - 1));
            listaTriangulos.add(triangulo);
        }

        if (listaPontos.size() % 3 == 2) {
            ArrayList<Point2D> triangulo = new ArrayList<>();
            triangulo.add(listaPontos.get(listaPontos.size() - 2));
            triangulo.add(listaPontos.get(listaPontos.size() - 1));
            listaTriangulos.add(triangulo);
        }

        return listaTriangulos;
    }

    public static ArrayList<Point2D> merge(ArrayList<Point2D> pedaco1, ArrayList<Point2D> pedaco2) {
        ArrayList<Point2D> resultado = new ArrayList<>();

        resultado.addAll(pedaco1);
        resultado.addAll(pedaco2);
        // Encontre o casco convexo dos pontos resultantes do merge
        ArrayList<Point2D> convexHull = convexHull(resultado);

        return convexHull;
    }

    public static ArrayList<Point2D> convexHull(ArrayList<Point2D> pontos) {
        ArrayList<Point2D> hull = new ArrayList<>();

        // Encontre o ponto mais à esquerda
        Point2D pontoMaisEsquerda = pontos.get(0);
        for (int i = 1; i < pontos.size(); i++) {
            Point2D pontoAtual = pontos.get(i);
            if (pontoAtual.getX() < pontoMaisEsquerda.getX()) {
                pontoMaisEsquerda = pontoAtual;
            }
        }

        hull.add(pontoMaisEsquerda);
        Point2D pontoAtual = pontoMaisEsquerda;
        Point2D proximoPonto;

        do {
            proximoPonto = pontos.get(0);

            for (int i = 1; i < pontos.size(); i++) {
                Point2D ponto = pontos.get(i);
                if (ponto.equals(pontoAtual)) {
                    continue;
                }
                int orientacao = orientacao(pontoAtual, ponto, proximoPonto);
                if (orientacao == -1
                        || orientacao == 0 && distancia(pontoAtual, ponto) > distancia(pontoAtual, proximoPonto)) {
                    proximoPonto = ponto;
                }
            }

            hull.add(proximoPonto);
            pontoAtual = proximoPonto;
        } while (!proximoPonto.equals(pontoMaisEsquerda));

        return hull;
    }

    public static int orientacao(Point2D p, Point2D q, Point2D r) {
        double valor = (q.getY() - p.getY()) * (r.getX() - q.getX()) - (q.getX() - p.getX()) * (r.getY() - q.getY());

        if (valor == 0) {
            return 0; // pontos colineares
        }
        return (valor > 0) ? 1 : -1; // sentido horário ou anti-horário
    }

    public static double distancia(Point2D p1, Point2D p2) {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static ArrayList<Point2D> noThread(ArrayList<ArrayList<Point2D>> listaTringulos) {

        if (listaTringulos.size() == 1) {
            return listaTringulos.get(0);
        }
        int meio = listaTringulos.size() / 2;

        ArrayList<Point2D> pedaco1 = new ArrayList<>();
        ArrayList<Point2D> pedaco2 = new ArrayList<>();
        pedaco1 = noThread(new ArrayList<>(listaTringulos.subList(0, meio)));

        pedaco2 = noThread(new ArrayList<ArrayList<Point2D>>(listaTringulos.subList(meio, listaTringulos.size())));
        return merge(pedaco1, pedaco2);
    }

    public static ArrayList<Point2D> onThread(ArrayList<ArrayList<Point2D>> listaTriangulos) {
        if (listaTriangulos.size() == 1) {
            return listaTriangulos.get(0);
        }

        int meio = listaTriangulos.size() / 2;

        ArrayList<Point2D> pedaco1 = new ArrayList<>();
        ArrayList<Point2D> pedaco2 = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<ArrayList<Point2D>> tarefa1 = () -> noThread(new ArrayList<>(listaTriangulos.subList(0, meio)));
        Callable<ArrayList<Point2D>> tarefa2 = () -> noThread(
                new ArrayList<>(listaTriangulos.subList(meio, listaTriangulos.size())));

        Future<ArrayList<Point2D>> future1 = executor.submit(tarefa1);
        Future<ArrayList<Point2D>> future2 = executor.submit(tarefa2);

        try {
            pedaco1 = future1.get();
            pedaco2 = future2.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();

        return merge(pedaco1, pedaco2);
    }

   public static void desenharPontosComLinhas(ArrayList<Point2D> pontos, ArrayList<Point2D> listaPontos) {


    JFrame frame = new JFrame("Desenho de Pontos");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel panel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            int panelWidth = getWidth() / 2; // Define a largura do painel como metade da largura do painel
            int panelHeight = getHeight() / 2; // Define a altura do painel como metade da altura do painel
            int panelX = (getWidth() - panelWidth) / 2; // Calcula a posição X do painel para que fique no centro horizontal do painel
            int panelY = (getHeight() - panelHeight) / 7; // Calcula a posição Y do painel para que fique no centro vertical do painel
            g2d.translate(panelX, panelY); // Translada o sistema de coordenadas para o centro do painel

            double escala = 0.000001;
            g2d.scale(escala, escala);

            // Desenhar os pontos
            int tamanhoPonto = 9999999;
            for (Point2D ponto : pontos) {
                int x = (int) ponto.getX();
                int y = (int) ponto.getY();

                g2d.setColor(Color.RED);
                g2d.fillOval(x - tamanhoPonto, y - tamanhoPonto, tamanhoPonto * 2, tamanhoPonto * 2);
            }

            for (Point2D ponto : listaPontos) {
                if (!pontos.contains(ponto)) {
                    int x = (int) ponto.getX();
                    int y = (int) ponto.getY();

                    g2d.setColor(Color.BLUE);
                    g2d.fillOval(x - tamanhoPonto / 12, y - tamanhoPonto / 12, tamanhoPonto / 8, tamanhoPonto / 8);
                }
            }

            // Desenhar as linhas
            for (int i = 0; i < pontos.size() - 1; i++) {
                Point2D ponto1 = pontos.get(i);
                Point2D ponto2 = pontos.get(i + 1);

                int x1 = (int) ponto1.getX();
                int y1 = (int) ponto1.getY();
                int x2 = (int) ponto2.getX();
                int y2 = (int) ponto2.getY();

                g2d.setColor(Color.BLACK);
                g2d.drawLine(x1, y1, x2, y2);
            }
        }
    };

    frame.getContentPane().add(panel);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizar a janela para preencher a tela
    frame.setVisible(true);
}

}
