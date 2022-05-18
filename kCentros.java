import java.util.*;
import java.io.*;
import java.io.IOException;

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
 
    public void floydwarshall(int adjacencymatrix[][])
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
    
    
    public static void main(String[] args)throws IOException{

        
   
        String path = "./teste.txt";

        buildFork grafo = lerArquivoTxt(path);

        //mostrarGrafo(grafo);

        int adjacency_matrix[][] = createAdjacencyMatrix(grafo);

        
        
        int numberofvertices = grafo.getVerticeSize();
        int INFINITY = 999999999;
 
        FloydWarshall floydwarshall = new FloydWarshall(numberofvertices);
        floydwarshall.floydwarshall(adjacency_matrix);
        
    }
}
