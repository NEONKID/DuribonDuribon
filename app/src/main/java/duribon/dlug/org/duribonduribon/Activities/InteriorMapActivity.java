package duribon.dlug.org.duribonduribon.Activities;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import duribon.dlug.org.duribonduribon.Fragments.MapFragment;
import duribon.dlug.org.duribonduribon.R;
import pl.polidea.view.ZoomView;
import android.view.LayoutInflater;
import android.content.Context;
import android.widget.RelativeLayout;

/**
 * Created by neonkid on 11/5/16.
 *
 * 내부 지도를 구현한 Activity,,
 */
public class InteriorMapActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lecture;
        Intent intent = getIntent();
        lecture = intent.getExtras().getString("data");
        setContentView(R.layout.activity_interiormap);


        switch (lecture.charAt(0)) {
            case '1': setZoom(R.layout.map_first);
                break;
            case '2': setZoom(R.layout.map_second);
                break;
            case '3': setZoom(R.layout.map_third);
                break;
            case '4':setZoom(R.layout.map_fourth);
                break;
            case '5':setZoom(R.layout.map_fifth);
                break;
        }

        MapFragment.room_flag = false;  // 원상 복귀,,

    }
    public void setZoom(int setLayout)
    {
        View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(setLayout, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ZoomView zoomView = new ZoomView(this);
        zoomView.addView(v);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setMaxZoom(4f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다

        CoordinatorLayout container = (CoordinatorLayout)findViewById(R.id.dest_content);
        container.addView(zoomView);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

    public void Previousbutton(View v) {
        finish();
    }

}
