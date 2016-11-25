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

public class Menu2 extends Fragment implements View.OnClickListener{

	private static final int PICK_IMAGE_REQUEST = 234;
	private ImageView imageView;
	private Button buttonChoose,buttonUpload;
	private Uri filePath;
	private StorageReference storageRef;
	UploadActivity ua = new UploadActivity();

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

			View myView = inflater.inflate(R.layout.activity_profile, container, false);

			storageRef = FirebaseStorage.getInstance().getReference();
			imageView = (ImageView) myView.findViewById(R.id.ivGalery);
			buttonChoose = (Button) myView.findViewById(R.id.buttonChoose);
			buttonUpload = (Button) myView.findViewById(R.id.buttonImageUpload);
			return myView;
		}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == PICK_IMAGE_REQUEST && resultCode == ua.RESULT_OK && data != null && data.getData() != null){
			filePath = data.getData();
			try {
				Bitmap bitmap = MediaStore.Images.Media.getBitmap(ua.getContentResolver(),filePath);
				imageView.setImageBitmap(bitmap);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

		@Override
		public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);
			//you can set the title for your toolbar here for different fragments different titles
			getActivity().setTitle("Menu 2");
			buttonChoose.setOnClickListener(this);
			buttonUpload.setOnClickListener(this);
		}

	@Override
	public void onClick(View view) {
		if (view == buttonChoose){
			ua.showFileChooser();
		} else if (view == buttonUpload){
			ua.uploadFile();
		}
	}
}