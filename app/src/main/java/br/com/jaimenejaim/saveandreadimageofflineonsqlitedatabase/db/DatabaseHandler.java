package br.com.jaimenejaim.saveandreadimageofflineonsqlitedatabase.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.jaimenejaim.saveandreadimageofflineonsqlitedatabase.config.AppConfig;
import br.com.jaimenejaim.saveandreadimageofflineonsqlitedatabase.facades.InterfacePhotoModel;
import br.com.jaimenejaim.saveandreadimageofflineonsqlitedatabase.model.PhotoModel;

/**
 * Created by jaimenejaim on 23/08/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper implements InterfacePhotoModel {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());



    private static final String TABLE_NAME = "PhotosDB";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PHOTO = "_data";
    private static final String COLUMN_CREATED_AT = "created_at";




    public DatabaseHandler(Context context) {
        super(context, AppConfig.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MODEL_TABLE_MONITORAMENTO = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_PHOTO + " BLOB, "
                + COLUMN_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP);";
        db.execSQL(CREATE_MODEL_TABLE_MONITORAMENTO);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }






    @Override
    public void add(PhotoModel photoModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHOTO, photoModel.getByteBuffer());
        values.put(COLUMN_CREATED_AT, System.currentTimeMillis());

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public PhotoModel get(int index) {

        String query = "SELECT "+ COLUMN_ID + ","
                        + COLUMN_PHOTO + ","
                        +COLUMN_CREATED_AT +
                        " FROM "+ TABLE_NAME +" WHERE "+ COLUMN_ID +" = "+ index;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        PhotoModel monitoramento = null;

        if (cursor.moveToFirst()) {
            monitoramento  = new PhotoModel();
            monitoramento.setId(Integer.parseInt(cursor.getString(0)));
//            monitoramento.setByteBuffer(cursor.getBlob(1));
            monitoramento.setCreated_at(new Date(cursor.getLong(2)));


        }

        return monitoramento;
    }

    @Override
    public List<PhotoModel> all() {

        SQLiteDatabase db = this.getWritableDatabase();

        List<PhotoModel> monitoramentos = new ArrayList<>();

        String query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_CREATED_AT +" DESC";


        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                PhotoModel monitoramento = new PhotoModel();
                monitoramento.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                monitoramento.setByteBuffer(cursor.getBlob(cursor.getColumnIndex(COLUMN_PHOTO)));
                monitoramento.setCreated_at(new Date(cursor.getColumnIndex(COLUMN_CREATED_AT)));
                monitoramentos.add(monitoramento);
            } while (cursor.moveToNext());
        }
        return monitoramentos;
    }

    @Override
    public void remove(int index) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query  = "DELETE FROM " + TABLE_NAME + " WHERE "+ COLUMN_ID +" = " + index;

        db.execSQL(query);
        db.close();

    }






}
