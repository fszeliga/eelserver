package els.controller;

import els.main.Utils;
import els.model.MainModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.TextFlow;

/**
 * Created by Filip on 2015-07-20.
 */
public class FXMLMainController implements Initializable {
    private static FXMLMainController mainController = new FXMLMainController();

    public static FXMLMainController the(){return mainController;}

    @FXML
    Button databaseLogButton;
    @FXML
    ScrollPane sensorsScrollPane;
    @FXML
    FlowPane sensorsFlowPane;
    @FXML
    TextFlow infoTextArea;
    @FXML
    Label infoLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utils.write("[FXMLMainController] initialize() called");

        assert databaseLogButton != null : "fx:id=\"databaseLogButton\" was not injected";
        assert sensorsScrollPane !=null : "scollpane empty";

        MainModel.the().setInfoTextArea(infoTextArea);
        databaseLogButton.setOnAction(event -> MainModel.the().setInfoText("test"));

        addSensors(sensorsFlowPane);
    }


    private void addSensors(FlowPane pane) {
       // ScrollPane scrollPane = new ScrollPane();
        //scrollPane.setId("sensors_scrollpane");
        //scrollPane.setFitToWidth(true);
        //FlowPane container = new FlowPane(Orientation.HORIZONTAL, 10, 10);
        //container.setId("sensors_flowpane");
        //container.setPadding(new Insets(10, 10, 10, 10));

//        scrollPane.viewportBoundsProperty().addListener((bounds, oldBounds, newBounds) -> {
//            container.setPrefWidth(newBounds.getWidth());
//            if (newBounds.getHeight() > container.getPrefHeight()) {
//                container.setPrefHeight(newBounds.getHeight());
//            }
//        });

        //scrollPane.setContent(container);

        for (int i = 0; i < 15; i++) {
            BorderPane sensor = new BorderPane();
            sensor.setId("sensor_tile");
            double size = 100;
            sensor.setMinHeight(size);
            sensor.setMinWidth(size);
            sensor.setPrefSize(size, size);

            Label sensor_name = new Label("Active Sensor " + i);
            sensor_name.setId("sensor_name");

            sensor.setAlignment(sensor_name, Pos.CENTER);
            sensor.setTop(sensor_name);
            pane.getChildren().add(sensor);
        }
        for (int i = 0; i < 15; i++) {
            BorderPane sensor = new BorderPane();
            sensor.setId("sensor_tile");
            double size = 100;
            sensor.setMinHeight(size);
            sensor.setMinWidth(size);
            sensor.setPrefSize(size, size);

            Label sensor_name = new Label("Passive Sensor " + i);
            sensor_name.setId("sensor_name");

            sensor.setAlignment(sensor_name, Pos.CENTER);
            sensor.setTop(sensor_name);
            pane.getChildren().add(sensor);
        }
    }

}
