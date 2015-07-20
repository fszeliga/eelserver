package els.eelCommunication.servers;

/**
 * Created by Filip on 2015-07-19.
 */
public interface EELServerInterface {

    //will only be called if the application closes -> no need to update the EEL Main Server holding all different sockets
    void onServerStop();
}
