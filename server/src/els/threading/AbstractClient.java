package els.threading;

/**
 * Created by Filip on 2015-07-13.
 */
public interface AbstractClient {

    void sendClientMessage(byte[] msg);
    void receiveClientMessage(byte[] msg);
    void errorClient(String s);



}
