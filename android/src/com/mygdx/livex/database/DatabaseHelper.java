package com.mygdx.livex.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mygdx.livex.model.Row;

import java.util.List;

import nl.qbusict.cupboard.CupboardBuilder;
import nl.qbusict.cupboard.CupboardFactory;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by Radek on 2016-02-26.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    static {
        CupboardFactory.setCupboard(new CupboardBuilder().useAnnotations().build());

        cupboard().register(Row.class);
    }

    public DatabaseHelper(Context context) {
        super(context, "database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        cupboard().withDatabase(sqLiteDatabase).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        cupboard().withDatabase(sqLiteDatabase).upgradeTables();
    }

    //Nawrot
    //Aleksandra

    public static List<Row> rowsForPrzedstawiciel(String imie, String nazwisko, Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        return cupboard().withDatabase(db).query(Row.class).withSelection("imie_przedstawiciela LIKE ? and nazwisko_przedstawiciela LIKE ?", new String[]{imie, nazwisko}).groupBy("miasto").list();
    }

    public static List<Row> rowsForPrzedstawiciel(String imie, String nazwisko, String miasto, Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        return cupboard().withDatabase(db).query(Row.class).withSelection("imie_przedstawiciela LIKE ? and nazwisko_przedstawiciela LIKE ? and miasto LIKE ?", new String[]{imie, nazwisko, miasto}).list();
    }


    public static List<Row> rowsForPrzedstawiciel(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        return cupboard().withDatabase(db).query(Row.class).groupBy("imie_przedstawiciela, nazwisko_przedstawiciela").list();
    }
}
