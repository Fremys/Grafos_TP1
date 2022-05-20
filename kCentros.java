import java.util.*;
import java.io.*;
import java.io.IOException;
/**
 * Class name: Combinacao.java
 *
 * Classe que especifica o esquema de combinacao
 * usando a contagem binaria.
 *
 * Autor: Daemonio (Marcos Paulo Ferreira)
 * Contato: undefinido gmail com
 * Homepage: https://daemoniolabs.wordpress.com
 *
 * Mon Jul  4 14:44:14 BRT 2011
 *
 */
 
class Combinacao {
    private int r ;
    private String[] entrada ;
    private int MAX ;
    private int N ;
 
    /** se r e' zero entao iremos fazer todas
     * as combinacoes (com qualquer quantidade
     * de elementos).
     */
    public Combinacao(String[] entrada, int r) {
        this.r = r ;
        this.entrada = entrada ;
        this.MAX = ~(1 << entrada.length) ;
        this.N = 1;
    }
 
    /** Retorna true quando ha pelo menos
     * uma combinacao disponivel.
     */
    public boolean hasNext() {
        if ( r != 0 ) {
            while ( ((this.N & this.MAX) != 0) && (countbits() != r) ) N+=1 ;
        }
 
        return (this.N & this.MAX) != 0;
    }
 
    /** Retorna a quantidade de bits ativos (= 1)
     * de N.
     */
    private int countbits() {
        int i;
        int c;
 
        i = 1;
        c = 0;
        while ( (this.MAX & i) != 0 ) {
            if ( (this.N & i) != 0) {
                c++;
            }
            i = i << 1 ;
        }
 
        return c ;
    }
 
    /** Util para obter o tamanho da saida. Esse
     * tamanho e' o numero de posicoes do vetor
     * retornado por next.
     */
    public int getSaidaLength() {
        if (r != 0) {
            return r;
        }
 
        return this.countbits();
    }
 
    /** Retorna uma combinacao.
     *
     * ATENCAO: Sempre use next() quando se
     * tem certeza que ha uma combinacao
     * disponivel. Ou seja, sempre use next()
     * quando hasNext() retornar true.
     */
    public String[] next() {
        int saida_index, entrada_index, i;
 
        String[] saida = new String[this.getSaidaLength()];
 
        entrada_index = 0;
        saida_index = 0;
        i = 1;
 
        while ((this.MAX & i) != 0) {
            if ((this.N & i) != 0) {
                saida[saida_index] = entrada[entrada_index];
                saida_index += 1;
            }
            entrada_index += 1;
            i = i << 1;
        }
 
        N += 1;
 
        return saida;
    }
}

class Aresta{
    private int custo = 1;
    private int origem;
    private int destino;

    public Aresta(int custo, int origem, int destino){
        this.custo = custo;
        this.origem = origem;
        this.destino = destino;
    }

    public int getCusto(){
        return this.custo;
    }

    public int getOrigem(){
        return this.origem;
    }

    public int getDestino(){
        return this.destino;
    }

    public void setCusto(int custo){
        this.custo = custo;
    }

    public void setOrigem(int origem){
        this.origem = origem;
    }

    public void setDestino(int destino){
        this.destino = destino;
    }
}

class buildFork{

    public int vertices;
    public int Arestas;

  
    //limitar o numero de vertices
    int limiteVertices;
    int raio;
    int infinito = 999999999;
  
    // definir dados
    Random random = new Random();
    public List<List<Aresta>> grafo;
    
    //construtor
    public buildFork(int qtdVertices, int qtdArestas)
    {
        this.vertices = qtdVertices;
        
        // Instanciando o grafo
        grafo = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++){
            grafo.add(new ArrayList<Aresta>());
        }

