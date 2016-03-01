package com.mygdx.livex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mygdx.game.R;
import com.mygdx.livex.database.DatabaseHelper;
import com.mygdx.livex.dialog.ChooseElementDialog;
import com.mygdx.livex.model.Row;
import com.mygdx.livex.model.UserData;
import com.mygdx.livex.util.KeyboardUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Radek on 2016-02-27.
 */
public class FormActivity extends AppCompatActivity {
    UserData userData = new UserData();
    @Bind(R.id.przedst)
    TextView mPrzedstawicielMedyczny;

    @Bind(R.id.miasto)
    TextView mMiasto;

    @Bind(R.id.apteka)
    TextView mApteka;

    @Bind(R.id.dalej)
    View mQuiz;
    @OnClick(R.id.dalej)
    void onDalej(View v) {
        if(mImie.isEnabled() && mImie.getText().length()>0 &&
                mNazwisko.getText().length()>0
                && (mTelefon.getText().length()>0  || mEmail.getText().length()>0 )
                ) {
            if(mCheck1.isChecked() == true && mCheck2.isChecked() == true) {
                userData.setUserData(mImie.getText().toString(), mNazwisko.getText().toString(), mTelefon.getText().toString(), mEmail.getText().toString(), mCheck1.isChecked(), mCheck2.isChecked(), mCheck3.isChecked());
                Intent intent = new Intent(this, AndroidLauncher.class);
                intent.putExtra("user_data", userData);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Musisz zaakceptować regulamin i wyrazić zgodę na przetwarzanie danych osobowych.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Uzupełnij wszystkie dane!", Toast.LENGTH_SHORT).show();
        }

    }


    @OnClick(R.id.regulamin)
    void onRegulamin(View veiw) {
        startActivity(new Intent(this, RegulaminActivity.class));
    }

    @Bind(R.id.imie)
    EditText mImie;

    @Bind(R.id.nazwisko)
    EditText mNazwisko;

    @Bind(R.id.telefon)
    EditText mTelefon;

    @Bind(R.id.email)
    EditText mEmail;

    @Bind(R.id.check1)
    CheckBox mCheck1;

    @Bind(R.id.check2)
    CheckBox mCheck2;

    @Bind(R.id.check3)
    CheckBox mCheck3;

    String imie_przedstawiciela;
    String nazwisko_przedstawiciela;
    String miasto;
    String nazwa_apteki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.form_activity);

        ButterKnife.bind(this);


        KeyboardUtil keyboardUtil = new KeyboardUtil(this, findViewById(R.id.content));

//enable it
        keyboardUtil.enable();

        mMiasto.setEnabled(false);
        mApteka.setEnabled(false);
        mImie.setEnabled(false);
        mNazwisko.setEnabled(false);
        mTelefon.setEnabled(false);
        mEmail.setEnabled(false);

        mPrzedstawicielMedyczny.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ChooseElementDialog() {

                    @Override
                    public String getRowText(Row row) {
                        return row.imie_przedstawiciela + " " + row.nazwisko_przedstawiciela;
                    }

                    @Override
                    public List<Row> getRows(Context context) {
                        return DatabaseHelper.rowsForPrzedstawiciel(context);
                    }

                    @Override
                    public void onRowSelected(Row row) {
                        imie_przedstawiciela = row.imie_przedstawiciela;
                        nazwisko_przedstawiciela = row.nazwisko_przedstawiciela;
                        mPrzedstawicielMedyczny.setText(getRowText(row));
                        mMiasto.setEnabled(true);
                        mMiasto.setText("Miasto");
                        mApteka.setText("Apteka");

                        mApteka.setEnabled(false);
                        mImie.setEnabled(false);
                        mNazwisko.setEnabled(false);
                        mTelefon.setEnabled(false);
                        mEmail.setEnabled(false);
                    }
                }.show(getSupportFragmentManager(), "tag");
            }
        });


        mMiasto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ChooseElementDialog() {

                    @Override
                    public String getRowText(Row row) {
                        return row.miasto;
                    }

                    @Override
                    public List<Row> getRows(Context context) {
                        return DatabaseHelper.rowsForPrzedstawiciel(imie_przedstawiciela, nazwisko_przedstawiciela, context);
                    }

                    @Override
                    public void onRowSelected(Row row) {
                        miasto = row.miasto;
                        mMiasto.setText(miasto);
                        mApteka.setEnabled(true);
                        mApteka.setText("Apteka");

                        mImie.setEnabled(false);
                        mNazwisko.setEnabled(false);
                        mTelefon.setEnabled(false);
                        mEmail.setEnabled(false);
                    }
                }.show(getSupportFragmentManager(), "tag");
            }
        });

        mApteka.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new ChooseElementDialog() {

                    @Override
                    public boolean isTwoLines() {
                        return true;
                    }

                    @Override
                    public String getRowText(Row row) {
                        return row.nazwa_apteki;
                    }

                    @Override
                    public List<Row> getRows(Context context) {
                        return DatabaseHelper.rowsForPrzedstawiciel(imie_przedstawiciela, nazwisko_przedstawiciela, miasto, context);
                    }

                    @Override
                    public void onRowSelected(Row row) {
                        nazwa_apteki = row.nazwa_apteki;
                        mApteka.setText(nazwa_apteki);
                        mImie.setEnabled(true);
                        mNazwisko.setEnabled(true);
                        mTelefon.setEnabled(true);
                        mEmail.setEnabled(true);


                        userData.setRow(row);
                    }
                }.show(getSupportFragmentManager(), "tag");
            }
        });

        mImie.addTextChangedListener(mTextWatcher);
        mNazwisko.addTextChangedListener(mTextWatcher);
        mTelefon.addTextChangedListener(mTextWatcher);
        mEmail.addTextChangedListener(mTextWatcher);

        mCheck1.setOnCheckedChangeListener(mOnCheckCahangeListener);
        mCheck2.setOnCheckedChangeListener(mOnCheckCahangeListener);
        mCheck3.setOnCheckedChangeListener(mOnCheckCahangeListener);
    }

    private void checkConditions() {
        if(mImie.isEnabled() && mImie.getText().length()>0 &&
                mNazwisko.getText().length()>0
                && (mTelefon.getText().length()>0  || mEmail.getText().length()>0 )
                ) {
            if(mCheck1.isChecked() == true && mCheck2.isChecked() == true) {
                mQuiz.setAlpha(1);
            } else {
                mQuiz.setAlpha(0.5f);
            }
        } else {
            mQuiz.setAlpha(0.5f);
        }
    }

    CompoundButton.OnCheckedChangeListener mOnCheckCahangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            checkConditions();
        }
    };

    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkConditions();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
