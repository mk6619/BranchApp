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
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import adapters.Course;
import adapters.Course_RecyclerView;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_Home extends Fragment {
	private List<Course> list;
	private Course_RecyclerView man;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_home, container, false);
		list = new ArrayList<Course>();
		new HttpRequest_AsyncTask_Login(getActivity(),
				Http_Url.FETCH_ATENDANCE_DETAILS_URL).execute();
		man = new Course_RecyclerView(getActivity(), list, getActivity().getIntent().getStringExtra("username"));
		RecyclerView rec = (RecyclerView) view.findViewById(R.id.rechome);
		rec.setAdapter(man);
		rec.setLayoutManager(new LinearLayoutManager(getActivity()));
		return view;
	}

	@SuppressLint("DefaultLocale")
	private void json_fetch_students(String result) throws JSONException {
		int course1_total = 0, course1_present = 0, course2_total = 0, course2_present = 0;

		JSONArray jA = new JSONArray(result);
		for (int i = 0; i < jA.length(); i++) {
			JSONObject obj = jA.getJSONObject(i);
			String present = obj.getString("present");
			int course_id = obj.getInt("course_id");
			if (present.equals("TRUE") && course_id == 1) {
				course1_present++;
			} else if (present.equals("TRUE") && course_id == 2) {
				course2_present++;
			}
			if(course_id == 1)
				course1_total++;
			else
				course2_total++;
		}
		Log.d("course1", String.valueOf(course1_present)+String.valueOf(course1_total));
		Log.d("course2", String.valueOf(course2_present)+String.valueOf(course2_total));
		Course data1 = new Course();
		Course data2 = new Course();
		data1.subject = "Software Engineering";
		data1.teahcer = "Ashima";
		data2.subject = "Operating Systems";
		data2.teahcer = "Tanupreet Bhatia";
		float att1 = (float)course1_present/course1_total;
		float att2  = (float)course2_present/course2_total;
		String value1 = String.format("%.02f", att1*100);
		String value2 = String.format("%.02f", att2*100);
		data1.attendance = value1+"%";
		data2.attendance = value2+"%";
		list.add(data1);
		list.add(data2);
	}

	public class HttpRequest_AsyncTask_Login extends
			AsyncTask<Void, Void, String> {

		private String mUrl;
		private Context context;
		private ProgressDialog pd;

		/** Constructor */

		public HttpRequest_AsyncTask_Login(Context context, String mUrl) {
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
				List<NameValuePair> nvp = new ArrayList<NameValuePair>();
				nvp.add(new BasicNameValuePair("username", getActivity().getIntent().getStringExtra("username")));
				hp.setEntity(new UrlEncodedFormEntity(nvp));
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
