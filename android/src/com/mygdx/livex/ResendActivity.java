package com.mygdx.livex;

/**
 * Created by Radek on 03.04.2016.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mygdx.livex.database.DbRepository;
import com.mygdx.livex.model.NotSendUser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.qbusict.cupboard.CupboardFactory;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by Radek on 20.03.2016.
 */
public class ResendActivity extends AppCompatActivity {
    @Bind(R.id.info)
    TextView info;

    @Bind(R.id.send)
    Button send;

    @OnClick(R.id.send)
    void send(View v) {
        trySend();
    }

    @Bind(R.id.progress_bar)
    View progressBar;
    List<NotSendUser> notSendUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.resend_activity);

        ButterKnife.bind(this);

        progressBar.setVisibility(View.GONE);
        send.setVisibility(View.VISIBLE);
        info.setVisibility(View.VISIBLE);

        notSendUsers = cupboard().withDatabase(DbRepository.getDb(this)).query(NotSendUser.class).list();

        int count = notSendUsers.size();
        info.setText("" + count + " - ilość ankiet czekających na wysłanie");
    }
    int current = 0;
    public void trySend() {

        progressBar.setVisibility(View.VISIBLE);
        send.setVisibility(View.GONE);
        info.setVisibility(View.GONE);

        if(current < notSendUsers.size()) {
            final NotSendUser user = notSendUsers.get(current);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendData(user);
                }
            }).start();
        } else {
            progressBar.setVisibility(View.GONE);
            send.setVisibility(View.VISIBLE);
            info.setVisibility(View.VISIBLE);

            notSendUsers = cupboard().withDatabase(DbRepository.getDb(this)).query(NotSendUser.class).list();

            int count = notSendUsers.size();
            info.setText("" + count + " - ilość ankiet czekających na wysłanie");
        }
    }
    void onSuccess() {
        current++;
        trySend();
    }
    void onFail() {
        progressBar.setVisibility(View.GONE);
        send.setVisibility(View.VISIBLE);
        info.setVisibility(View.VISIBLE);

        notSendUsers = cupboard().withDatabase(DbRepository.getDb(this)).query(NotSendUser.class).list();
        Toast.makeText(this, "Błąd podczas wysyłania", Toast.LENGTH_SHORT).show();
    }

    public void sendData(NotSendUser notSendUser) {
        try {
            sendData(notSendUser.imie,
                    notSendUser.nazwisko,

                    notSendUser.telefon,
                    notSendUser.email,
                    notSendUser.odp1,
                    notSendUser.odp2,
                    notSendUser.odp3,
                    notSendUser.nazwa_apteki,
                    notSendUser.ulica,
                    notSendUser.miasto,
                    notSendUser.wojewodztwo,
                    notSendUser.nazwisko_przedstawiciela,
                    notSendUser.imie_przedstawiciela,
                    notSendUser.rks_nazwisko,
                    notSendUser.rks_imie,
                    notSendUser.data_utworzenia);
        }catch (Exception e) {

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
                            notSendUser.save(ResendActivity.this);

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
                        notSendUser.save(ResendActivity.this);

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
                    notSendUser.delete(ResendActivity.this);
                }
                onSuccess();
            }
        });

        return;
    }

    NotSendUser getNotSendUser(String imie, String nazwisko, String nazwa_apteki, String ulica) {
        NotSendUser notSendUser = CupboardFactory.cupboard().withDatabase(DbRepository.getDb(ResendActivity.this)).query(NotSendUser.class).withSelection("imie = ? and nazwisko = ? and nazwa_apteki = ? and ulica = ?",
                new String[]{imie,nazwisko, nazwa_apteki, ulica}).get();
        return notSendUser;
    }
}
