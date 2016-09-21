package hyapps.cmcc.com.keeyalivedemo;

/**
 * Created by cb on 2016/9/21.
 */
public class KeepLiveManager {
    private static KeepLiveManager instance = null;

    private KeepLiveManager(){
    }

    public static KeepLiveManager getInstance() {
        synchronized (KeepLiveManager.class) {
            if (instance == null) {
                instance = new KeepLiveManager();
            }
        }
        return instance;
    }


}
