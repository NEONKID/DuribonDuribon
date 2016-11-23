package duribon.dlug.org.duribonduribon.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by neonkid on 10/28/16.
 */

public class DBHelper extends SQLiteOpenHelper {
    private final String tag = "DB_Helper";
    private final static String db_name = "duribon_timetable.db";
    private final String db_table_name = "schedule";
    static String result;
    SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, db_name, null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + db_table_name + "(" + "_id integer PRIMARY KEY,"
                + "subject text, " + "classroom text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS" + db_table_name;
        db.execSQL(sql);
        onCreate(db);
    }

    public void add(int id, String s_name, String c_name) {
        ContentValues values = new ContentValues();
        values.put("_id", id);
        values.put("subject", s_name);
        values.put("classroom", c_name);
        db.insert(db_table_name, null, values);
        search_data();
    }

    public void update(long rawId, String s_name, String c_name) {
        ContentValues values = new ContentValues();
        values.put("_id", rawId);
        values.put("subject", s_name);
        values.put("classroom", c_name);
        db.update(db_table_name, values, "_id = " + rawId, null);
        search_data();
    }

    public void delete(long rawId) {
        db.delete(db_table_name, "_id = " + rawId, null);
        search_data();
    }

    public void search_data() {
        String sql = "select * from " + db_table_name;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            String subject = cursor.getString(1);
            String classroom = cursor.getString(2);
            result = (subject + " " + classroom);
            Log.i(tag, result);
            cursor.moveToNext();
        }
        cursor.close();
    }

    public Cursor getAll() {
        return db.query(db_table_name, null, null, null, null, null, null);
    }

    public Cursor getId(int id) {
        Cursor cursor = db.query(db_table_name, null, "_id = " + id, null, null, null, null);
        if(cursor != null && cursor.getCount() != 0) {
            cursor.moveToNext();
        }
        return cursor;
    }

    public int getCounter() {
        Cursor cursor = null;
        String sql = "select * from " + db_table_name;
        cursor = db.rawQuery(sql, null);
        int counter = 0;
        while(!cursor.isAfterLast()) {
            cursor.moveToNext();
            counter++;
        }
        return counter;
    }
}
