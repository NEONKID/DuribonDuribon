package duribon.dlug.org.duribonduribon.Model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by neonkid on 10/28/16.
 */

/*
    SQLiteOpenHelper를 상속,,
    이 클래스는 추상 클래스로, DB 생성, 개방, 업그레이드를
    위한 베스트 프랙티스 패턴 구현을 위해 사용,,

    RawQuery: SQL문을 그대로 받음,,
    SQLite에서 지원하는 질의문에는 상당한 제약이 있음,,

    복수개의 테이블에서 작업을 할 때는 RqwQuery,,
    한 개의 테이블에서 작업할 때는 일반 Query를 사용,,

    QueryBuilder의 사용,, : StringBuilder와 비슷,,
 */
public class DBHandler extends SQLiteOpenHelper {

    private ContentResolver duriCR;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DuribonDuribon.db";
    public static final String TABLE_NAME = "subjects";

    // Index 키 이름,,
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SUBJECTNAME = "subjectname";
    public static final String COLUMN_CLASSTIME = "classtime";
    public static final String COLUMN_CLASSROOM = "classroom";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SUBJECTS_TABLE = "CREATE TABLE" + TABLE_NAME + "(" + COLUMN_ID + "INTEGER PRIMARY KEY, "
                + COLUMN_SUBJECTNAME + "TEXT" + COLUMN_CLASSTIME + "TIMETABLE" + ")";
        db.execSQL(CREATE_SUBJECTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    public void addSubject(Subjects subject) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SUBJECTNAME, subject.getSubjectName());
        values.put(COLUMN_CLASSROOM, subject.getClassroom());

        SQLiteDatabase db;
        try {
            db = this.getWritableDatabase();
        } catch(SQLException ex) {
            db = this.getReadableDatabase();
        }

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Subjects findSubject(String subjectname) {
        String query = "SELECT * FROM" + TABLE_NAME + "WHERE" +
                COLUMN_SUBJECTNAME + " = \"" + subjectname + " \"";

        SQLiteDatabase db;
        try {
            db = this.getWritableDatabase();
        } catch(SQLException ex) {
            db = this.getReadableDatabase();
        }

        Cursor cursor = db.rawQuery(query, null);
        Subjects subject = new Subjects();

        if(cursor.moveToFirst()) {
            cursor.moveToFirst();
            subject.setID(Integer.parseInt(cursor.getString(0)));   // DB의 첫번째 칼럼,,
            subject.setSubjectName(cursor.getString(1));
            subject.setClassroom(cursor.getString(2));
            cursor.close();
        } else {
            subject = null;
        }
        db.close();
        return subject;
    }

    public boolean deleteSubject(String subjectname) {
        boolean result = false;

        String query = "SELECT * FROM" + TABLE_NAME + "WHERE" +
                COLUMN_SUBJECTNAME + "= \"" + subjectname + " \"";

        SQLiteDatabase db;
        try {
            db = this.getWritableDatabase();
        } catch(SQLException ex) {
            db = this.getReadableDatabase();
        }

        Cursor cursor = db.rawQuery(query, null);
        Subjects subject = new Subjects();

        if(cursor.moveToFirst()) {
            subject.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(subject.getID()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
}
