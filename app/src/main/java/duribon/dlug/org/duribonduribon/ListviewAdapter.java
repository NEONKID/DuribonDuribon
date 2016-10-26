package duribon.dlug.org.duribonduribon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import duribon.dlug.org.duribonduribon.Model.Campus;
import io.realm.internal.Context;

/**
 * Created by neonkid on 10/26/16.
 */

public class ListviewAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    private List<Campus> campusList = null;
    private ArrayList<Campus> arrayList = null;

    public ListviewAdapter(Context context, List<Campus> campusList) {
        this.context = context;
        this.campusList = campusList;
        // inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<Campus>();
        this.arrayList.addAll(campusList);
    }

    public class ViewHolder {
        TextView tv_name;
    }

    @Override
    public int getCount() {
        return campusList.size();
    }

    @Override
    public Campus getItem(int position) {
        return campusList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;

        return view;
    }
}
