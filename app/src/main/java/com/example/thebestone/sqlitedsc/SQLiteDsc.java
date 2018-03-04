package com.example.thebestone.sqlitedsc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by thebestone on 04/03/18.
 */

public class SQLiteDsc extends SQLiteOpenHelper {

    private static String DB_NAME = "DB_DSC";
    private static int DB_Version = 1;
    private static String CREATE_TABLE = "CREATE TABLE TB_DSC(name varchar(225), contact varchar(225))";

    Context context;

    public SQLiteDsc(Context context) {
        super(context, DB_NAME, null, DB_Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        } catch (SQLiteException e) {
            Toast.makeText(context, "Error : " + e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
