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

import net.sourceforge.zbar.Symbol;

import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class QrCodeActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qr_code);
		Toast.makeText(this, getIntent().getStringExtra("id"), Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, ZBarScannerActivity.class);
		intent.putExtra(ZBarConstants.SCAN_MODES, new int[] { Symbol.QRCODE });
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg1 == RESULT_OK) {
			// Scan result is available by making a call to
			// data.getStringExtra(ZBarConstants.SCAN_RESULT)
			// Type of the scan result is available by making a call to
			// data.getStringExtra(ZBarConstants.SCAN_RESULT_TYPE)
			 Toast.makeText(this, "Scan Result = " +
			 arg2.getStringExtra(ZBarConstants.SCAN_RESULT),
			 Toast.LENGTH_SHORT).show();
			// Toast.makeText(this, "Scan Result Type = " +
			// arg2.getIntExtra(ZBarConstants.SCAN_RESULT_TYPE, 0),
			// Toast.LENGTH_SHORT).show();
			new HttpRequest_AsyncTask_Login(getIntent().getStringExtra("name"),
					arg2.getStringExtra(ZBarConstants.SCAN_RESULT),
					getIntent().getStringExtra("id"), getIntent()
							.getStringExtra("id"), this,
					Http_Url.INSERT_ATTENDANCE).execute();
			

			// The value of type indicates one of the symbols listed in Advanced
			// Options below.
		} else if (arg1 == RESULT_CANCELED) {
			Toast.makeText(this, "Camera unavailable", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public class HttpRequest_AsyncTask_Login extends
			AsyncTask<Void, Void, String> {

		private String username, teacher_id, course_id, mUrl, date;
		private Context context;
		private ProgressDialog pd;

		/** Constructor */

		public HttpRequest_AsyncTask_Login(String username, String date,
				String teacher_id, String course_id, Context context,
				String mUrl) {
			this.username = username;
			this.context = context;
			this.mUrl = mUrl;
			this.teacher_id = teacher_id;
			this.course_id = course_id;
			this.date = date;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = ProgressDialog.show(context, "Wait",
					"Checking Username and Password!!");
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
			Intent intent;
			if (Integer.parseInt(result.trim()) == 1) {
				intent = new Intent(context, HomeActivityStudent.class);
				intent.putExtra("username", username);
				context.startActivity(intent);
			} else if (Integer.parseInt(result.trim()) == 2) {
				intent = new Intent(context, HomeActivityTeacher.class);
				intent.putExtra("username", username);
				context.startActivity(intent);
			} 
		}

		/** Helper Method For Sending Http Requests */

		private String sendHttpRequest() throws IOException {
			String result = null;
			HttpClient hc = new DefaultHttpClient();
			HttpPost hp = new HttpPost(mUrl);
			List<NameValuePair> nvp = new ArrayList<NameValuePair>();
			nvp.add(new BasicNameValuePair("student_name", username));
			nvp.add(new BasicNameValuePair("present", "TRUE"));
			nvp.add(new BasicNameValuePair("course_id", course_id));
			nvp.add(new BasicNameValuePair("teacher_id", teacher_id));
			nvp.add(new BasicNameValuePair("date", date));
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
