package id.sch.smktelkom_mlg.project.xirpl607162534.mokletblitz;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created by Jose Witjaksono on 23/11/2016.
 */

public class Menu1 extends Fragment implements View.OnClickListener{

	private TextView textViewUserEmail;
	private Button buttonLogout;
	private FirebaseAuth firebaseAuth;
	ProfileActivity pa = new ProfileActivity();

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myView = inflater.inflate(R.layout.activity_profile, container,false);
		buttonLogout = (Button) myView.findViewById(R.id.buttonLogout);
		textViewUserEmail = (TextView) myView.findViewById(R.id.textViewUserEmail);

		firebaseAuth = FirebaseAuth.getInstance();

		if(firebaseAuth.getCurrentUser() == null){
			pa.finish();
			startActivity(new Intent(getActivity(), LoginActivity.class));
		}

		FirebaseUser user = firebaseAuth.getCurrentUser();

		textViewUserEmail = (TextView)myView.findViewById(R.id.textViewUserEmail);
		buttonLogout = (Button) myView.findViewById(R.id.buttonLogout);

		textViewUserEmail.setText("Welcome "+user.getEmail());

		buttonLogout.setOnClickListener(this);
		return myView;
	}

	@Override
	public void onClick(View view) {
		if(view == buttonLogout){
			firebaseAuth.signOut();
			pa.finish();
			startActivity(new Intent(getActivity(), LoginActivity.class));
		}
	}
}