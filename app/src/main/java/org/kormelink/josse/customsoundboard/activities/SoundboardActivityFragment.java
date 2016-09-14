package org.kormelink.josse.customsoundboard.activities;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.j256.ormlite.dao.Dao;

import org.kormelink.josse.customsoundboard.R;
import org.kormelink.josse.customsoundboard.adapters.SoundBoardArrayAdapter;
import org.kormelink.josse.customsoundboard.database.DatabaseHelper;
import org.kormelink.josse.customsoundboard.entities.LocalSound;

import java.io.IOException;
import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class SoundboardActivityFragment extends Fragment {

    private Context context;
    private MediaPlayer mediaPlayer;
    @BindView(R.id.soundboard_gv)
    GridView gridView;

    public SoundboardActivityFragment() {
        //empty fragment constructor needed
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_soundboard, container, false);

        ButterKnife.bind(this, view);

        mediaPlayer = new MediaPlayer();

        try {
            Dao<LocalSound, String> dao = new DatabaseHelper(context).getLocalSoundDao();
            gridView.setAdapter(new SoundBoardArrayAdapter(context, dao.queryForAll()));
        } catch (SQLException e) {
            e.printStackTrace();

        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LocalSound localSound = (LocalSound) gridView.getAdapter().getItem(i);

                try {
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }

                    mediaPlayer.setDataSource(localSound.getLocalFilepath());
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                        }
                    });
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }
}
