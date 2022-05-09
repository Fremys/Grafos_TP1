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