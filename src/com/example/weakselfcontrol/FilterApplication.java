package com.example.weakselfcontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FilterApplication  extends Activity implements OnClickListener{

	private Button btthirdapp; // ������Ӧ�ó���
	private Button btsystemapp;// ϵͳ����
	
	private int filter = MainActivity.FILTER_THIRD_APP; 
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// ��ʼ���ؼ�������

		btthirdapp = (Button) findViewById(R.id.btthirdapp);
		btsystemapp = (Button) findViewById(R.id.btsystemapp);
		btthirdapp.setOnClickListener(this);
		btsystemapp.setOnClickListener(this);
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		System.out.println(""+view.getId());
		switch(view.getId()){

		case R.id.btthirdapp:
			filter = MainActivity.FILTER_THIRD_APP ;
			break ;
		case R.id.btsystemapp:
			filter = MainActivity.FILTER_SYSTEM_APP ;
			break ;
		}
		Intent intent = new Intent(getBaseContext(),MainActivity.class) ;
		intent.putExtra("filter", filter) ;
		startActivity(intent);
	}

}
