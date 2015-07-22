package els.view;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Filip on 2015-07-21.
 */
public class FontAwesomeMapping {
    private Map<String, String> fontMap = new HashMap<>();

    private static FontAwesomeMapping instance = new FontAwesomeMapping();

    public static FontAwesomeMapping the() {
        return instance;
    }

    public FontAwesomeMapping() {
        fontMap.put("vr-questionmark", "\uf000");
        fontMap.put("vr-chair", "\uf001");
        fontMap.put("vr-door", "\uf002");
        fontMap.put("vr-fan", "\uf003");
        fontMap.put("vr-hum", "\uf004");
        fontMap.put("vr-temp", "\uf005");
        fontMap.put("vr-blinds", "\uf006");
        fontMap.put("vr-camera", "\uf007");
        fontMap.put("vr-window", "\uf008");
        fontMap.put("vr-drop", "\uf009");
        fontMap.put("vr-questionmark-circle", "\uf00a");
        fontMap.put("vr-flame", "\uf00b");
        fontMap.put("vr-bolt", "\uf00c");
        fontMap.put("vr-lightbulb", "\uf00d");
        fontMap.put("vr-pc", "\uf00e");
        fontMap.put("vr-day-night", "\uf00f");

    }

    public String convert2FontAwesome(String icon) {
        return fontMap.get(icon);
    }
}
