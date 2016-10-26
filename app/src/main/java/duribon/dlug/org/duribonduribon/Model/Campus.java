package duribon.dlug.org.duribonduribon.Model;

import io.realm.RealmObject;

/**
 * Created by neonkid on 10/26/16.
 */

public class Campus extends RealmObject {
    private String building;
    private String room;

    public Campus() {

    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
