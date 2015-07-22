package els.model;

import els.main.Utils;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Created by Filip on 2015-07-20.
 */
public class MainModel {
    private TextFlow infoTextFlow;

    private static MainModel mainModel = new MainModel();

    public static MainModel the() {
        return mainModel;
    }

    private MainModel() {
        Utils.write("[MainModel] created singleton", Utils.ANSI_GREEN);
    }

    public void setInfoTextArea(TextFlow infoTextArea) {
        this.infoTextFlow = infoTextArea;
    }

    public void setInfoText(String infoText) {
        Text text = new Text(infoText + "\n");
        text.setWrappingWidth(infoTextFlow.getWidth() - 5);
        this.infoTextFlow.getChildren().add(text);
    }
}
