package duribon.dlug.org.duribonduribon.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import duribon.dlug.org.duribonduribon.Model.DBHelper;
import duribon.dlug.org.duribonduribon.R;

/**
 * Created by neonkid on 10/31/16.
 */

public class TimetableFragment extends Fragment implements View.OnClickListener {
    private final String tag = "TimetableFragment.class";
    private String db_name = "duribon_timetable.db";
    private DBHelper helper;

    // SQLiteDatabase db;
    Cursor cursor;

    String time_line[] = {"1교시\n09:00","2교시\n10:00","3교시\n11:00","4교시\n12:00","5교시\n13:00",
            "6교시\n14:00","7교시\n15:00","8교시\n16:00","9교시\n17:00","10교시\n18:00"};
    String day_line[] = {"시간","월","화","수","목","금"};

    LinearLayout layout[] = new LinearLayout[time_line.length];
    LinearLayout lay_time;

    TextView time[] = new TextView[time_line.length];
    TextView day[] = new TextView[day_line.length];
    TextView data[] = new TextView[time_line.length * day_line.length];
    // TextView db_data[] = new TextView[time_line.length * day_line.length];

    EditText put_subject;
    EditText put_classroom;

    int db_id;
    String db_classroom, db_subject;

    public static TimetableFragment newInstance() {
        TimetableFragment fragment = new TimetableFragment();
        return fragment;
    }

    public TimetableFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
        View ttview = inflater.inflate(R.layout.fragment_timetable, container, false);
        ButterKnife.inject(this, ttview);

        String dbPath = getActivity().getApplicationContext().getDatabasePath(db_name).getPath();
        Log.i("Duribon db path= ", "" + dbPath);

        helper = new DBHelper(getActivity());
        int counter = helper.getCounter();
        Log.i(tag, "counter = " + counter);

        helper.search_data();

        LinearLayout.LayoutParams params_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        params_1.weight = 1;
        params_1.width = getLcdSizeWidth() / 6;
        params_1.height = getLcdSizeHeight() / 14;
        params_1.setMargins(1, 1, 1, 1);
        params_1.gravity = 1;

        LinearLayout.LayoutParams params_2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params_2.weight = 1;
        params_2.width = getLcdSizeWidth() / 6;
        params_2.height = getLcdSizeHeight() / 20;
        params_2.setMargins(1, 1, 1, 1);

        lay_time = (LinearLayout)ttview.findViewById(R.id.lay_time);
        layout[0] = (LinearLayout)ttview.findViewById(R.id.lay_0);
        layout[1] = (LinearLayout)ttview.findViewById(R.id.lay_1);
        layout[2] = (LinearLayout)ttview.findViewById(R.id.lay_2);
        layout[3] = (LinearLayout)ttview.findViewById(R.id.lay_3);
        layout[4] = (LinearLayout)ttview.findViewById(R.id.lay_4);
        layout[5] = (LinearLayout)ttview.findViewById(R.id.lay_5);
        layout[6] = (LinearLayout)ttview.findViewById(R.id.lay_6);
        layout[7] = (LinearLayout)ttview.findViewById(R.id.lay_7);
        layout[8] = (LinearLayout)ttview.findViewById(R.id.lay_8);
        layout[9] = (LinearLayout)ttview.findViewById(R.id.lay_9);

        //  요일
        for(int i = 0; i < day.length; i++) {
            day[i] = new TextView(getActivity());
            day[i].setText(day_line[i]);
            day[i].setGravity(Gravity.CENTER);
            day[i].setBackgroundColor(Color.parseColor("#FAF4C0"));
            day[i].setTextSize(10);
            lay_time.addView(day[i], params_2);
        }

        //  교시
        for(int i = 0; i < time.length; i++) {
            time[i] = new TextView(getActivity());
            time[i].setText(time_line[i]);
            time[i].setGravity(Gravity.CENTER);
            time[i].setBackgroundColor(Color.parseColor("#EAEAEA"));
            time[i].setTextSize(10);
            layout[i].addView(time[i], params_1);
        }

        cursor = helper.getAll();
        cursor.moveToFirst();

