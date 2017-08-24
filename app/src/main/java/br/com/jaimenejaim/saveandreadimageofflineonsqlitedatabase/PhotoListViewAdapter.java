package br.com.jaimenejaim.saveandreadimageofflineonsqlitedatabase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import br.com.jaimenejaim.saveandreadimageofflineonsqlitedatabase.model.PhotoModel;

/**
 * Created by jaimenejaim on 23/08/17.
 */

public class PhotoListViewAdapter extends BaseAdapter {

    private List<PhotoModel> objects;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public PhotoListViewAdapter(Context mContext, List<PhotoModel> objects){
        this.objects = objects;
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public PhotoModel getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();

        if( convertView == null ){
            //We must create a View:
            convertView = mLayoutInflater.inflate(R.layout.row_listview, viewGroup, false);
        }


        PhotoModel photoModel = getItem(index);


        viewHolder.photo = convertView.findViewById(R.id.photo);
        viewHolder.photo.setImageBitmap(MainActivity.getImage(photoModel.getByteBuffer()));


        return convertView;
    }


    protected class ViewHolder{
        private ImageView photo;
    }





    private Bitmap decodeBitmap(byte[] b) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        ByteArrayInputStream bis = new ByteArrayInputStream(b);
        BitmapFactory.decodeStream(bis, null, o);

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
        return BitmapFactory.decodeStream(bis, null, o2);
    }


}
