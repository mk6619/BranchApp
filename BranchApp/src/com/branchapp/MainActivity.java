package com.branchapp;

import http_requests.HttpRequest_AsyncTask_Login;
import http_requests.Http_Url;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private EditText loginUser, loginPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loginUser = (EditText) findViewById(R.id.loginuser);
		loginPassword = (EditText) findViewById(R.id.loginpass);
		loginUser.clearFocus();
		Button b = (Button) findViewById(R.id.loginButton);
		Animation anim = AnimationUtils.loadAnimation(this,
				R.anim.abc_slide_in_bottom);
		b.startAnimation(anim);

		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new HttpRequest_AsyncTask_Login(loginUser.getText().toString(),
						loginPassword.getText().toString(), MainActivity.this,
						Http_Url.LOGIN_URL).execute();

			}
		});

	}
}
