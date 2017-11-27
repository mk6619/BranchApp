package adapters;


import java.util.List;

import com.branchapp.R;
import com.branchapp.StudentDetailActivity;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Student_RecyclerView extends RecyclerView.Adapter<Student_RecyclerView.MyViewHolder>{
Context context;
LayoutInflater inflater;
List<Data> list; 
public Student_RecyclerView(Context context,List<Data> list) {
	this.list = list;
	this.context = context;
	inflater = LayoutInflater.from(context);
}	

@Override
public int getItemCount() {
	return list.size();
}

@Override
public void onBindViewHolder(MyViewHolder holder, int arg1) {
	Data data = list.get(arg1);
	holder.name.setText(data.name);
	holder.phone = data.phoneno;
	holder.var.setText(data.var);
	holder.var1.setText(data.var1);
	Picasso.with(context).load(data.img_url).into(holder.iv);
	holder.img_url = data.img_url;
//	if(arg1 == 0){
//		holder.iv.setImageResource(R.drawable.mayank);
//	}
//	else if(arg1 == 1){
//		holder.iv.setImageResource(R.drawable.pp);
//	}
}

@Override
public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
	View view = inflater.inflate(R.layout.cust_rec_student, arg0, false);
	MyViewHolder holder = new MyViewHolder(view);
	return holder;
}
@SuppressLint("NewApi")
class MyViewHolder extends RecyclerView.ViewHolder{
	ImageView iv;
	TextView name,var,var1;
	Bundle bundle;
	FloatingActionButton fab;
	String phone,img_url;
	public MyViewHolder(View itemView) {
		super(itemView);
		iv = (ImageView) itemView.findViewById(R.id.imageRec);
		name = (TextView) itemView.findViewById(R.id.nameRec);
		var = (TextView) itemView.findViewById(R.id.varRec);
		var1 = (TextView) itemView.findViewById(R.id.varRec1);
		fab = (FloatingActionButton) itemView.findViewById(R.id.fab);
		Typeface typ = Typeface.createFromAsset(context.getAssets(), "robot_light.ttf");
		Typeface typ2 = Typeface.createFromAsset(context.getAssets(), "robot_condensed_light.ttf");
		name.setTypeface(typ);
		var.setTypeface(typ2);
		var1.setTypeface(typ2);
		fab.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:"+ phone));
				context.startActivity(intent);				
			}
		});
		itemView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				bundle =  ActivityOptions.makeSceneTransitionAnimation((Activity) context,iv,iv.getTransitionName()).toBundle();
				Intent intent = new Intent(context,StudentDetailActivity.class);
				intent.putExtra("name", name.getText().toString());
				intent.putExtra("img_url", img_url);
				context.startActivity(intent, bundle);
				
			}
		});
	}
	
}

}