        //Construindo o grafo
        for(int i = 0; i < this.vertices; i++){

            for(int j = 0; j < this.vertices; j++){

                if(i != j){
                    //Adicionar aresta nos vertices
                    addArestas(infinito, i, j, true);
                }
            }

        }
    }

    // criar arestas entre o par de vertices
    public void addArestas(int custo, int v, int w, boolean iniciais)
    {
        if(iniciais){
            Aresta aresta = new Aresta(custo, v, w);
            grafo.get(v).add(aresta);
        }else{

            Aresta newAresta = new Aresta(custo, v, w);
            Aresta newArestaI = new Aresta(custo, w, v);

            Aresta oldAresta = new Aresta(999999999, v, w);
            Aresta oldArestaI = new Aresta(999999999, w, v);

            int indexAresta = getIndexAresta(oldAresta);
            int indexArestaI = getIndexAresta(oldArestaI);

            grafo.get(v).set(indexAresta, newAresta);
            grafo.get(w).set(indexArestaI, newArestaI);

        }
    }

    public int getVerticeSize(){
        return grafo.size();
    }

    public Aresta getAresta(int v, int d){

        Aresta aresta = null;

        for(int i = 0; i < grafo.get(v).size(); i++){

            aresta = grafo.get(v).get(i);

            if(aresta.getDestino() == d)
                return aresta;
        }

        return aresta;
    }

    public int getIndexAresta(Aresta aresta){

        int index = -1;

        for(int i = 0; i < grafo.get(aresta.getOrigem()).size(); i++){

            if(grafo.get(aresta.getOrigem()).get(i).getDestino() == aresta.getDestino()){
                
                index = i;
                return index;
            }
        }

        return index;
    }

    public Aresta getArestaForIndex(int v, int index){

        Aresta aresta = grafo.get(v).get(index);
        
        return aresta;
    }

    public int getArestaSize(int v){
        return grafo.get(v).size();
    }
}

class FloydWarshall
{
    private int distancematrix[][];
    private int numberofvertices;
    public static final int INFINITY = 999999999;
 
    public FloydWarshall(int numberofvertices)
    {
        distancematrix = new int[numberofvertices][numberofvertices];
        this.numberofvertices = numberofvertices;
    }
 
    public int[][] floydwarshall(int adjacencymatrix[][])
    {
        for (int source = 0; source < numberofvertices; source++)
        {
            for (int destination = 0; destination < numberofvertices; destination++)
            {
                distancematrix[source][destination] = adjacencymatrix[source][destination];
            }
        }
 
        for (int intermediate = 0; intermediate < numberofvertices; intermediate++)
        {
            for (int source = 0; source < numberofvertices; source++)
            {
                for (int destination = 0; destination < numberofvertices; destination++)
                {
                    if (distancematrix[source][intermediate] + distancematrix[intermediate][destination]
                         < distancematrix[source][destination])
                        distancematrix[source][destination] = distancematrix[source][intermediate] 
                            + distancematrix[intermediate][destination];
                }
            }
        }
        
        
        for (int source = 0; source < numberofvertices; source++)
        System.out.print("\t" + source);
        
        System.out.println();
        for (int source = 0; source < numberofvertices; source++)
        {
            System.out.print(source + "\t");
            for (int destination =0; destination < numberofvertices; destination++)
            {
                System.out.print(distancematrix[source][destination] + "\t");
            }
            System.out.println();
        }

        return distancematrix;
    }
}

public class kCentros{
    

    public static void mostrarGrafo(buildFork grafos){

        for(int i = 0; i < grafos.getVerticeSize(); i++){
            System.out.println("vertice[" + i +"]: ");
            for(int j = 0; j < grafos.getArestaSize(i); j++){
                System.out.println("");
                System.out.println( "\tOrigem: " + i + "\n\tDestino: " + grafos.getArestaForIndex(i, j).getDestino() + "\n\tCusto: " + grafos.getArestaForIndex(i, j).getCusto() + ", ");

            }

            System.out.println("----------------------");
        }
    }

