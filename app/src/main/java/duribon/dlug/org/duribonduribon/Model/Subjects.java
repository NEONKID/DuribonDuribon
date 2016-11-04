package duribon.dlug.org.duribonduribon.Model;

/**
 * Created by neonkid on 11/1/16.
 */

public class Subjects {
    private int _id;
    private String _subjectname;
    private String _timestamp;
    private String _classroom;

    // 기본 생성자,,
    public Subjects() {

    }

    // 2번째 생성자,,
    public Subjects(int id, String subjectname, String timestamp, String classroom) {
        this._id = id;
        this._subjectname = subjectname;
        this._timestamp = timestamp;
        this._classroom = classroom;
    }

    public Subjects(String subjectname, String timestamp, String classroom) {
        this._subjectname = subjectname;
        this._timestamp = timestamp;
        this._classroom = classroom;
    }

    // ID 입력해도 되고 안해도 됨,,
    public void setID(int id) {
        this._id = id;
    }

    // ID를 가져옴,,
    public int getID() {
        return this._id;
    }

    // 과목 이름을 정함,,
    public void setSubjectName(String subjectName) {
        this._subjectname = subjectName;
    }

    // 과목 이름을 가져옴,,
    public String getSubjectName() {
        return this._subjectname;
    }

    // 강의실을 정해줌,,
    public void setClassroom(String classroom) {
        this._classroom = classroom;
    }

    // 강의실을 가져옴,,
    public String getClassroom() {
        return this._classroom;
    }
}
