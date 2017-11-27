package com.branchapp;

import adapters.ListView_Adapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class HomeActivityTeacher extends AppCompatActivity {
	Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acitivty_home_teacher);
		ListView lv = (ListView) findViewById(R.id.listdrawer);
		String items[] = { "Home", "Students", "Send Notification",
				"Notifications" };
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		//		android.R.layout.simple_list_item_1, items);
		 ListView_Adapter adapter = new ListView_Adapter(items, this);
		lv.setAdapter(adapter);
		DrawerLayout dl = (DrawerLayout) findViewById(R.id.dlt);
		toolbar = (Toolbar) findViewById(R.id.hometoolbarteacher);
		toolbar.setTitle("Home");
		setSupportActionBar(toolbar);
		Fragment_Navigation frag = (Fragment_Navigation) getSupportFragmentManager()
				.findFragmentById(R.id.fragidt);
		frag.setUp(toolbar, dl);
		FragmentManager man;
		man = getSupportFragmentManager();
		Fragment_Home_Teacher sch = new Fragment_Home_Teacher();
		man.beginTransaction().replace(R.id.frameLayout, sch).commit();

		lv.setOnItemClickListener(new OnItemClickListener() {
			FragmentManager man = getSupportFragmentManager();

			Fragment fragment;

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:
					fragment = new Fragment_Home_Teacher();
					toolbar.setTitle("Home");
					break;
				case 1:
					fragment = new Fragment_Students();
					toolbar.setTitle("Students");
					break;
				case 3:
					fragment = new Fragment_Notifications();
					toolbar.setTitle("Notifications");
					break;
				case 2:
					fragment = new Fragment_SendNoti();
					toolbar.setTitle("Send Notification");
					break;
				}

				man.beginTransaction().replace(R.id.frameLayout, fragment)
						.commit();
				DrawerLayout dl = (DrawerLayout) findViewById(R.id.dlt);
				dl.closeDrawers();
			}
		});
	}
}
