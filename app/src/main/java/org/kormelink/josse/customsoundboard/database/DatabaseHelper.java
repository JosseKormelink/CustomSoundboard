package org.kormelink.josse.customsoundboard.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.kormelink.josse.customsoundboard.R;
import org.kormelink.josse.customsoundboard.entities.LocalSound;

import java.sql.SQLException;

/**
 * Created by Josse on 12/09/2016.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "customSoundBoard.db";
    private static final int DATABASE_VERSION = 1;
    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();

    private Dao<LocalSound, String> localSoundDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, LocalSound.class);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Unable to create database", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, LocalSound.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Unable to upgrade database from version " + oldVersion + " to new "
                    + newVersion, e);
        }
    }

    public Dao<LocalSound, String> getLocalSoundDao() throws SQLException {
        if (localSoundDao == null) {
            localSoundDao = getDao(LocalSound.class);
        }
        return localSoundDao;
    }
}
