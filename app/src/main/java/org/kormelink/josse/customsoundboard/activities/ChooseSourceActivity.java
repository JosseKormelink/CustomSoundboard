package org.kormelink.josse.customsoundboard.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.kormelink.josse.customsoundboard.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseSourceActivity extends AppCompatActivity {

    public static final String LOG_TAG = ChooseSourceActivity.class.getSimpleName();

    @BindView(R.id.from_local_storage_btn)
    Button fromLocalStorageBtn;

    @BindView(R.id.from_service_btn)
    Button fromServiceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_source);

        ButterKnife.bind(this);

        fromLocalStorageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fromServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseSourceActivity.this, ServiceActivity.class));
            }
        });

        Log.v(LOG_TAG, "Derp");

    }


}
