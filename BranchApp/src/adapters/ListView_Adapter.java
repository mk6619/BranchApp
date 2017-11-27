package adapters;


import com.branchapp.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListView_Adapter extends BaseAdapter{
	String items[];
	Context c;
	LayoutInflater inflater;
	public ListView_Adapter(String items[],Context c) {
		this.items = items;
		this.c = c;
		inflater =(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.length;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = arg1;
		view= inflater.inflate(R.layout.cust_list, null);
		TextView name;
		ImageView iv;
		name = (TextView) view.findViewById(R.id.listtext);
		iv = (ImageView) view.findViewById(R.id.listimage);
		String item = items[arg0];
		name.setText(item);
		switch(arg0){
		case 0:
			iv.setImageResource(R.drawable.ic_home_black_24dp);
			break;
		case 1:
			iv.setImageResource(R.drawable.ic_directions_bike_black_24dp);
			break;
		case 2:
			iv.setImageResource(R.drawable.ic_group_black_24dp);
			break;
		case 3:
			iv.setImageResource(R.drawable.ic_local_post_office_black_24dp);
			break;
		}
		return view;
	}

}
