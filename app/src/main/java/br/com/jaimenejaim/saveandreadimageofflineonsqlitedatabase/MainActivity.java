package br.com.jaimenejaim.saveandreadimageofflineonsqlitedatabase;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.List;

import br.com.jaimenejaim.saveandreadimageofflineonsqlitedatabase.db.DatabaseHandler;
import br.com.jaimenejaim.saveandreadimageofflineonsqlitedatabase.model.PhotoModel;

public class MainActivity extends AppCompatActivity {


    private final String TAG = this.getClass().getSimpleName();

    public static final int PICTURE_TAKEN_FROM_CAMERA = 100;

    // file url to store image/video
    public  Uri fileUri;


    ListView mListView;
    PhotoListViewAdapter mListViewAdapter;

    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listview);


        new AsyncTask<Void, Void, List<PhotoModel>>() {

            public ProgressDialog dialog;

            @Override
            protected void onPreExecute() {




            }

            @Override
            protected List<PhotoModel> doInBackground(Void... params) {

//                dialog = new ProgressDialog(MainActivity.this);
//                dialog.setTitle("Aguarde...");
//                dialog.setMessage("listando monitoramentos.");
//                dialog.setCancelable(false);
//                dialog.show();

                db = new DatabaseHandler(MainActivity.this);


                List<PhotoModel> photos = db.all();


                if(photos.size() == 0){
                    makeFakeObjectToPersistInDatabase();
                    photos = db.all();
                }



                return photos;
            }

            @Override
            protected void onPostExecute(List<PhotoModel> monitoramentos) {
//                dialog.dismiss();

                mListViewAdapter = new PhotoListViewAdapter(MainActivity.this, monitoramentos);

                mListView.setAdapter(mListViewAdapter);

            }
        }.execute();




    }



    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                getContentResolver().openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 100;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
                getContentResolver().openInputStream(selectedImage), null, o2);
    }



    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }




    private void makeFakeObjectToPersistInDatabase(){




        String uri1 = "@drawable/photo_1";
        String uri2 = "@drawable/photo_2";
        String uri3 = "@drawable/photo_3";
        String uri4 = "@drawable/photo_4";

        int imageResource1 = getResources().getIdentifier(uri1, null, getPackageName());
        int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
        int imageResource3 = getResources().getIdentifier(uri3, null, getPackageName());
        int imageResource4 = getResources().getIdentifier(uri4, null, getPackageName());

        Drawable res0 = getResources().getDrawable(imageResource1);
        Drawable res1 = getResources().getDrawable(imageResource2);
        Drawable res2 = getResources().getDrawable(imageResource3);
        Drawable res3 = getResources().getDrawable(imageResource4);


        Bitmap bitmap1 = ((BitmapDrawable)res0).getBitmap();
        Bitmap bitmap2 = ((BitmapDrawable)res1).getBitmap();
        Bitmap bitmap3 = ((BitmapDrawable)res2).getBitmap();
        Bitmap bitmap4 = ((BitmapDrawable)res3).getBitmap();

        PhotoModel photoModel0 = new PhotoModel();
        photoModel0.setByteBuffer(getBytes(bitmap1));

        PhotoModel photoModel1 = new PhotoModel();
        photoModel1.setByteBuffer(getBytes(bitmap2));

        PhotoModel photoModel2 = new PhotoModel();
        photoModel2.setByteBuffer(getBytes(bitmap3));

        PhotoModel photoModel3 = new PhotoModel();
        photoModel3.setByteBuffer(getBytes(bitmap4));

        db = new DatabaseHandler(this);
        db.add(photoModel0);
        db.add(photoModel1);
        db.add(photoModel2);
        db.add(photoModel3);


    }


//    private class TaskPhoto extends AsyncTask<Void, Void, List<PhotoModel>> {
//
//        public ProgressDialog dialog;
//
//        @Override
//        protected void onPreExecute() {
//            dialog = ProgressDialog.show(MainActivity.this,"Aguarde...","listando monitoramentos.",true,true);
//        }
//
//        @Override
//        protected List<PhotoModel> doInBackground(Void... params) {
//
//            db = new DatabaseHandler(MainActivity.this);
//
//            return db.all();
//        }
//
//        @Override
//        protected void onPostExecute(List<PhotoModel> monitoramentos) {
//            dialog.dismiss();
//
//            mListViewAdapter = new PhotoListViewAdapter(MainActivity.this, monitoramentos);
//
//            mListView.setAdapter(mListViewAdapter);
//
//        }
//    }

}
