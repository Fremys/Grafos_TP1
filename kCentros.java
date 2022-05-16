import java.util.*;
import java.io.*;

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
}

class buildFork{

    public int vertices;
    public int Arestas;
  
    //limitr o numero de vertices
    int limiteVertices;
    int raio;
  
    // definir dados
    Random random = new Random();
    public List<List<Aresta>> listaAdjacentes;
    
    //construtor
    public buildFork(int qtdVertices, int raio)
    {
        this.raio = raio;
        
        this.vertices = qtdVertices;
        
        // Construindo lista de arestas para cada vertice do grafo
        listaAdjacentes = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++){
            listaAdjacentes.add(new ArrayList<Aresta>());
        }

        //construido o grafo

        for(int i = 0; i < this.vertices; i++){

            for(int j = 0; j < this.vertices; j++){

                if( i != j){
                    // add an edge between them
                    addArestas(i, j);
                }
            }

        }
    }
  
  
    // criar arestas entre o par de vertices
    void addArestas(int v, int w)
    {
        Aresta aresta = new Aresta(1, v, w);
        listaAdjacentes.get(v).add(aresta);
    }

}

public class FloydWarshall
{
    private int distancematrix[][];
    private int numberofvertices;
    public static final int INFINITY = 999;
 
    public FloydWarshall(int numberofvertices)
    {
        distancematrix = new int[numberofvertices + 1][numberofvertices + 1];
        this.numberofvertices = numberofvertices;
    }
 
    public void floydwarshall(int adjacencymatrix[][])
    {
        for (int source = 1; source <= numberofvertices; source++)
        {
            for (int destination = 1; destination <= numberofvertices; destination++)
            {
                distancematrix[source][destination] = adjacencymatrix[source][destination];
            }
        }
 
        for (int intermediate = 1; intermediate <= numberofvertices; intermediate++)
        {
            for (int source = 1; source <= numberofvertices; source++)
            {
                for (int destination = 1; destination <= numberofvertices; destination++)
                {
                    if (distancematrix[source][intermediate] + distancematrix[intermediate][destination]
                         < distancematrix[source][destination])
                        distancematrix[source][destination] = distancematrix[source][intermediate] 
                            + distancematrix[intermediate][destination];
                }
            }
        }
 
        for (int source = 1; source <= numberofvertices; source++)
            System.out.print("\t" + source);
 
        System.out.println();
        for (int source = 1; source <= numberofvertices; source++)
        {
            System.out.print(source + "\t");
            for (int destination = 1; destination <= numberofvertices; destination++)
            {
                System.out.print(distancematrix[source][destination] + "\t");
            }
            System.out.println();
        }
    }
 
    public static void main(String... arg)
    {
        int adjacency_matrix[][];
        int numberofvertices;
 
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the number of vertices");
        numberofvertices = scan.nextInt();
 
        adjacency_matrix = new int[numberofvertices + 1][numberofvertices + 1];
        System.out.println("Enter the Weighted Matrix for the graph");
        for (int source = 1; source <= numberofvertices; source++)
        {
            for (int destination = 1; destination <= numberofvertices; destination++)
            {
                adjacency_matrix[source][destination] = scan.nextInt();
                if (source == destination)
                {
                    adjacency_matrix[source][destination] = 0;
                    continue;
                }
                if (adjacency_matrix[source][destination] == 0)
                {
                    adjacency_matrix[source][destination] = INFINITY;
                }
            }
        }
 
        System.out.println("The Transitive Closure of the Graph");
        FloydWarshall floydwarshall = new FloydWarshall(numberofvertices);
        floydwarshall.floydwarshall(adjacency_matrix);
        scan.close();
    }
}

public class kCentros{
  
    public static void main(String[] args){
        int vertices =3;
        int raio = 6;
        buildFork grafo = new buildFork(vertices, raio);

        for(int i = 0; i < grafo.listaAdjacentes.size(); i++){
            System.out.println("vertice[" + i +"]: ");
            for(int j = 0; j < grafo.listaAdjacentes.get(i).size(); j++){
                System.out.println("");
                System.out.println( "\tOrigem: " + grafo.listaAdjacentes.get(i).get(j).getOrigem() + "\n\tDestino: " + grafo.listaAdjacentes.get(i).get(j).getDestino() + "\n\tCusto: " + grafo.listaAdjacentes.get(i).get(j).getCusto() + ", ");

            }

            System.out.println("----------------------");
        }
        
    
    }
}