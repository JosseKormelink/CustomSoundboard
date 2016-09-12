package org.kormelink.josse.customsoundboard.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Josse on 12/09/2016.
 */

@DatabaseTable(tableName = "LocalSound")
public class LocalSound {

    @DatabaseField(canBeNull = false)
    private String title;
    @DatabaseField(canBeNull = false, unique = true, id = true)
    private String localFilepath;

    public LocalSound() {
    }

    public LocalSound(String title, String localFilepath) {
        this.title = title;
        this.localFilepath = localFilepath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocalFilepath() {
        return localFilepath;
    }

    public void setLocalFilepath(String localFilepath) {
        this.localFilepath = localFilepath;
    }
}
