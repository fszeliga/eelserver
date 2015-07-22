package els.model;

import els.main.Utils;
import els.view.FontAwesomeHandler;
import els.view.FontAwesomeMapping;
import java.util.Objects;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class Sensor extends BorderPane {
    private int sensorID;
    private String name;
    private String symbol;

    private boolean isFloat;
    private int dataID;
    private boolean continuous;
    private int vHigh;
    private int vLow;
    private String icon;
    private String comment;

    private StringProperty valueProperty = new SimpleStringProperty("initial");

    private float value = 0;
    private boolean controllable = false;

    public Sensor() {
        setPrefSize(120, 120);
        setPadding(new Insets(5, 5, 5, 5));
    }

    public int getSensorID() {
        return sensorID;
    }

    public void setSensorID(int sensorID) {
        this.sensorID = sensorID;
    }

    public String getSensorName() {
        return name;
    }

    public void setSensorName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean isFloat() {
        return isFloat;
    }

    public void setIsFloat(boolean isFloat) {
        this.isFloat = isFloat;
    }

    public int getDataID() {
        return dataID;
    }

    public void setDataID(int dataID) {
        this.dataID = dataID;
    }

    public boolean isContinuous() {
        return continuous;
    }

    public void setContinuous(boolean continuous) {
        this.continuous = continuous;
    }

    public int getvHigh() {
        return vHigh;
    }

    public void setvHigh(int vHigh) {
        this.vHigh = vHigh;
    }

    public int getvLow() {
        return vLow;
    }

    public void setvLow(int vLow) {
        this.vLow = vLow;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
        if (isFloat) this.valueProperty.setValue(Float.toString(value));
        else this.valueProperty.setValue(Integer.toString((int) value));
    }


    public void generatePane() {
        this.setTop(new Label(getSensorName()));
        this.setAlignment(this.getTop(), Pos.CENTER);

        Label icon = FontAwesomeHandler.createIconLabel(FontAwesomeMapping.the().convert2FontAwesome(getIcon()), 50);
        Utils.write(getIcon().replace("- ", ""));
        icon.setId("sensor_icon");
        this.setCenter(icon);
        this.setAlignment(this.getCenter(), Pos.CENTER);

        Label value = new Label();
        if (Objects.equals(symbol, "NA") | Objects.equals(symbol, "0")) value.textProperty().bind(valueProperty);

        else value.textProperty().bind(Bindings.concat(valueProperty).concat(" " + symbol));
        this.setBottom(value);
        this.setAlignment(this.getBottom(), Pos.CENTER);

        if (controllable) this.setOnMouseClicked(event -> turnOnOff());
    }

    private void turnOnOff() {
        if (value > 0) setValue(0);
        else setValue(100);
    }

    public void setControllable() {
        this.controllable = true;
    }
}
