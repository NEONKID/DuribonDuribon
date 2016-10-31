package duribon.dlug.org.duribonduribon.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by neonkid on 10/28/16.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블을 생성한다.
        db.execSQL("CREATE TABLE CAMPUS (_id INTEGER PRIMARY_KEY AUTOINCREMENT, building TEXT, room TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String building, String room) {
        // 읽고 쓰기가 가능하도록 DB 오픈,,
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO CAMPUS VALUES(null, '" + building + "', " + room + "');");
        db.close();
    }

    public void update(String item, int price) {

    }

    public void delete(String building) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM CAMPUS WHERE building = '" + building + "';");
        db.close();
    }

    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 다음 코드 사용,,
        Cursor cursor = db.rawQuery("SELECT * FROM CAMPUS", null);
        while(cursor.moveToNext()) {
            result = cursor.getString(1);
        }
        return result;
    }
}
