package exercises;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Opg1 extends Application {

    private String text;
    private Button button;
    private TextArea textArea;
    private VBox container;

    public synchronized void buildText(String c){
        text += c;
    }

    @Override
    public void start(Stage primaryStage) {
        text = "";
        button = new Button("Start threads");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                text="";
                //create pool
                ExecutorService executor = Executors.newCachedThreadPool();
                // Create tasks
                Runnable printA = new PrintChar('a', 100);
                Runnable printB = new PrintChar('b', 100);
                Runnable print100 = new PrintNum(100);
                // Create threads
                executor.execute(printA);
                executor.execute(printB);
                executor.execute(print100);
                //No new tasks can be accepted, but any existing tasks will continue to finish.
                executor.shutdown();
                while (!executor.isTerminated()) { }
                textArea.setText(text);
                System.out.println(text);
            }
        });
        textArea = new TextArea(text);
        textArea.wrapTextProperty().setValue(true);
        container = new VBox(button, textArea);
        Scene scene = new Scene(container);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class PrintChar implements Runnable{
        private char charToPrint;
        private int times;

        public PrintChar(char c, int t){
            charToPrint=c;
            times=t;
        }

        @Override
        public void run() {
            for (int i = 0; i < times; i++) {
                buildText(""+charToPrint);
            }
        }
    }

    private class PrintNum implements Runnable{
        private int lastNum;

        public PrintNum(int n){
            lastNum=n;
        }

        @Override
        public void run() {
            for (int i = 0; i <= lastNum; i++) {
                buildText(""+i);
            }
        }
    }
}