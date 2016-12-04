package duribon.dlug.org.duribonduribon.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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
    CoordinatorLayout coordinatorLayout;    // 내부 지도 레이아웃..

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interiormap);
        ButterKnife.inject(this);

        MapFragment.room_flag = false;  // 원상 복귀,,

        View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_interiormap, null, false);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ZoomView zoomView = new ZoomView(this);
        zoomView.addView(v);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setMaxZoom(4f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다

        CoordinatorLayout container = (CoordinatorLayout) findViewById(R.id.dest_content);
        container.addView(zoomView);
        /*if()
        {
        //검색 값에 따라서 DestinationMap에서 보여주는 층수를 다르게 해야함
        }
        else //1층인 경우
        {
            //버튼을 없애야함.
        }
        */
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

    public void Previousbutton(View v) {
        finish();
    }

}
