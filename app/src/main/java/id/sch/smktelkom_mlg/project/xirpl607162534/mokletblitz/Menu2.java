package id.sch.smktelkom_mlg.project.xirpl607162534.mokletblitz;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;


/**
 * Created by Jose Witjaksono on 23/11/2016.
 */

public class Menu2 extends Fragment{

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		//returning our layout file
		//change R.layout.yourlayoutfilename for each of your fragments
		return inflater.inflate(R.layout.activity_profile, container, false);
	}


	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		//you can set the title for your toolbar here for different fragments different titles
		getActivity().setTitle("Menu 2");
	}
}