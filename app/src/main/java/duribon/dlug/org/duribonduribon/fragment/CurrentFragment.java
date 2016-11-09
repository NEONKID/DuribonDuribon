package duribon.dlug.org.duribonduribon.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import duribon.dlug.org.duribonduribon.R;

/**
 * Created by neonkid on 11/7/16.
 */
/*
    임시로 기본 화면을 띄우기 위한 클래스,,
    절대 건드리지 말 것.
*/
public class CurrentFragment extends Fragment {
    @InjectView(R.id.textView)
    TextView textView;

    private static final String ARGS_KEY = "idx";

    public static CurrentFragment CurrentFragment(int position) {
        CurrentFragment fragment = new CurrentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS_KEY, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    public CurrentFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_fragment, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int idx = getArguments().getInt(ARGS_KEY, -1);
        textView.setText("Page Position" + idx);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}

