package org.kormelink.josse.customsoundboard.entities;

/**
 * Created by Josse on 26/08/2016.
 */
public class RemoteSound {
    private String title;
    private String storageRef;

    public RemoteSound() {
    }

    public RemoteSound(String title, String storageRef) {
        this.title = title;
        this.storageRef = storageRef;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStorageRef() {
        return storageRef;
    }

    public void setStorageRef(String storageRef) {
        this.storageRef = storageRef;
    }

    @Override
    public String toString() {
        return "RemoteSound{" +
                "title='" + title + '\'' +
                ", storageRef='" + storageRef + '\'' +
                '}';
    }
}
