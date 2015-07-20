package els.model;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Created by Filip on 2015-07-20.
 */
public class MainModel {
    private TextFlow infoTextFlow;

    private static MainModel mainModel = new MainModel();

    public static MainModel the(){return mainModel;}

    private MainModel(){
    }

    public void setInfoTextArea(TextFlow infoTextArea) {
        this.infoTextFlow = infoTextArea;
    }

    public void setInfoText(String infoText) {
        Text text = new Text(infoText);
        text.setWrappingWidth(infoTextFlow.getWidth()-5);
        this.infoTextFlow.getChildren().add(text);
    }
}
