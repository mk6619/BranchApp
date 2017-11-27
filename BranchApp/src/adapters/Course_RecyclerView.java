package adapters;

import java.util.List;

import net.sourceforge.zbar.Symbol;

import com.branchapp.QrCodeActivity;
import com.branchapp.R;
import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class Course_RecyclerView extends
		RecyclerView.Adapter<Course_RecyclerView.MyViewHolder> {

	List<Course> list;
	Context c;
	LayoutInflater inflater;
	String name;
	public Course_RecyclerView(Context c , List<Course> list, String name) {
		this.list = list;
		this.c = c;
		inflater = LayoutInflater.from(c);
		this.name = name;
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int arg1) {
		Course course = list.get(arg1);
		holder.attendance.setText(course.attendance);
		holder.subject.setText(course.subject);
		holder.teacher.setText(course.teahcer);
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		View view = inflater.inflate(R.layout.cust_rec_course, arg0, false);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	public class MyViewHolder extends ViewHolder {
		TextView teacher, subject, attendance;

		public MyViewHolder(View itemView) {
			super(itemView);
			teacher = (TextView) itemView.findViewById(R.id.course_teacher);
			subject = (TextView) itemView.findViewById(R.id.course_subject);
			attendance = (TextView) itemView.findViewById(R.id.course_attend);
			Typeface typ1= Typeface.createFromAsset(c.getAssets(), "robot_light.ttf");
			Typeface typ2= Typeface.createFromAsset(c.getAssets(), "robot_condensed_light.ttf");
			subject.setTypeface(typ1);
			teacher.setTypeface(typ2);
			itemView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(c,QrCodeActivity.class);
					if(teacher.getText().toString().equals("Ashima"))
						intent.putExtra("id", "1");
					else
						intent.putExtra("id", "2");
					intent.putExtra("name", name);
					
					c.startActivity(intent);

				
				}
			});
		}

	}
}
