package com.branchapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_Navigation extends Fragment{
ActionBarDrawerToggle toggle;
DrawerLayout drawer;
	@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.drawer, container, false);
	TextView mUser = (TextView) view.findViewById(R.id.user);
	mUser.setText(getActivity().getIntent().getStringExtra("username"));
	return view;
}
	public  void setUp(Toolbar toolbar , DrawerLayout dl ){
		drawer = dl;
		toggle = new ActionBarDrawerToggle(getActivity(), dl,toolbar, R.string.hello_world, R.string.app_name);
		drawer.setDrawerListener(toggle);
		toggle.syncState();
	}
}
