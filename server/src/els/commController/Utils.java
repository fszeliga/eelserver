package els.commController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Utils {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m"; //used for errors
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m"; //used for info like finally etc
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[;36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static String byteArrayToBinaryString(byte[] toString) {
        String string = "";

        for (byte b : toString) {
            String s = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            string += s;
            string += " ";
        }
        return string;
    }

    public static String byteToBinaryString(byte toString) {
        String string = "";

        String s = String.format("%8s", Integer.toBinaryString(toString & 0xFF)).replace(' ', '0');
        string += s;

        return string;
    }

    public static int byteArrayToSignedInt(byte[] b) {
        int value = 0;
        for (int i = 0; i < b.length; i++) {
            if (i == 0) {
                value = (value << 8) + (b[i] & 0x7f);
            } else {
                value = (value << 8) + (b[i] & 0xff);
            }
        }

        if ((b[0] & (byte) 0x80) == (byte) 0x80) {
            value = -value;
        }

        return value;
    }

    public static int byteArrayToUnsignedInt(byte[] b) {
        int value = 0;
        for (int i = 0; i < b.length; i++) {
            value = (value << 8) + (b[i] & 0xff);
        }

        return value;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static byte[] intToByteArray(int eventid) {
        byte[] b = new byte[2];
        for (int i = 0; i < 2; i++) {
            int offset = (b.length - 1 - i) * 8;
            b[i] = (byte) ((eventid >>> offset) & 0xFF);
        }
        return b;
    }

    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    public static String byteToHexString(byte b) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02x", b & 0xff));
        return sb.toString();
    }

    public static int stringToByte(String string) {
        return Byte.parseByte(string);
    }

    public static int stringToInt(String string) {
        return Integer.parseInt(string);
    }

    public static void print(String color, String msg) {
        System.out.print(color + msg);
        System.out.println(ANSI_RESET);
        System.out.println("");
    }

    public static String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }

    public static void write(byte[] b, String c) {
        System.out.print(c + byteArrayToHexString(b));
        System.out.println(ANSI_RESET);
        System.out.println("");
    }

    public static void write(String s, String c) {
        System.out.print(c + s);
        System.out.println(ANSI_RESET);
        System.out.println("");
    }

    public static void write(byte[] b) {
        write(b, ANSI_WHITE);
        System.out.println(ANSI_RESET);
        System.out.println("");
    }

    public static void write(String s) {
        write(s, ANSI_WHITE);

    }
}
