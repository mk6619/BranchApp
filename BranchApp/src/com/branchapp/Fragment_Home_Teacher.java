package com.branchapp;

import java.util.List;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import adapters.Course;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Fragment_Home_Teacher extends Fragment{
List<Course> list;
ImageView iv;
String qrCode;
EditText et;
	@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.frag_teacher_home, container, false);
	Button b = (Button) view.findViewById(R.id.generateqr);
	iv = (ImageView) view.findViewById(R.id.qrcodeimg);
	iv.setVisibility(View.INVISIBLE);
	et = (EditText) view.findViewById(R.id.qrdate);
	b.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
			 String qrData = et.getText().toString();
			    int qrCodeDimention = 500;

			    QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrData, null,
			            Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeDimention);

			    try {
			        Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
			        iv.setImageBitmap(bitmap);
			        iv.setVisibility(View.VISIBLE);
			    } catch (WriterException e) {
			        e.printStackTrace();
			    }


			
		}
	});
	return view;
}
}
