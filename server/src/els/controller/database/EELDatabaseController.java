package els.controller.database;

import els.main.Utils;
import els.model.Sensor;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

/**
 * Created by Filip on 2015-07-20.
 */
public class EELDatabaseController {
    JSONObject sensors;
    String url = "http://imi-elab1.imi.kit.edu/get_all_sensors.php";
    boolean successImport = false;

    private static EELDatabaseController ourInstance = new EELDatabaseController();
    private List<Sensor> allSensors;

    public static EELDatabaseController the() {
        return ourInstance;
    }

    private EELDatabaseController() {
        String jsonString = null;
        try {
            jsonString = IOUtils.toString(new URL(url));
        } catch (IOException e) {
            e.printStackTrace();
        }

        sensors = new JSONObject(jsonString);

        if (sensors.get("success").toString().equals("1")) {
            Utils.write("[ELSDatabaseController] successfully got sensors from server", Utils.ANSI_GREEN);
            successImport = true;
        } else {
            Utils.write("[ELSDatabaseController] failed to retreive sensors from sensors", Utils.ANSI_RED);//todo EELException
        }
    }


    public JSONObject getAllSensors() {
        return sensors;
    }
}
