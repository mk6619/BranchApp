package com.branchapp;

import http_requests.Http_Url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class Fragment_SendNoti extends Fragment {
	private EditText et;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_send_notification,
				container, false);
		//TextView tv = (TextView) view.findViewById(R.id.sendnoti);
		Typeface typ = Typeface.createFromAsset(getActivity().getAssets(),
				"robot_light.ttf");
		et = (EditText) view.findViewById(R.id.text_noti);
		et.setTypeface(typ);
		//tv.setTypeface(typ);
		FloatingActionButton button = (FloatingActionButton) view
				.findViewById(R.id.fabsend);
		button.setOnClickListener(new OnClickListener() {

			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View arg0) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String currentDateandTime = sdf.format(new Date());
				new HttpRequest_AsyncTask_Login(getActivity(),
						Http_Url.INSERT_NOTIFICATION_URL, currentDateandTime,
						et.getText().toString(), getActivity().getIntent()
								.getStringExtra("username")).execute();
			}
		});
		return view;
	}

	public class HttpRequest_AsyncTask_Login extends
			AsyncTask<Void, Void, String> {

		private String mUrl, date, data, username;
		private Context context;
		private ProgressDialog pd;

		/** Constructor */

		public HttpRequest_AsyncTask_Login(Context context, String mUrl,
				String date, String data, String username) {
			this.context = context;
			this.mUrl = mUrl;
			this.data = data;
			this.date = date;
			this.username = username;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = ProgressDialog.show(context, "Wait", "Inserting Data");
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
			if (Integer.parseInt(result.trim()) == 1) {
				Toast.makeText(getActivity(), "Successfully Inserted",
						Toast.LENGTH_SHORT).show();
			} else
				Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT)
						.show();
		}

		/** Helper Method For Sending Http Requests */

		private String sendHttpRequest() throws IOException {
			String result = null;
			HttpClient hc = new DefaultHttpClient();
			HttpPost hp = new HttpPost(mUrl);
			List<NameValuePair> nvp = new ArrayList<NameValuePair>();
			nvp.add(new BasicNameValuePair("date", date));
			nvp.add(new BasicNameValuePair("data", data));
			nvp.add(new BasicNameValuePair("send_by", username));
			hp.setEntity(new UrlEncodedFormEntity(nvp));

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