    public static buildFork lerArquivoTxt(String path) throws IOException{

        BufferedReader buffRead = new BufferedReader(new FileReader(path));
        String linha = "";

        //Construir grafo inicial com distancias infinitas
        buildFork grafo;
        linha = buffRead.readLine();
        String[] cabecalho = linha.split(" ");
        grafo = new buildFork(Integer.parseInt(cabecalho[1]), Integer.parseInt(cabecalho[2]));


        boolean finish = false;

        while (!finish) {

            linha = buffRead.readLine();

            if(linha == null){
                finish = true;
            }
            else{

                String[] aresta = linha.split(" ");
                grafo.addArestas(Integer.parseInt(aresta[3]), Integer.parseInt(aresta[1]) - 1, Integer.parseInt(aresta[2]) - 1, false);
            }
        }

        //fechar arquivo após leitura
        buffRead.close();

        return grafo;
    }


    public static int[][] createAdjacencyMatrix(buildFork grafo) {

        int[][] AdjacencyMatrix = new int[grafo.getVerticeSize()][grafo.getVerticeSize()];

        for(int i = 0; i < grafo.getVerticeSize(); i++){
            for(int j = 0; j < grafo.getVerticeSize(); j++){

                if(i != j){

                    Aresta aresta = grafo.getAresta(i, j);
                    AdjacencyMatrix[i][j] = aresta.getCusto();

                }else{
                
                    AdjacencyMatrix[i][j] = 0;
                }
            }
        }
        return AdjacencyMatrix;
    }

    public static Integer findMin(List<Integer> list)
    {
  
        // check list is empty or not
        if (list == null || list.size() == 0) {
            return Integer.MAX_VALUE;
        }
  
        // create a new list to avoid modification 
        // in the original list
        List<Integer> sortedlist = new ArrayList<>(list);
  
        // sort list in natural order
        Collections.sort(sortedlist);
  
        // first element in the sorted list
        // would be minimum
        return sortedlist.get(0);
    }
        
    public static void main(String[] args)throws IOException{
   
        String path = "./teste.txt";

        buildFork grafo = lerArquivoTxt(path);

        //mostrarGrafo(grafo);

        int adjacency_matrix[][] = createAdjacencyMatrix(grafo);

        int numerDeVertices = grafo.getVerticeSize();
        int INFINITY = 999999999;
        
        FloydWarshall floydwarshall = new FloydWarshall(numerDeVertices);
        
        int[][] distanceMatrix = floydwarshall.floydwarshall(adjacency_matrix);

        String[] str = new String[grafo.getVerticeSize()];
        String [] saida;

        for(int i = 0; i  < grafo.getVerticeSize(); i ++)
            str[i] = ""+i+"";

 
        /** 
         * Combinações de 5 elementos agrupados
         * de 3 em 3.
        */

        for(int nCentros = 2; nCentros <= 2; nCentros++){

            Combinacao comb1 = new Combinacao(str, nCentros);
            
            while (comb1.hasNext()) {
                
                saida = comb1.next();
                
                HashMap<Integer, List<Integer>> clusters;

                clusters = new HashMap<>();
                for (int i = 0; i < nCentros; i++){
                    clusters.put( Integer.parseInt(saida[i]) ,new ArrayList<Integer>());
                }
                
                for(int y = 0; y < grafo.getVerticeSize(); y++){
                    
                    List<Integer> distancias = new ArrayList<Integer>();

                    distancias.clear();
                    //int[] caminho_minimo = new int[nCentros];

                    for(int x = 0; x < nCentros; x++){
                        
                        int distancia = distanceMatrix[y][Integer.parseInt(saida[x])];
                        //caminho_minimo[x] = distancia;
                        distancias.add(distancia);
                    }

                    int menorDistancia = findMin(distancias);

                    int clusterCorrespondenteIndex = distancias.indexOf(menorDistancia);

                    int clusterCorrespondente = Integer.parseInt(saida[clusterCorrespondenteIndex]);
                    
                    clusters.get(clusterCorrespondente).add(y);
                    
                }

                for(int i = 0; i < saida.length; i++){
                    int valor = Integer.parseInt(saida[i]);
                    System.out.print("Cluster "+ valor + ": ");
                    for(int j = 0; j < clusters.get(valor).size(); j++){
                        
                        System.out.print(clusters.get(valor).get(j) + ", ");
                    }
                    System.out.println();
                }
                System.out.println("=============================");
            }

        }


        
    }
}
