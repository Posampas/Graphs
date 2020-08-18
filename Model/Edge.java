package Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;

public class Edge {

    private Vertex head;
    private Vertex tail;
    private int edgeLabel;
    private Path edgeShape;
    private boolean isSelected;

    public Edge(Vertex head, Vertex tail, int edgeLabel) {
        this.head = head;
        this.tail = tail;
        this.edgeLabel = edgeLabel;
        isSelected = false;

        // Tworzenie scieżki od jednego wierzchołka do drugiego
        edgeShape = new Path();
        MoveTo moveTo = new MoveTo();
        moveTo.setX(head.getCircle().getCenterX());
        moveTo.setY(head.getCircle().getCenterY());

        ArcTo arcTo = new ArcTo();
        arcTo.setX(tail.getCircle().getCenterX());
        arcTo.setY(tail.getCircle().getCenterY());
        arcTo.setRadiusX(100);
        arcTo.setRadiusY(50);

        edgeShape.getElements().add(moveTo);
        edgeShape.getElements().add(arcTo);
    }

    public Path getEdgeShape() {
        return edgeShape;
    }

    public Vertex getHead() {
        return head;
    }

    public Vertex getTail() {
        return tail;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    private double[] calcualteCordinatesOfTheMiddlePoint(Vertex first, Vertex second) {

        double firstX = first.getCircle().getCenterX();
        double firstY = first.getCircle().getCenterY();
        double secondX = second.getCircle().getCenterX();
        double secondY = second.getCircle().getCenterY();
        double[] centerCordiates = new double[2];

        centerCordiates[0] = firstX + secondX / 2;
        centerCordiates[1] = firstY + secondY / 2;
        return centerCordiates;


    }

}
