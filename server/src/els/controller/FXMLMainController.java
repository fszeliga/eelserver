package els.controller;

import els.main.Utils;
import els.model.MainModel;
import els.model.Sensor;
import els.model.SensorModel;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;

/**
 * Created by Filip on 2015-07-20.
 */
public class FXMLMainController implements Initializable {
    private static FXMLMainController mainController = new FXMLMainController();

    public static FXMLMainController the() {
        return mainController;
    }

    @FXML
    Button databaseLogButton;
    @FXML
    ScrollPane sensorsScrollPane;
    @FXML
    TilePane sensorsFlowPane;
    @FXML
    TextFlow infoTextFlow;
    @FXML
    Label infoLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utils.write("[FXMLMainController] initialize() called");

        assert databaseLogButton != null : "fx:id=\"databaseLogButton\" was not injected";
        assert sensorsScrollPane != null : "scollpane empty";

        Font.loadFont(getClass().getResource("../view/vrp-font.ttf").toExternalForm(), 50);

        MainModel.the().setInfoTextArea(infoTextFlow);
        databaseLogButton.setOnAction(event -> {
            MainModel.the().setInfoText("test");
            SensorModel.the().setSensorValue(10, 10);
        });

        addSensors();

        sensorsFlowPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            int count = (int) sensorsFlowPane.getWidth() / 130;
            int gap = (int) (sensorsFlowPane.getWidth() - count * 120) / count;
            Utils.write(Integer.toString(count));
            Utils.write(Integer.toString(gap));
            //sensorsFlowPane.setVgap(gap);
            //sensorsFlowPane.setHgap(gap);
        });
        sensorsFlowPane.setPadding(new Insets(5, 5, 5, 5));
    }


    private void addSensors() {
        Map<Integer, Sensor> sensors = SensorModel.the().getSensors();

        for (Integer key : sensors.keySet()) {
            Sensor sensorHelp = sensors.get(key);
            sensorHelp.setId("sensor_pane");
            sensorsFlowPane.getChildren().add(sensorHelp);
        }

    }

}
