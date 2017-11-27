package http_requests;

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

import com.branchapp.HomeActivityStudent;
import com.branchapp.HomeActivityTeacher;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class HttpRequest_AsyncTask_Login extends AsyncTask<Void, Void, String> {

	private String username, password, mUrl;
	private Context context;
	private ProgressDialog pd;

	/** Constructor */

	public HttpRequest_AsyncTask_Login(String username, String password,
			Context context, String mUrl) {
		this.username = username;
		this.password = password;
		this.context = context;
		this.mUrl = mUrl;
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
		} else {
			Toast.makeText(context, "Username and Password doesnt match!!",
					Toast.LENGTH_SHORT).show();
		}

	}

	/** Helper Method For Sending Http Requests */

	private String sendHttpRequest() throws IOException {
		String result = null;
		HttpClient hc = new DefaultHttpClient();
		HttpPost hp = new HttpPost(mUrl);
		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("roll_no", username));
		nvp.add(new BasicNameValuePair("password", password));
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
