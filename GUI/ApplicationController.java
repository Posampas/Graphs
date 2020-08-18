package GUI;

import Model.Graph;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ApplicationController {

    Graph graph;

    enum State {ADDING_VERTEX, CONNECTING_VERTEX, WAINTING, DELETING}

    State programState;


    @FXML
    public void initialize() {
        graph = new Graph(world);
        programState = State.WAINTING;
        Thread t = new Thread(new InputTread(this));
        t.setDaemon(true);
        t.start();
/*        ApplicationClass.scene.setOnKeyPressed(event -> {
            System.out.println(event.getCode().toString());
        });*/


    }

    @FXML
    BorderPane pane;

    @FXML
    Pane world;

    @FXML
    MenuItem helpMenuItem;

    @FXML
    public void helpMenuItem(){
        System.out.println(graph);
        System.out.println("Lista wierzchołków");
        System.out.println(graph.getVertexList());
        System.out.println("Wybrane wierzcholki");
        System.out.println(graph.getSelectedVertexes());
    }

    @FXML
    MenuItem addVertexMenuItem;

//    fuckje przypisane do obiktów w menu Edit

    public void addVertex() {
        programState = State.ADDING_VERTEX;

    }


    @FXML
    MenuItem connetVertexMenuItem;

    @FXML
    public void connectVertexMenuFunction(){
        programState = State.CONNECTING_VERTEX;
        graph.unSelectObjects();
        graph.resetConnectingVertexData();
    }


    @FXML
    MenuItem deleteMenuItem;

//    Handlerty  zarzeń myszy

    public void mouseClidedHandler(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        switch (programState) {
            case ADDING_VERTEX:
                addVertexHanderFunction(x, y);
                programState = State.WAINTING;
                break;
            case WAINTING:
                selectVertexHandlerFuncion(x, y);
                break;
            case CONNECTING_VERTEX:

                connectVertexHandlerFuction(x, y);
                break;

        }

    }


    public void mouseDraggeed() {

    }


//    funckcje obsługjące zdarzenia myszy

    private void connectVertexHandlerFuction(double x, double y) {
        graph.loopThroughVertexListAndFindTheOneClickedOn(x,y);
        if (programState != State.CONNECTING_VERTEX) {
            graph.unSelectObjects();
        }

        if (!graph.isHeadSet()) {
            graph.setHead(graph.getSelectedVertexForConneting(null));
            if (graph.getHead() != null){
                graph.setHeadSet(true);
            }
        } else {
            if (!graph.isTailSet()) {
                graph.setTail(graph.getSelectedVertexForConneting(graph.getHead()));
                if (graph.getTail() != null) {
                    graph.setTailSet(true);
                }
            }
        }

        if (graph.isTailSet() && graph.isHeadSet()){
            graph.connectTwoVertex(graph.getHead(),graph.getTail());
            graph.unSelectObjects();
        }

        if (graph.isConnectingComplete()){
            programState = State.WAINTING;
            graph.resetConnectingVertexData();
        }



    }

    private void selectVertexHandlerFuncion(double x, double y) {
        graph.loopThroughVertexListAndFindTheOneClickedOn(x, y);
    }

    private void addVertexHanderFunction(double x, double y) {
        graph.addVertex(x, y);
    }


//    Handler zdarzeń klawiatury

    @FXML
    public void keyboardEventHandler(KeyEvent event) {
        KeyCode code = event.getCode();

        switch (programState) {
            case WAINTING:
                switch (code) {
                    case ESCAPE:
                        graph.unSelectObjects();
                        break;
                }
                break;
            case ADDING_VERTEX:
                switch (code) {
                    case ESCAPE:
                        programState = State.WAINTING;
                        break;
                }
            case CONNECTING_VERTEX:
                ///
                break;
        }
    }

    @FXML
    private void deleteMenuITemFunction() {
        graph.deleteSelectedObject();
    }

    @FXML
    private void uselectObjectMenuItemFunction() {
        graph.unSelectObjects();
    }




}

class InputTread implements Runnable {
    ApplicationController controller;

    public InputTread(ApplicationController controller) {
        this.controller = controller;

    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (ApplicationClass.newCode) {
                    ApplicationClass.newCode = false;
                    controller.keyboardEventHandler(ApplicationClass.keyEvent);
                }
            }


        }
    }
}