        for(int i = 0, id = 0; i < layout.length; i++) {
            for(int j = 1; j < day_line.length; j++) {
                data[id] = new TextView(getActivity());
                data[id].setId(id);
                data[id].setTextSize(10);
                data[id].setOnClickListener(this);
                data[id].setGravity(Gravity.CENTER);
                data[id].setBackgroundColor(Color.parseColor("#EAEAEA"));
                if((cursor != null) && (!cursor.isAfterLast())) {
                    db_id = cursor.getInt(0);
                    db_subject = cursor.getString(1);
                    db_classroom = cursor.getString(2);
                    if(data[id].getId() == db_id) {
                        data[id].setText(db_subject + "\n" + db_classroom);
                        cursor.moveToNext();
                    }
                } else if(cursor.isAfterLast()) {
                    cursor.close();
                }
                layout[i].addView(data[id], params_1);
                id++;
            }
        }

        setHasOptionsMenu(true);
        return ttview;
    }

    public int getLcdSizeWidth() {
        return ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
    }

    public int getLcdSizeHeight() {
        return ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
    }

    public void add_timetable_dialog(final int id) {
        final LinearLayout dig_layout = (LinearLayout)View.inflate(getActivity(), R.layout.activity_timetable_add, null);
        final AlertDialog.Builder add_dialog = new AlertDialog.Builder(getActivity());
        add_dialog.setTitle("Timetable");
        // add_dialog.setIcon()
        add_dialog.setView(dig_layout);
        add_dialog.setPositiveButton("저장", new DialogInterface.OnClickListener() {
            EditText put_subject = (EditText)dig_layout.findViewById(R.id.input_subject);
            EditText put_classroom = (EditText)dig_layout.findViewById(R.id.input_classroom);
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int get_id = data[id].getId();
                helper.add(get_id, put_subject.getText().toString(), TimetableFragment.this.put_classroom.getText().toString());
                data[id].setText("" + put_subject.getText() + "\n" + put_classroom);
            }
        });
        add_dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        add_dialog.show();
    }

    public void update_timetable_dialog(final int id) {
        final LinearLayout dig_layout = (LinearLayout)View.inflate(getActivity(), R.layout.activity_timetable_add, null);
        AlertDialog.Builder update_dialog = new AlertDialog.Builder(getActivity());
        update_dialog.setTitle("Timetable");
        // update_dialog.setIcon();
        update_dialog.setView(dig_layout);
        put_subject = (EditText)dig_layout.findViewById(R.id.input_subject);
        put_classroom = (EditText)dig_layout.findViewById(R.id.input_classroom);

        Cursor update_cursor = helper.getAll();
        if(update_cursor != null) {
            update_cursor.moveToFirst();
            while(!update_cursor.isAfterLast()) {
                if(update_cursor.getInt(0) == id) {
                    put_subject.setText(update_cursor.getString(1));
                    put_classroom.setText(update_cursor.getString(2));
                    break;
                }
                update_cursor.moveToNext();
            }
        }
        put_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                put_subject.setText(null);
            }
        });
        put_classroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                put_classroom.setText(null);
            }
        });
        update_dialog.setPositiveButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int get_id = data[id].getId();
                helper.update(get_id, put_subject.getText().toString(), put_classroom.getText().toString());
                data[id].setText("" + put_subject.getText() + "\n" + put_classroom.getText());
            }
        });
        update_dialog.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                helper.delete(id);
                data[id].setText(null);
            }
        });
        update_dialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        helper.close();
    }

    @Override
    public void onClick(View view) {
        Cursor event_cursor = null;
        event_cursor = helper.getAll();
        int get[] = new int[50];
        if(event_cursor != null) {
            Log.i(tag, "cursor is not null");
            event_cursor.moveToFirst();
            for(int i = 0; i < 50; i++) {
                get[i] = 0;
            }
            while(!event_cursor.isAfterLast()) {
                get[event_cursor.getInt(0)] = event_cursor.getInt(0);
                Log.i(tag, "get" + get[event_cursor.getInt(0)]);
                event_cursor.moveToNext();
            }
            for(int i = 0; i < 50; i++) {
                Log.i(tag, "get[i] = " + get[i] + "view.getId = " + view.getId() + "data[i].getId() =" + data[i].getId());
                if((get[i] != 0) && (get[i] == view.getId())) {
                    update_timetable_dialog(view.getId());
                    break;
                } else if((get[i] == 0) && (view.getId() == data[i].getId())) {
                    add_timetable_dialog(view.getId());
                    break;
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.timetable, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
