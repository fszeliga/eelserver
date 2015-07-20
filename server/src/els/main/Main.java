package els.main;

import els.database.EELDatabaseController;
import els.eelCommunication.servers.EELCommServer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    private static int tcpPort = 7755;
    private static int webSocketPort = 7855;
    private static String hostname = "localhost";
    private TextArea infoTextArea;
    //private static ELSServer server;

    public static void main(String[] args) {
        //new EELWebSocketServer(new InetSocketAddress(hostname, webSocketPort)).start();
        //new EELTCPSocketServer(new InetSocketAddress(hostname, tcpPort)).start();
        EELDatabaseController.the();
        launch(args);
    }

    static void disconnectAll() {
        //server.disconnectAll();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            BorderPane page = FXMLLoader.load(getClass().getResource("../view/Main.fxml"));
            Scene scene = new Scene(page, 900, 600);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Energy Efficiency Lab");
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
/*
        //BorderPane layout = new BorderPane();

        layout.setTop(addToolbar());
        layout.setMargin(layout.getTop(), new Insets(10, 10, 10, 10));

        layout.setCenter(addSensors());
        layout.setMargin(layout.getCenter(), new Insets(10, 10, 10, 10));

        //layout.setRight(addPassiveSensors());
        //layout.setMargin(layout.getRight(), new Insets(10, 10, 10, 10));

        this.infoTextArea = new TextArea();
        infoTextArea.setEditable(false);
        infoTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            infoTextArea.setScrollTop(Double.MAX_VALUE);
        });
        layout.setLeft(infoTextArea);
        layout.setMargin(layout.getLeft(), new Insets(10, 10, 10, 10));

        layout.setBottom(addInfoBar());
        layout.setMargin(layout.getBottom(), new Insets(0, 0, 0, 0));
        */

    }

    @Override
    public void stop() {
        EELCommServer.stopServer();
    }


}
