package Model;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;

public class Graph {
//    lista zaznaczonych obiektów
    private ArrayList<Vertex> selectedVertex = new ArrayList<>();
//    lista zaznaczonych krawędzi
    private ArrayList<Edge> selectedEdge = new ArrayList<>();

//    Pola uzywane do połączenia wierzchołków
    private Vertex head;
    private Vertex tail;
    private boolean isHeadSet;
    private boolean isTailSet;
    private boolean connectingComplete;

    private ArrayList<Vertex> vertexList = new ArrayList<>();
    private ArrayList<Edge> edgeList = new ArrayList<>();
    private int edgeNumering = 1;
    private int vertexNumering = 0;
    private int[][] adjencencyMatrix = new int[0][0];
    Pane world;

    public Graph( Pane world) {
        this.world = world;

    }


    public void addVertex(double x, double y) {
        Vertex vertex = new Vertex(this, vertexNumering, x ,y, world);
//        Zinkrementuj numer wierzchołka
        vertexNumering++;
//        Dodaj wierzołek do listy
        vertexList.add(vertex);
//      Dostosować do tego macierz
        if (adjencencyMatrix.length > 0) {  // Jeżei macierz nie jest pusta to dodaj nowy wierzchołek do macierzy
            int[][] oldMatrix = adjencencyMatrix;
            adjencencyMatrix = new int[vertexList.size()][vertexList.size()];
            int counter = 0;
            for (int[] ver : oldMatrix) {
                System.arraycopy(ver, 0, adjencencyMatrix[counter++], 0, oldMatrix.length);
            }
        } else if (adjencencyMatrix.length == 0) {
            adjencencyMatrix = new int[1][1];
        }

//        macierz jest dostosowana
//        dodaj reprezentacje graficzna wierzcholka do Pane
        vertex.addToWorld();




    }


    public void setHead(Vertex head) {
        this.head = head;
    }

    public void setTail(Vertex tail){
        this.tail = tail;
    }
    public Vertex getHead(){
        return head;
    }
    public Vertex getTail(){
        return  tail;
    }

    public boolean isConnectingComplete(){
        return connectingComplete;
    }
    public void resetConnectingVertexData(){
        head = null;
        tail = null;
        isHeadSet = false;
        isTailSet = false;
        connectingComplete = false;
    }


    public Vertex getSelectedVertexForConneting(Vertex vertex) {

        for (int i = 0; i < vertexList.size() ; i++) {
            if (vertexList.get(i).isSelected() && !vertexList.get(i).equals(vertex)){
                return vertexList.get(i);
            }
        }
        return null;
    }

    public void setHeadSet(boolean headSet) {
        isHeadSet = headSet;
    }

    public void setTailSet(boolean tailSet) {
        isTailSet = tailSet;
    }

    public boolean isHeadSet(){
        return isHeadSet;
    }

    public boolean isTailSet() {
        return isTailSet;
    }

    public void connectTwoVertex(Vertex head, Vertex tail) {

        // etykieta pierszego wierzchoka
        int headLabel = head.getLabel();
        int tailLabel = tail.getLabel();
        System.out.println("Vertex labels :" + headLabel + " " + tailLabel);
        adjencencyMatrix[headLabel][tailLabel] = 1;
        Edge edge = new  Edge(head, tail, edgeNumering++);
        edgeList.add(edge);
        connectingComplete = true;
        world.getChildren().add(edge.getEdgeShape());
    }

    public void deleteVertex(Vertex vertex) {

//        Znajdz wszytkie krawędzie jakie sa związane z tym wierzcholkiem i usun je
        ArrayList<Edge> toRemove = new ArrayList<>();
        for (Edge e : edgeList ) {
            if (e.getHead().equals(vertex) || e.getTail().equals(vertex)){
                toRemove.add(e);
            }
        }
        for (Edge e: toRemove) {
            edgeList.remove(e);
            world.getChildren().remove(e.getEdgeShape());
        }

        // 1. usuń z tablicy
        int vertexLabel = vertex.getLabel();
        if (vertexList.size() == 1){
            adjencencyMatrix = new int[1][1];
        } else if (vertexLabel == 0) {
            int[][] oldMatrix = adjencencyMatrix;
            adjencencyMatrix = new int[vertexList.size()-1][vertexList.size()-1];
            int counter = 0;
            for (int i = 1; i < oldMatrix.length; i++) {
                System.arraycopy(oldMatrix[i], 1, adjencencyMatrix[counter++], 0, adjencencyMatrix.length);
            }

        } else if (vertexLabel == vertexList.size() - 1) {
            int[][] oldMatrix = adjencencyMatrix;
            adjencencyMatrix = new int[vertexList.size() - 1][vertexList.size() - 1];
            int counter = 0;

            for (int i = 0; i < oldMatrix.length - 1; i++) {
                System.arraycopy(oldMatrix[i], 0, adjencencyMatrix[counter++], 0, adjencencyMatrix.length);
            }
        } else {

            int[][] oldMatrix = adjencencyMatrix;
            adjencencyMatrix = new int[vertexList.size() - 1][vertexList.size() - 1];
            int couterRow = 0;
            int couterCol = 0;
            for (int i = 0; i < oldMatrix.length; i++) {

                if (i != vertexLabel) {
                    couterCol = 0;
                    for (int j = 0; j < oldMatrix.length; j++) {
                        if (j != vertexLabel)
                            adjencencyMatrix[couterRow][couterCol++] = oldMatrix[i][j];
                    }
                    couterRow++;
                }

            }


        }


//        2. usuń z listy wierzchołków i świata
        vertexList.remove(vertex);

//        3. prznumeruj wierzchołki
        if (vertexLabel != vertexList.size() - 1 ) {
            vertexNumering = 0;
            for (Vertex v : vertexList) {
                v.setLabel(vertexNumering++);
                v.setLabelText();
            }

        }

    }

    public void deleteEdge(Edge edge){
        // żeby usunąć krawędz konieczne jest dowiedzenie się do jakich wierzchołków on należy
        int headLabel = edge.getHead().getLabel();
        int tailLabel = edge.getTail().getLabel();
        // usunąć wpis w tabeli
        adjencencyMatrix[headLabel - 1][tailLabel - 1] = 0;
        // usunać krawęć z listy krawędzi
        edgeList.remove(edge);


    }

    Edge getEdge(int index){return edgeList.get(index);}

    Vertex getVertex(int index) {
        return vertexList.get(index);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int[] arr : adjencencyMatrix) {
            string.append(Arrays.toString(arr)).append('\n');
        }
        return string.toString();
    }

    public ArrayList<Edge> getEdgeList(){
        return edgeList;
    }


    public void loopThroughVertexListAndFindTheOneClickedOn(double x , double y){
        for (Vertex v : vertexList ) {
            if (v.isClickedOn(x,y)){
                v.setSelected(true);
                selectedVertex.add(v);
            }
        }
    }

    public void unSelectObjects(){
        for (Vertex v: selectedVertex) {
            if(v.isSelected()){
                v.setSelected(false);
            }
        }
        selectedVertex.clear();

        for (Edge e :edgeList) {
            if (e.isSelected()){
                e.setSelected(false);
            }
        }
    }


    public void deleteSelectedObject(){
        for(Vertex v: selectedVertex){
            deleteVertex(v);
            v.deleteFromWorld();
        }
        selectedVertex.clear();
    }

    public ArrayList<Vertex> getSelectedVertexes(){
        return selectedVertex;
    }

    public ArrayList<Vertex> getVertexList(){
        return vertexList;
    }
}
