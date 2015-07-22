package els;

import els.comm.servers.EELCommServer;
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
        launch(args);
    }

    static void disconnectAll() {
        //server.disconnectAll();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            BorderPane page = FXMLLoader.load(getClass().getResource("view/Main.fxml"));
            Scene scene = new Scene(page, 900, 600);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Energy Efficiency Lab");
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void stop() {
        EELCommServer.stopServer();
    }


}
