package br.com.jaimenejaim.saveandreadimageofflineonsqlitedatabase.facades;

import java.util.List;

import br.com.jaimenejaim.saveandreadimageofflineonsqlitedatabase.model.PhotoModel;

/**
 * Created by jaimenejaim on 23/08/17.
 */

public interface InterfacePhotoModel {

    void add(PhotoModel photoModel);
    PhotoModel get(int index);
    List<PhotoModel> all();
    void remove(int index);

}
