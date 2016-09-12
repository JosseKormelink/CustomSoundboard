package org.kormelink.josse.customsoundboard.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.j256.ormlite.dao.Dao;

import org.kormelink.josse.customsoundboard.R;
import org.kormelink.josse.customsoundboard.database.DatabaseHelper;
import org.kormelink.josse.customsoundboard.entities.LocalSound;
import org.kormelink.josse.customsoundboard.entities.RemoteSound;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceActivity extends AppCompatActivity {

    public static final String LOG_TAG = ServiceActivity.class.getSimpleName();
    public static final String SOUNDS = "sounds";
    public static final String STORAGE_URL = "gs://customsoundboard-6a09d.appspot.com";
    private Context context;
    @BindView(R.id.service_progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        context = this;

        ButterKnife.bind(this);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();

        final FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        FirebaseRecyclerAdapter<RemoteSound, SoundItemViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<RemoteSound, SoundItemViewHolder>(
                        RemoteSound.class,
                        R.layout.sound_item,
                        SoundItemViewHolder.class,
                        databaseReference.child(SOUNDS)
                        )
                {
                    @Override
                    protected void populateViewHolder(SoundItemViewHolder viewHolder, final RemoteSound model, int position) {
                        Log.v(LOG_TAG, model.toString());

                        progressBar.setVisibility(View.INVISIBLE);

                        viewHolder.soundItemTitleTv.setText(model.getTitle());
                        viewHolder.getView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Get a reference to the file on firebase
                                StorageReference storageReference = firebaseStorage.getReferenceFromUrl(STORAGE_URL).child(SOUNDS).child(model.getStorageRef());
                                // Download the file on the system
                                String soundStorageRef = model.getStorageRef();
                                try {
                                    final File localFile = File.createTempFile(model.getTitle(), soundStorageRef.substring(soundStorageRef.lastIndexOf('.')));

                                    storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            // Local file has been created
                                            // Create a new LocalSound object, provided with its filepath
                                            try {
                                                Dao<LocalSound, String> dao = new DatabaseHelper(context).getLocalSoundDao();
                                                dao.create(new LocalSound(model.getTitle(), localFile.getPath()));
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }

                                            startActivity(new Intent(ServiceActivity.this, SoundboardActivity.class));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Handle any errors
                                        }
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                };

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_remote_sounds);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(firebaseRecyclerAdapter);

//        databaseReference.child("sounds");
//        RemoteSound remoteSound = new RemoteSound("alles wat ik heb", "456");
//        databaseReference.push().setValue(remoteSound);

        //Test voor het afspelen van een geluid van FireBase
//        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
//
//        StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://customsoundboard-6a09d.appspot.com").child("sounds");
//
//        final MediaPlayer mediaPlayer = new MediaPlayer();
//
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//
//        storageReference.child("albatrots.mp3").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                try {
//                    mediaPlayer.setDataSource(uri.toString());
//                    mediaPlayer.prepare();
//                    mediaPlayer.start();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    public static class SoundItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_sound_item_title)
        TextView soundItemTitleTv;
        private View view;

        public SoundItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }

        public View getView() {
            return view;
        }
    }
}
