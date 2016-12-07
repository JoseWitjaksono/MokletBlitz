package id.sch.smktelkom_mlg.project.xirpl607162534.mokletblitz;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * Created by Jose Witjaksono on 23/11/2016.
 */

public class Menu2 extends Fragment{

	private static final int PICK_IMAGE_REQUEST = 234;
	private ImageView imageView;
	private Button buttonChoose,buttonUpload;
	private Uri filePath;
	private StorageReference storageRef;
	private FirebaseAuth firebaseAuth;

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

			View myView = inflater.inflate(R.layout.activity_upload, container, false);
			firebaseAuth = FirebaseAuth.getInstance();
			FirebaseUser user = firebaseAuth.getCurrentUser();
			storageRef = FirebaseStorage.getInstance().getReference();
			imageView = (ImageView) myView.findViewById(R.id.ivGalery);
			buttonChoose = (Button) myView.findViewById(R.id.buttonChoose);
			buttonUpload = (Button) myView.findViewById(R.id.buttonImageUpload);
			buttonChoose.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					showFileChooser();
				}
			});
			buttonUpload.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					uploadFile();
				}
			});
			return myView;
		}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
			filePath = data.getData();
			try {
				Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(),filePath);
				imageView.setImageBitmap(bitmap);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void showFileChooser(){
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent,"Pilih Gambar"),PICK_IMAGE_REQUEST);
	}

	private void uploadFile(){

		if  (filePath != null){

			final ProgressDialog progressDialog = new ProgressDialog(getActivity());
			progressDialog.setTitle("Uploading...");
			progressDialog.show();

			StorageReference riversRef = storageRef.child("images/"+firebaseAuth.getCurrentUser().getUid()+".jpg");

			riversRef.putFile(filePath)
					.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
						@Override
						public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
							Toast.makeText(getActivity().getApplicationContext(),"File Uploaded", Toast.LENGTH_LONG).show();

						}
					})
					.addOnFailureListener(new OnFailureListener() {
						@Override
						public void onFailure(@NonNull Exception exception) {
							progressDialog.dismiss();
							Toast.makeText(getActivity().getApplicationContext(),exception.getMessage(), Toast.LENGTH_LONG).show();
						}
					})
					.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
						@Override
						public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
							double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
							progressDialog.setMessage(((int) progress)+ "% Uploaded...");
						}
					});
		}else
		{
		}
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		//you can set the title for your toolbar here for different fragments different titles
		getActivity().setTitle("Menu 2");
	}
}