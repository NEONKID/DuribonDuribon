package duribon.dlug.org.duribonduribon.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import duribon.dlug.org.duribonduribon.Fragments.MapFragment;
import duribon.dlug.org.duribonduribon.R;
import pl.polidea.view.ZoomView;
import android.view.LayoutInflater;
import android.content.Context;
import android.widget.LinearLayout;
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
        setContentView(R.layout.activity_interiormap);

        String lecture;
        Intent intent = getIntent();
        lecture = intent.getExtras().getString("data");

        ImageView imageView = (ImageView)findViewById(R.id.destination);

        if(lecture.charAt(0) ==2) {
            imageView.setImageResource(R.drawable.testsecond);
        }
        else { //1층인 경우
            //버튼을 없애야함.
        }

        MapFragment.room_flag = false;  // 원상 복귀,,

        View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_interiormap, null, false);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ZoomView zoomView = new ZoomView(this);
        zoomView.addView(v);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setMaxZoom(4f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다

        CoordinatorLayout container = (CoordinatorLayout) findViewById(R.id.dest_content);
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
