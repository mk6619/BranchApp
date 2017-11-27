package http_requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
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

import android.content.AsyncTaskLoader;
import android.content.Context;

public class HttpRequest extends AsyncTaskLoader<String> {

	private String mUrl;
	private String type_data;
	private int status_code;

	public HttpRequest(Context context, String url, String type_data,
			int status_code) throws MalformedURLException {
		super(context);
		mUrl = url;
		this.type_data = type_data;
		this.status_code = status_code;
	}

	/**
	 * Helper Method to send http requests
	 * 
	 * @throws IOException
	 */

	private String sendHttpRequest() throws IOException {
		String result = null;
		HttpClient hc = new DefaultHttpClient();
		HttpPost hp = new HttpPost(mUrl);
		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("type_data", type_data));
		try {
			if (status_code == 1)
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

	@Override
	public String loadInBackground() {
		try {
			return sendHttpRequest();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
