package com.mygdx.livex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.mygdx.livex.database.DbRepository;
import com.mygdx.livex.model.NotSendUser;
import com.mygdx.livex.model.Row;
import com.mygdx.livex.model.UserData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.qbusict.cupboard.CupboardFactory;

/**
 * Created by Radek on 2016-02-27.
 */
public class SendingDataActivity extends AppCompatActivity {
    UserData mUserData;

    @OnClick(R.id.try_again)
    void tryAgain(View v) {
        findViewById(R.id.error_view).setVisibility(View.GONE);
        findViewById(R.id.loading_view).setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendData(mUserData);
            }
        }).start();
    }

    void onFail() {
//        findViewById(R.id.error_view).setVisibility(View.VISIBLE);
//        findViewById(R.id.loading_view).setVisibility(View.GONE);

        Toast.makeText(this, "Brak połaczenia z Internetem.", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Twoje zgłoszenie zostanie wysłane, kiedy uzyskasz dostęp do sieci", Toast.LENGTH_LONG).show();

        Intent startIntent = new Intent(SendingDataActivity.this, PodiumActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startIntent);

        finish();

    }

    void onSuccess() {
        Toast.makeText(this, "Twoje zgłoszenie zostało wysłane", Toast.LENGTH_LONG).show();

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

        findViewById(R.id.error_view).setVisibility(View.GONE);
        findViewById(R.id.loading_view).setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendData(mUserData);
            }
        }).start();
    }

    public void sendData(UserData userData) {

        Row row = userData.getRow();
        try {
            String data_utworzenia = DateFormat.format("yyyy-MM-dd hh:mm:ss", new java.util.Date()).toString();//new Date().toString();

            sendData(userData.getImie(), userData.getNazwisko(), userData.getTelefon(), userData.getEmail(), userData.getCheck1(), userData.getCheck2(), userData.getCheck3(),
                    row.nazwa_apteki, row.ulica, row.miasto, row.wojewodztwo, row.nazwisko_przedstawiciela, row.imie_przedstawiciela, row.rks_nazwisko, row.rks_imie, data_utworzenia);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void sendData(
            final String imie,
            final String nazwisko,

            final String telefon,
            final String email,
            final String odp1,
            final String odp2,
            final String odp3,
            final String nazwa_apteki,
            final String ulica,
            final String miasto,
            final String wojewodztwo,
            final String nazwisko_przedstawiciela,
            final String imie_przedstawiciela,
            final String rks_nazwisko,
            final String rks_imie,
            final String data_utworzenia


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



        data += "&" + URLEncoder.encode("data_utworzenia", "UTF-8") + "="
                + URLEncoder.encode(data_utworzenia, "UTF-8");

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
            if(!"OK\n".equals(text)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        NotSendUser notSendUser = getNotSendUser(imie, nazwisko, nazwa_apteki, ulica);

                        if (notSendUser == null) {
                            notSendUser = new NotSendUser();
                            notSendUser.imie = imie;
                            notSendUser.nazwisko = nazwisko;

                            notSendUser.telefon = telefon;
                            notSendUser.email = email;
                            notSendUser.odp1 = odp1;
                            notSendUser.odp2 = odp2;
                            notSendUser.odp3 = odp3;
                            notSendUser.nazwa_apteki = nazwa_apteki;
                            notSendUser.ulica = ulica;
                            notSendUser.miasto = miasto;
                            notSendUser.wojewodztwo = wojewodztwo;
                            notSendUser.nazwisko_przedstawiciela = nazwisko_przedstawiciela;
                            notSendUser.imie_przedstawiciela = imie_przedstawiciela;
                            notSendUser.rks_nazwisko = rks_nazwisko;
                            notSendUser.rks_imie = rks_imie;
                            notSendUser.data_utworzenia = data_utworzenia;
                            notSendUser.save(SendingDataActivity.this);

                        }
                        onFail();
                    }
                });
                return;
            }
            Log.d("result", "r = " + text);
        } catch (Exception ex) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    NotSendUser notSendUser = getNotSendUser(imie, nazwisko, nazwa_apteki, ulica);

                    if(notSendUser == null) {
                        notSendUser = new NotSendUser();
                        notSendUser.imie = imie;
                        notSendUser.nazwisko = nazwisko;

                        notSendUser.telefon = telefon;
                        notSendUser.email = email;
                        notSendUser.odp1 = odp1;
                        notSendUser.odp2 = odp2;
                        notSendUser.odp3 = odp3;
                        notSendUser.nazwa_apteki = nazwa_apteki;
                        notSendUser.ulica = ulica;
                        notSendUser.miasto = miasto;
                        notSendUser.wojewodztwo = wojewodztwo;
                        notSendUser.nazwisko_przedstawiciela = nazwisko_przedstawiciela;
                        notSendUser.imie_przedstawiciela = imie_przedstawiciela;
                        notSendUser.rks_nazwisko = rks_nazwisko;
                        notSendUser.rks_imie = rks_imie;
                        notSendUser.data_utworzenia = data_utworzenia;
                        notSendUser.save(SendingDataActivity.this);

                    }
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
                NotSendUser notSendUser = getNotSendUser(imie, nazwisko, nazwa_apteki, ulica);
                if(notSendUser != null) {
                    notSendUser.delete(SendingDataActivity.this);
                }
                onSuccess();
            }
        });

        return;
    }

    NotSendUser getNotSendUser(String imie, String nazwisko, String nazwa_apteki, String ulica) {
        NotSendUser notSendUser = CupboardFactory.cupboard().withDatabase(DbRepository.getDb(SendingDataActivity.this)).query(NotSendUser.class).withSelection("imie = ? and nazwisko = ? and nazwa_apteki = ? and ulica = ?",
                new String[]{imie,nazwisko, nazwa_apteki, ulica}).get();
        return notSendUser;
    }
}
