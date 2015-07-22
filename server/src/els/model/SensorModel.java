package els.model;

import els.controller.database.EELDatabaseController;
import java.util.Hashtable;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Filip on 2015-07-20.
 */
public class SensorModel {
    private JSONObject sensorsObject;
    //private List<Sensor> sensors = new ArrayList<>();
    private Map<Integer, Sensor> sensorMap = new Hashtable<>();

    private static SensorModel ourInstance = new SensorModel();

    public static SensorModel the() {
        return ourInstance;
    }

    private SensorModel() {
        sensorsObject = requestSensors();
        parseSensors(sensorsObject);
        //injectSensorValueMap();
    }

    private void parseSensors(JSONObject json) {
        //todo JSONArray passivSensors = json.getJSONArray("not_controllable");
        JSONArray activeSensors = json.getJSONArray("controllable");

        for(int i = 0; i < activeSensors.length();i++){
            JSONObject s = activeSensors.getJSONObject(i);

            Sensor sensorObj = new Sensor();

            sensorObj.setSensorID(s.getInt("sensor_id"));
            sensorObj.setSymbol(s.getString("symbol"));
            sensorObj.setSensorName(s.getString("name"));
            sensorObj.setComment(s.getString("data_comment"));
            sensorObj.setIsFloat(s.getInt("is_float") != 0);
            sensorObj.setContinuous(s.getInt("continuous") != 0);
            sensorObj.setvHigh(s.getInt("vhigh"));
            sensorObj.setvLow(s.getInt("vlow"));
            sensorObj.setIcon(s.getString("icon"));
            sensorObj.setDataID(s.getInt("data_id"));
            sensorObj.setControllable();

            sensorObj.generatePane();

            sensorMap.put(sensorObj.getSensorID(), sensorObj);
        }

        //todo JSONArray passivSensors = json.getJSONArray("not_controllable");
        JSONArray passiveSensors = json.getJSONArray("not_controllable");

        for(int i = 0; i < passiveSensors.length();i++){
            JSONObject s = passiveSensors.getJSONObject(i);

            Sensor sensorObj = new Sensor();

            sensorObj.setSensorID(s.getInt("sensor_id"));
            sensorObj.setSymbol(s.getString("symbol"));
            sensorObj.setSensorName(s.getString("name"));
            sensorObj.setComment(s.getString("data_comment"));
            sensorObj.setIsFloat(s.getInt("is_float") != 0);
            sensorObj.setContinuous(s.getInt("continuous") != 0);
            sensorObj.setvHigh(s.getInt("vhigh"));
            sensorObj.setvLow(s.getInt("vlow"));
            sensorObj.setIcon(s.getString("icon"));
            sensorObj.setDataID(s.getInt("data_id"));

            sensorObj.generatePane();

            sensorMap.put(sensorObj.getSensorID(), sensorObj);
        }

    }

    public Map<Integer, Sensor> getSensors(){
        return sensorMap;
    }

    private JSONObject requestSensors() {
        return EELDatabaseController.the().getAllSensors();
    }

    public void setSensorValue(int id, float newValue){
        sensorMap.get(id).setValue(newValue);
    }
}
