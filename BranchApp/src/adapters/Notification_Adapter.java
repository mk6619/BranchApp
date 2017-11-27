package adapters;

import java.util.List;

import com.branchapp.R;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.MyViewHolder>{
	Context c;
	LayoutInflater inflater;
	List<NotificationData> list;
	public Notification_Adapter(Context c, List<NotificationData> list) {
		this.list = list;
		this.c = c;
		inflater = LayoutInflater.from(c);
	}
	
@Override
public int getItemCount() {
	// TODO Auto-generated method stub
	return list.size();
}

@Override
public void onBindViewHolder(MyViewHolder holder, int arg1) {
	NotificationData data = list.get(arg1);
	holder.date.setText(data.date);
	holder.name.setText(data.name);
	holder.noti.setText(data.noti);
}

@Override
public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
	View view = inflater.inflate(R.layout.cust_rec_notification, arg0, false);
	MyViewHolder holder = new MyViewHolder(view);
	return holder;
}
class MyViewHolder extends RecyclerView.ViewHolder{
	TextView name , noti , date;
	public MyViewHolder(View itemView) {
		super(itemView);
		name = (TextView) itemView.findViewById(R.id.recnotname);
		noti = (TextView) itemView.findViewById(R.id.recnotnot);
		date = (TextView) itemView.findViewById(R.id.recnotdate);
		Typeface font = Typeface.createFromAsset(c.getAssets(), "robot_condensed_light.ttf");
		date.setTypeface(font);
		Typeface font2 = Typeface.createFromAsset(c.getAssets(), "robot_light.ttf");
		noti.setTypeface(font2);
	}
	
}

}
