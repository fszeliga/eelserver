package els.commView;


import els.main.Utils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by Filip on 2015-07-18.
 */
public class EELGUI extends Application {
    private static EELGUI ourInstance = new EELGUI();

    public static EELGUI the() {
        return ourInstance;
    }

    private EELGUI(){
        Utils.write("[EELGUI] Constructor called", Utils.ANSI_GREEN);
       String[] asd =  new String[0];
        launch(asd);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Utils.write("[EELGUI] start() called", Utils.ANSI_GREEN);
        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

}
