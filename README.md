# Trabalho Prático parte 1: Caixeiro Viajante

Na segunda parte da disciplina, estamos focados no estudo de problemas intratáveis, tipicamente pertencente às classes NP, e nas técnicas de projeto de algoritmos que podem nos ajudar a encontrar soluções de compromisso adequadas.

Na primeira parte do trabalho prático, iremos investigar como pode ser impressionante a diferença de tempo para resolver um problema com força bruta ou resolvê-lo mais rapidamente, ao mesmo tempo que veremos quão boa é a resposta da solução mais rápida.

O problema a ser tratado é bem conhecido: o Caixeiro Viajante. Dado um grafo G, completo, em que os vértices representam as cidades e as arestas representam o custo de se deslocar entre duas cidades, queremos saber qual é o caminho de menor custo que saia de uma cidade, passe por todas as outras sem repeti-las e volte à cidade de partida.

## As tarefas preparatórias do seu grupo de trabalho são:
  - [X] Projetar e implementar uma solução para o problema do Caixeiro Viajante utilizando força bruta. O professor fornecerá um “gerador de dados” que criará uma matriz VxV com os custos de viagem. O grupo pode decidir usar ou não este método;

  - [X] Projetar e implementar uma solução para o problema do Caixeiro Viajante utilizando algoritmo guloso. O grupo deve decidir se vai utilizar o critério demonstrado em aula ou outro à escolha.
  
## A tarefa principal tem duas partes:
  - [X] Criar um teste automatizado. Este teste deve gerar grafos com a quantidade V de vértices crescente, iniciando em 5, e tentar resolver o problema do Caixeiro Viajante com estes grafos. O teste de cada tamanho de conjunto deve ser repetido 70 vezes, registrando o tempo médio de solução para cada tamanho de conjunto. O teste será interrompido quando uma iteração exceder o tempo de 4 minutos. Você então saberá o número N-1, que representa a quantidade de vértices de um grafo no qual o algoritmo de força bruta consegue fornecer a resposta em até aproximadamente 3,5 segundos;

  - [X] A partir dos resultados acima, criar um relatório contendo o que é pedido:
    - [X] Gere 1000 grafos aleatórios, cada um com (N-1) vértices;
    - [X] Para cada um destes grafos, execute a solução do Caixeiro Viajante utilizando força bruta. Armazene a solução obtida e o tempo médio para encontrar cada solução;
    - [X] Para cada um destes grafos, execute a solução do Caixeiro Viajante utilizando o algoritmo guloso. Verifique em quantas destas vezes ele encontrou a solução obtida pela força bruta e armazene o tempo médio para encontrar cada solução;
    - [X] Comente as respostas encontradas.
  
### Observe que um teste deste porte vai demorar cerca de 60 minutos para a última iteração. Não deixe para fazer tudo na última hora.

## Regras:
  - Grupos de 4 alunos. Caso um grupo tenha menos alunos, estará sujeito à alocação de alunos
para completar as vagas por parte do professor;
  - Este parte do trabalho não tem apresentação ao professor, apenas uma entrega no Canvas;
