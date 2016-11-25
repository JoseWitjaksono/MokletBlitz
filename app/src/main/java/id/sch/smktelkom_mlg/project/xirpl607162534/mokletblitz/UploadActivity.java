package id.sch.smktelkom_mlg.project.xirpl607162534.mokletblitz;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {
	private static final int PICK_IMAGE_REQUEST = 234;
	private ImageView imageView;
	private Button buttonChoose,buttonUpload;
	private Uri filePath;
	private StorageReference storageRef;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);

		storageRef = FirebaseStorage.getInstance().getReference();
		imageView = (ImageView) findViewById(R.id.ivGalery);
		buttonChoose = (Button) findViewById(R.id.buttonChoose);
		buttonUpload = (Button) findViewById(R.id.buttonImageUpload);

		buttonChoose.setOnClickListener(this);
		buttonUpload.setOnClickListener(this);
	}

	public void showFileChooser(){
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent,"Pilih Gambar"),PICK_IMAGE_REQUEST);
	}

	public void uploadFile(){

		if  (filePath != null){

			final ProgressDialog progressDialog = new ProgressDialog(this);
			progressDialog.setTitle("Uploading...");
			progressDialog.show();

			StorageReference riversRef = storageRef.child("images/rivers.jpg");

			riversRef.putFile(filePath)
					.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
						@Override
						public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
							Toast.makeText(getApplicationContext(),"File Uploaded", Toast.LENGTH_LONG).show();

						}
					})
					.addOnFailureListener(new OnFailureListener() {
						@Override
						public void onFailure(@NonNull Exception exception) {
							progressDialog.dismiss();
							Toast.makeText(getApplicationContext(),exception.getMessage(), Toast.LENGTH_LONG).show();
						}
					})
					.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
						@Override
						public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
							double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
							progressDialog.setMessage(((int) progress)+ "% Uploaded...");
						}
					});
		}else {

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
			filePath = data.getData();
			try {
				Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
				imageView.setImageBitmap(bitmap);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(View view) {

		if (view == buttonChoose){
			showFileChooser();
		} else if (view == buttonUpload){
			uploadFile();
		}
	}
}