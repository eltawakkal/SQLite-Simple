package com.example.thebestone.sqlitedsc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SQLiteDsc sqLiteDsc;
    SQLiteDatabase db;
    Cursor cursor;

    String data[];

    ListView listView;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteDsc = new SQLiteDsc(MainActivity.this);
        listView = findViewById(R.id.mListDataDSC);
        fab = findViewById(R.id.fabAdd);

        //read  the database

        refreshListView();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //write the database

               showAddDialog();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int position, final long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Hapus!");
                alert.setMessage("Apakah Data Akan Dihapus?");
                alert.setCancelable(false);
                alert.setNegativeButton("Batal", null);
                alert.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            db = sqLiteDsc.getWritableDatabase();
                            db.execSQL("delete from TB_DSC where name = '" + data[position] + "'");

                            refreshListView();
                        } catch (SQLiteException e) {
                            Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alert.create();
                alert.show();



                return true;
            }
        });
    }

    public void showAddDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final View v = getLayoutInflater().inflate(R.layout.custom_add, null);

        alert.setTitle("Tambah Data");
        alert.setView(v);
        alert.setCancelable(false);
        alert.setNegativeButton("Batal", null);
        alert.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {

                    EditText editName = v.findViewById(R.id.editName);
                    EditText editContact = v.findViewById(R.id.editContact);

                    String name = editName.getText().toString();
                    String contact = editContact.getText().toString();

                    db = sqLiteDsc.getWritableDatabase();
                    db.execSQL("insert into TB_DSC (name, contact) values ('" + name + "', '"+ contact + "')");

                    refreshListView();
                } catch (SQLiteException e) {
                    Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });
        alert.create();
        alert.show();

    }

    private void refreshListView() {
        db = sqLiteDsc.getReadableDatabase();
        cursor = db.rawQuery("select * from TB_DSC", null);

        if (cursor.getCount()==0) {
            Toast.makeText(this, "No Data Saved!", Toast.LENGTH_SHORT).show();
        } else {

            data = new String[cursor.getCount()];
            cursor.moveToFirst();

            for (int i = 0; i < data.length; i ++) {
                data[i] = cursor.getString(0);
                cursor.moveToNext();
            }

            listView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, data));
        }
    }
}
