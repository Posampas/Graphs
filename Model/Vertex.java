package Model;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Vertex {
    private Circle circle; // graficzna reprezecatja wierzchołka
    private Graph graph; // Do jakiego grafu należy ten wierzchołek
    private boolean isSelected;
    private int label;  // Oznaczenie jakie bedzie miał wierzchołek w danym grafie
    private Label labelText;
    private Pane pane;


    public int getLabel() {
        return label;
    }

    void setLabel(int label) {
        this.label = label;
    }

    public Vertex(Graph graph, int label, double x, double y, Pane pane) {
        circle = new Circle(x, y, 15, Color.TRANSPARENT);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(5.0);
        this.graph = graph;
        labelText = new Label("" + label);
        this.pane = pane;
        this.label = label;

    }

    Circle getCircle() {
        return circle;
    }


    boolean isClickedOn(double x, double y) {
        double centerX = circle.getCenterX();
        double centerY = circle.getCenterY();

        return circle.getRadius() > Math.sqrt(Math.pow(centerX - x, 2) + Math.pow(centerY - y, 2));
    }

    void setSelected(boolean selected) {
        isSelected = selected;
        if (isSelected){
            setColor(Color.MAGENTA);
        } else {
            setColor(Color.BLACK);
        }
    }

    void setColor(Color color){
        circle.setStroke(color);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void deleteFromWorld(){
        pane.getChildren().remove(labelText);
        pane.getChildren().remove(circle);

    }

    public void addToWorld(){
        pane.getChildren().add(circle);
        pane.getChildren().add(labelText);
        labelText.setTranslateX(circle.getCenterX()-5);
        labelText.setTranslateY(circle.getCenterY()-5);

    }

    public void setLabelText() {
        labelText.setText("" + label);
    }


    @Override
    public String toString() {
        return "Vertex{" +
                "label= " + label +
                '}';
    }
}
