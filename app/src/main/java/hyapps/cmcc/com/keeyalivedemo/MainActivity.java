package hyapps.cmcc.com.keeyalivedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import hyapps.cmcc.com.keeyalivedemo.service.InvisibleForegroundService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        starInvisibleForegroundService();
    }

    private void starInvisibleForegroundService() {
        InvisibleForegroundService.startService(this);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
