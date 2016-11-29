package duribon.dlug.org.duribonduribon.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import duribon.dlug.org.duribonduribon.R;

/**
 * Created by neonkid on 11/14/16.
 *
 * 애플리케이션의 첫 화면 (잠깐 나타났다가 사라지는 화면)
 *
 */

public class IntroAppActivity extends AppCompatActivity {
    private final int MY_PERMISSION_CODE_ACCESS_FINE_LOCATION = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            loadApp();  // 앱을 실행하고 권한을 검사한다.
        } else {
            checkPermission();  // 권한을 검사한다.
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, getString(R.string.know_permission), Toast.LENGTH_SHORT).show();
            }
            // 권한이 없는 경우, 권한 설정을 유도한다.
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_CODE_ACCESS_FINE_LOCATION);
        } else {
            Log.i("[INFO]", "User Permission Allow");
            loadApp();  // 이미 권한이 주어진 경우, 앱을 실행한다.
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case MY_PERMISSION_CODE_ACCESS_FINE_LOCATION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, getString(R.string.allow_permission), Toast.LENGTH_SHORT).show();
                    loadApp();  // 사용자가 권한 허용 버튼을 클릭하였다.
                    break;
                } else {
                    Log.d("[INFO]", "User Permission Deny");
                    System.exit(0); // 권한 요구를 거부할 경우, 앱을 종료한다.
                }
                return;
        }
    }

    private void loadApp() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(IntroAppActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, 150);
    }
}
