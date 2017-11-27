package com.branchapp;

import http_requests.Http_Url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapters.NotificationData;
import adapters.Notification_Adapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_Notifications extends Fragment{
	private List<NotificationData> list;
	private Notification_Adapter man;
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.frag_notifications, container, false);
	RecyclerView rec  = (RecyclerView) view.findViewById(R.id.recnot);
	list = new ArrayList<NotificationData>();
	new HttpRequest_AsyncTask_Login(getActivity(), Http_Url.FETCH_NOTIFICATION_URL).execute();
	man = new Notification_Adapter(getActivity(), list); 

	rec.setAdapter(man);
	rec.setLayoutManager(new LinearLayoutManager(getActivity()));

	return view;
}

/** Helper Method to fetch json */

private void json_fetch_students(String result) throws JSONException {
	JSONArray jA = new JSONArray(result);
	for (int i = 0; i < jA.length(); i++) {
		NotificationData data = new NotificationData();
		JSONObject obj = jA.getJSONObject(i);
		data.date = obj.getString("date");
		data.name = obj.getString("send_by");
		data.noti = obj.getString("data");
		list.add(data);
	}

}

public class HttpRequest_AsyncTask_Login extends
		AsyncTask<Void, Void, String> {

	private String mUrl;
	private Context context;
	private ProgressDialog pd;

	/** Constructor */

	public HttpRequest_AsyncTask_Login(Context context, String mUrl
			) {
		this.context = context;
		this.mUrl = mUrl;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pd = ProgressDialog.show(context, "Wait", "Loading Data");
	}

	@Override
	protected String doInBackground(Void... params) {
		try {
			return sendHttpRequest();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		pd.dismiss();
		try {
			json_fetch_students(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		man.notifyDataSetChanged();
	}

	/** Helper Method For Sending Http Requests */

	private String sendHttpRequest() throws IOException {
		String result = null;
		HttpClient hc = new DefaultHttpClient();
		HttpPost hp = new HttpPost(mUrl);
		try {

			HttpResponse response = hc.execute(hp);
			HttpEntity ent = response.getEntity();
			InputStream is = ent.getContent();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			result = sb.toString();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}

}

}
