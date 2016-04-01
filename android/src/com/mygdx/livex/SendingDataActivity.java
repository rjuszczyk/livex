package com.mygdx.livex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mygdx.livex.R;
import com.mygdx.livex.model.Row;
import com.mygdx.livex.model.UserData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Radek on 2016-02-27.
 */
public class SendingDataActivity extends AppCompatActivity {
    UserData mUserData;

    @OnClick(R.id.try_again)
    void tryAgain(View v) {
        sendData(mUserData);
    }

    void onFail() {
        findViewById(R.id.error_view).setVisibility(View.VISIBLE);
        findViewById(R.id.loading_view).setVisibility(View.GONE);
    }

    void onSuccess() {
        Intent startIntent = new Intent(SendingDataActivity.this, PodiumActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startIntent);

        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dane_activity);

        mUserData = (UserData) getIntent().getSerializableExtra("user_data");

        ButterKnife.bind(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                sendData(mUserData);
            }
        }).start();
    }

    public void sendData(UserData userData) {
        findViewById(R.id.error_view).setVisibility(View.GONE);
        findViewById(R.id.loading_view).setVisibility(View.VISIBLE);
        Row row = userData.getRow();
        try {
            sendData(userData.getImie(), userData.getNazwisko(), userData.getTelefon(), userData.getEmail(), userData.getCheck1(), userData.getCheck2(), userData.getCheck3(),
                    row.nazwa_apteki, row.ulica, row.miasto, row.wojewodztwo, row.nazwisko_przedstawiciela, row.imie_przedstawiciela, row.rks_nazwisko, row.rks_imie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void sendData(
            String imie,
            String nazwisko,

            String telefon,
            String email,
            String odp1,
            String odp2,
            String odp3,
            String nazwa_apteki,
            String ulica,
            String miasto,
            String wojewodztwo,
            String nazwisko_przedstawiciela,
            String imie_przedstawiciela,
            String rks_nazwisko,
            String rks_imie


    ) throws UnsupportedEncodingException {

        String data = URLEncoder.encode("imie", "UTF-8")
                + "=" + URLEncoder.encode("" + imie, "UTF-8");

        data += "&" + URLEncoder.encode("nazwisko", "UTF-8") + "="
                + URLEncoder.encode(nazwisko, "UTF-8");

        data += "&" + URLEncoder.encode("telefon", "UTF-8") + "="
                + URLEncoder.encode(telefon, "UTF-8");

        data += "&" + URLEncoder.encode("email", "UTF-8") + "="
                + URLEncoder.encode(email, "UTF-8");

        data += "&" + URLEncoder.encode("odp1", "UTF-8") + "="
                + URLEncoder.encode(odp1, "UTF-8");

        data += "&" + URLEncoder.encode("odp2", "UTF-8") + "="
                + URLEncoder.encode(odp2, "UTF-8");

        data += "&" + URLEncoder.encode("odp3", "UTF-8") + "="
                + URLEncoder.encode(odp3, "UTF-8");

        data += "&" + URLEncoder.encode("odp3", "UTF-8") + "="
                + URLEncoder.encode(odp3, "UTF-8");

        data += "&" + URLEncoder.encode("nazwa_apteki", "UTF-8") + "="
                + URLEncoder.encode(nazwa_apteki, "UTF-8");


        data += "&" + URLEncoder.encode("ulica", "UTF-8") + "="
                + URLEncoder.encode(ulica, "UTF-8");

        data += "&" + URLEncoder.encode("miasto", "UTF-8") + "="
                + URLEncoder.encode(miasto, "UTF-8");

        data += "&" + URLEncoder.encode("wojewodztwo", "UTF-8") + "="
                + URLEncoder.encode(wojewodztwo, "UTF-8");
        data += "&" + URLEncoder.encode("nazwisko_przedstawiciela", "UTF-8") + "="
                + URLEncoder.encode(nazwisko_przedstawiciela, "UTF-8");


        data += "&" + URLEncoder.encode("imie_przedstawiciela", "UTF-8") + "="
                + URLEncoder.encode(imie_przedstawiciela, "UTF-8");

        data += "&" + URLEncoder.encode("rks_nazwisko", "UTF-8") + "="
                + URLEncoder.encode(rks_nazwisko, "UTF-8");

        data += "&" + URLEncoder.encode("rks_imie", "UTF-8") + "="
                + URLEncoder.encode(rks_imie, "UTF-8");


        String text = "";
        BufferedReader reader = null;

        // Send data
        try {

            // Defined URL  where to send data
            URL url = new URL("http://pharmawayjn.nazwa.pl/MedycynaRodzinna/livex/livex_add_result.php");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
            Log.d("result", "r = " + text);
        } catch (Exception ex) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onFail();
                }
            });
            return;
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onSuccess();
            }
        });

        return;
    }
}
