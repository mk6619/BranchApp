package com.branchapp;

import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

public class StudentDetailActivity extends AppCompatActivity{
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_student_details);
	Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
	toolbar.setTitle(getIntent().getStringExtra("name"));
	setSupportActionBar(toolbar);
	ImageView iv = (ImageView) findViewById(R.id.img);
	Picasso.with(this).load(getIntent().getStringExtra("img_url")).into(iv);
	
}
}
