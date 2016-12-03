package duribon.dlug.org.duribonduribon.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.ButterKnife;
import duribon.dlug.org.duribonduribon.Model.DBHelper;
import duribon.dlug.org.duribonduribon.R;

/**
 * Created by neonkid on 10/31/16.
 *
 * 시간표 Fragment
 */

public class TimetableFragment extends Fragment implements View.OnClickListener {
    private final String tag = "TimetableFragment.class";
    private String db_name = "duribon_timetable.db";
    private DBHelper helper;
    private Cursor cursor;
    private String time_line[] = new String[23];
    private String day_line[] = new String[6];
    private TextView data[] = new TextView[time_line.length * day_line.length];
    private EditText put_subject, put_classroom;
    private String db_classroom, db_subject, db_campus;
    private int db_id;
    private CustomSearchPOI customSearchPOI;

    public static TimetableFragment newInstance() {
        TimetableFragment fragment = new TimetableFragment();
        return fragment;
    }

    public TimetableFragment() {}

    public interface CustomSearchPOI {
        void requestSearch(String query);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            customSearchPOI = (CustomSearchPOI)context;
        } catch (ClassCastException ex) {
            throw new ClassCastException(context.toString() + "must implement CustomSearchPOI");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
        View ttview = inflater.inflate(R.layout.fragment_timetable, container, false);
        ButterKnife.inject(this, ttview);

        final int Lid = R.id.lay_A;
        final LinearLayout[] layouts = new LinearLayout[time_line.length];
        final LinearLayout lay_time;
        final TextView time[] = new TextView[time_line.length];
        final TextView day[] = new TextView[day_line.length];

        Display display = ((WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        String dbPath = getActivity().getApplicationContext().getDatabasePath(db_name).getPath();
        Log.i("Duribon db path= ", "" + dbPath);

        helper = new DBHelper(getActivity());
        int counter = helper.getCounter();
        Log.i(tag, "counter = " + counter);

        helper.search_data();

        time_line= getResources().getStringArray(R.array.table_times);
        day_line = getResources().getStringArray(R.array.table_days);

        LinearLayout.LayoutParams params_1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        params_1.weight = 1;    // 레이아웃의 weight를 동적으로 설정 (칸의 비율)
        params_1.width = point.x / 6;
        params_1.height = point.y / 14;
        params_1.setMargins(1, 1, 1, 1);
        params_1.gravity=1; // 표가 뒤틀리는 것을 방지

        LinearLayout.LayoutParams params_2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        params_2.weight = 1;    // 레이아웃의 weight를 동적으로 설정 (칸의 비율)
        params_2.width = point.x / 6;
        params_2.height = point.y / 20;
        params_2.setMargins(1, 1, 1, 1);

        lay_time = (LinearLayout)ttview.findViewById(R.id.lay_time);
        for(int i = 0; i < layouts.length; i++) {
            layouts[i] = (LinearLayout)ttview.findViewById(Lid + i);
        }

        //  요일
        for(int i = 0; i < day.length; i++) {
            day[i] = new TextView(getActivity());
            day[i].setText(day_line[i]);
            day[i].setGravity(Gravity.CENTER);
            day[i].setBackgroundColor(Color.parseColor("#FAF4C0")); // 요일 색상
            day[i].setTextSize(10);
            lay_time.addView(day[i], params_2);
        }

        //  교시
        for(int i = 0; i < time.length; i++) {
            time[i] = new TextView(getActivity());
            time[i].setText(time_line[i]);
            time[i].setGravity(Gravity.CENTER);
            time[i].setBackgroundColor(Color.parseColor("#EAEAEA"));    // 각 교시 색상
            time[i].setTextSize(10);
            layouts[i].addView(time[i], params_1);
        }

        cursor = helper.getAll();
        cursor.moveToFirst();

        for(int i = 0, id = 0; i < layouts.length; i++) {
            for(int j = 1; j < day_line.length; j++) {
                data[id] = new TextView(getActivity());
                data[id].setId(id);
                data[id].setTextSize(10);
                data[id].setOnClickListener(this);
                data[id].setGravity(Gravity.CENTER);
                data[id].setBackgroundColor(Color.parseColor("#EAEAEA"));   // 수업 색상
                if((cursor != null) && (!cursor.isAfterLast())) {
                    db_id = cursor.getInt(0);
                    db_subject = cursor.getString(1);
                    db_campus = cursor.getString(2);
                    db_classroom = cursor.getString(3);
                    if(data[id].getId() == db_id) {
                        data[id].setText(db_subject + "\n" + db_campus + "\n" + db_classroom);
                        cursor.moveToNext();
                    }
                } else if(cursor.isAfterLast()) {
                    cursor.close();
                }
                layouts[i].addView(data[id], params_1);
                id++;
            }
        }

        setHasOptionsMenu(true);
        return ttview;
    }

    private void add_timetable_dialog(final int id) {
        final LinearLayout dig_layout = (LinearLayout)View.inflate(getActivity(), R.layout.activity_timetable_add, null);
        AlertDialog.Builder add_dialog = new AlertDialog.Builder(getActivity());
        final Spinner spinner = (Spinner)dig_layout.findViewById(R.id.input_college);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        add_dialog.setTitle(getString(R.string.tt_add));
        // add_dialog.setIcon()
        add_dialog.setView(dig_layout);
        add_dialog.setPositiveButton(getString(R.string.Press_save), new DialogInterface.OnClickListener() {
            EditText put_subject = (EditText)dig_layout.findViewById(R.id.input_subject);
            EditText put_classroom = (EditText)dig_layout.findViewById(R.id.input_classroom);
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int get_id = data[id].getId();
                helper.add(get_id, put_subject.getText().toString(), spinner.getSelectedItem().toString(), put_classroom.getText().toString());
                data[id].setText("" + put_subject.getText() + "\n" + spinner.getSelectedItem().toString() + "\n" + put_classroom.getText());
            }
        });
        add_dialog.setNegativeButton(getString(R.string.Press_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        add_dialog.show();
    }

    private void info_timetable_dialog(final int id) {
        final LinearLayout dig_layout = (LinearLayout)View.inflate(getActivity(), R.layout.activity_timetable_info, null);
        final TextView out_subject = (TextView)dig_layout.findViewById(R.id.info_subject);
        final TextView out_college = (TextView)dig_layout.findViewById(R.id.info_campus);
        final TextView out_classroom = (TextView)dig_layout.findViewById(R.id.info_classroom);

        AlertDialog.Builder choice_dialog = new AlertDialog.Builder(getActivity());
        choice_dialog.setTitle(getString(R.string.tt_info));
        choice_dialog.setView(dig_layout);
        final Cursor info_cursor = helper.getAll();

        if(info_cursor != null) {
            info_cursor.moveToFirst();
            while(!info_cursor.isAfterLast()) {
                if(info_cursor.getInt(0) == id) {
                    out_subject.setText(info_cursor.getString(1));
                    out_college.setText(info_cursor.getString(2));
                    out_classroom.setText(info_cursor.getString(3));
                    break;
                }
                info_cursor.moveToNext();
            }
        }
        choice_dialog.setPositiveButton(getString(R.string.Location_navigate), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MapFragment.room_flag = true;
                customSearchPOI.requestSearch(out_college.getText().toString());
            }
        });
        choice_dialog.setNegativeButton(getString(R.string.tt_modify), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                update_timetable_dialog(id);
            }
        });
        choice_dialog.show();
    }

    private void update_timetable_dialog(final int id) {
        final LinearLayout dig_layout = (LinearLayout)View.inflate(getActivity(), R.layout.activity_timetable_add, null);
        final Spinner spinner = (Spinner)dig_layout.findViewById(R.id.input_college);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        AlertDialog.Builder update_dialog = new AlertDialog.Builder(getActivity());
        update_dialog.setTitle(getString(R.string.tt_modelete));
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
                    // put_college.setText(update_cursor.getString(2));
                    put_classroom.setText(update_cursor.getString(3));
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
        update_dialog.setPositiveButton(getString(R.string.Press_modify), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int get_id = data[id].getId();
                helper.update(get_id, put_subject.getText().toString(), spinner.getSelectedItem().toString(), put_classroom.getText().toString());
                data[id].setText("" + put_subject.getText() + "\n" + spinner.getSelectedItem().toString() + "\n" + put_classroom.getText());
            }
        });
        update_dialog.setNegativeButton(getString(R.string.Press_delete), new DialogInterface.OnClickListener() {
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
    public void onClick(final View view) {
        Cursor event_cursor = null;
        event_cursor = helper.getAll();
        int get[] = new int[time_line.length * day_line.length];
        if(event_cursor != null) {
            Log.i(tag, "cursor is not null");
            event_cursor.moveToFirst();
            for(int i = 0; i < time_line.length * day_line.length; i++) {
                get[i] = 0;
            }
            while(!event_cursor.isAfterLast()) {
                get[event_cursor.getInt(0)] = event_cursor.getInt(0);
                Log.i(tag, "get" + get[event_cursor.getInt(0)]);
                event_cursor.moveToNext();
            }
            for(int i = 0; i < time_line.length * day_line.length; i++) {
                Log.i(tag, "get[i] = " + get[i] + "view.getId = " + view.getId() + "data[i].getId() =" + data[i].getId());
                if((get[i] != 0) && (get[i] == view.getId())) {
                    info_timetable_dialog(view.getId());
                    break;
                } else if((get[i] == 0) && (view.getId() == data[i].getId())) {
                    add_timetable_dialog(view.getId());
                    break;
                }
            }
        }
    }
}
