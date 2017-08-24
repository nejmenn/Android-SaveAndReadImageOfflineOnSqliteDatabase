package br.com.jaimenejaim.saveandreadimageofflineonsqlitedatabase.model;

import android.net.Uri;

import java.util.Date;

/**
 * Created by jaimenejaim on 23/08/17.
 */

public class PhotoModel {
    private int id;
    private byte[] byteBuffer;
    private Date created_at;
    private Uri uri;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public byte[] getByteBuffer() {
        return byteBuffer;
    }
    public void setByteBuffer(byte[] byteBuffer) {
        this.byteBuffer = byteBuffer;
    }
    public Date getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
    public Uri getUri() {
        return uri;
    }
    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
