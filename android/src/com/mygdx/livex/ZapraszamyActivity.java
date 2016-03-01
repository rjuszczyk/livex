package com.mygdx.livex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mygdx.game.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Radek on 2016-02-27.
 */
public class ZapraszamyActivity extends AppCompatActivity {
    @OnClick(R.id.multilac)
    void dalej(View v) {
        startActivity(new Intent(this, FormActivity.class));
    }
    @OnClick(R.id.regulamin)
    void regulamin(View v) {
        startActivity(new Intent(this, RegulaminActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.zapraszamy_activity);

        ButterKnife.bind(this);
    }
}
