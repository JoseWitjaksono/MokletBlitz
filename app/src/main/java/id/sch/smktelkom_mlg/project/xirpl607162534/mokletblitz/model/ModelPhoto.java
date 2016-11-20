package id.sch.smktelkom_mlg.project.xirpl607162534.mokletblitz.model;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by JoseWitjaksono on 15/11/16.
 */
public class ModelPhoto extends RealmObject {
    @SerializedName("nama") public String nama;
    @SerializedName("foto") public String foto;
}
