package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

class MyCircle extends Circle{
    boolean mChecked;

    MyCircle(boolean checked){
        mChecked = checked;

        if(mChecked)
            setFill(Color.BEIGE);
        else
            setFill(Color.BISQUE);
    }

    public void switchState(){
        mChecked=!mChecked;

        if(mChecked)
            setFill(Color.BEIGE);
        else
            setFill(Color.BISQUE);
    }
}

class Square extends Rectangle{

    private int id;

    public interface squareInteraction{
        void leftCornerClicked(int id);
        void rightCornerClicked(int id);
    }

    @FunctionalInterface
    public interface squareLambda{
        void clicked(int i,int j);
    }

    squareInteraction mListener;
    squareLambda mListenerLambda;

    public void setSquareInteractionListener(squareInteraction listener){
        mListener = listener;
    }

    public void setLambdaListener(squareLambda listener){
        mListenerLambda = listener;
    }


    Square(double dimension, int id){

        this.id = id;

        setWidth(dimension);
        setHeight(dimension);

        this.setOnMouseClicked(event->{
            if(mListenerLambda != null)
                mListenerLambda.clicked(10,40);

            if (mListener == null)
                return;

            if(event.getX()<getWidth()/2) {
                    mListener.leftCornerClicked(this.id);
            } else {
                    mListener.rightCornerClicked(this.id);
            }

        });
    }
}

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {

        Group group = new Group();


        Square square = new Square(100,10);
        square.setFill(Color.BEIGE);

        MyCircle circle0 = new MyCircle(true);
        MyCircle circle1 = new MyCircle(false);

        square.setSquareInteractionListener(new Square.squareInteraction() {
            @Override
            public void leftCornerClicked(int id) {
                System.out.println("left corner clicked id:"+id);

                circle0.switchState();
            }

            @Override
            public void rightCornerClicked(int id) {
                System.out.println("right corner clicked id:"+id);

                circle1.switchState();
            }
        });

        square.setLambdaListener((int i,int j)->{
            System.out.print("["+i+" ,"+j+"]");
        });


        circle0.setRadius(50);
        circle1.setRadius(50);

        circle0.setCenterX(200);
        circle0.setCenterY(200);

        circle1.setCenterX(400);
        circle1.setCenterY(400);


        group.getChildren().add(square);
        group.getChildren().addAll(circle0,circle1);

        Scene scene = new Scene(group,640,480);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        System.out.println("jakis napis");

        launch(args);
    }
}
