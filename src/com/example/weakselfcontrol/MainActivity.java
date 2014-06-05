package com.example.weakselfcontrol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity  {

	public static final int FILTER_THIRD_APP = 0; // ������Ӧ�ó���
	public static final int FILTER_SYSTEM_APP = 1; // ϵͳ����

	private ListView listview = null;

	private PackageManager pm;
	private int filter = 0; 
	private List<AppInfo> mlistAppInfo ;
	private BrowseApplicationInfoAdapter browseAppAdapter = null ;
	//private Button listButton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse_app_list);
		
		listview = (ListView) findViewById(R.id.listviewApp);
		
		if(getIntent()!=null){
			filter = getIntent().getIntExtra("filter", 0) ;
		}
		mlistAppInfo = queryFilterAppInfo(filter); // ��ѯ����Ӧ�ó�����Ϣ
		// ����������������ע�ᵽlistView
		browseAppAdapter = new BrowseApplicationInfoAdapter(this, mlistAppInfo);
		listview.setAdapter(browseAppAdapter);
		listview.setItemsCanFocus(false);
		listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//ViewHolder vHolder = (ViewHolder) arg1.getTag();
				//��ÿ�λ�ȡ�����itemʱ������Ӧ��checkbox״̬�ı䣬ͬʱ�޸�map��ֵ
				//vHolder.cheBox.toggle();
				//browseAppAdapter.isSelected.put(arg2, vHolder.cheBox.isChecked());
				String str1 = (String) ((TextView) arg1.findViewById(R.id.tvAppLabel)).getText();
				String str2 = (String) ((TextView) arg1.findViewById(R.id.tvPkgName)).getText();
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, Settings.class);
				Bundle bundle = new Bundle();
				bundle.putString("classname", str1);
				intent.putExtras(bundle);
				bundle.putString("packagename", str2);
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
			}
		});
		
		/*listButton = (Button)findViewById(R.id.listButton);
		listButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for(int i=0;i<listview.getCount();i++){
					if(browseAppAdapter.isSelected.get(i)){
						//System.out.println(listview.getTag(i));
						ViewHolder vHolder = (ViewHolder) listview.getChildAt(i).getTag();
						Toast.makeText(MainActivity.this, vHolder.tvAppLabel.getText(),Toast.LENGTH_SHORT).show();
					}
				}
			}
		});*/
	}
	// ���ݲ�ѯ��������ѯ�ض���ApplicationInfo
	private List<AppInfo> queryFilterAppInfo(int filter) {
		pm = this.getPackageManager();
		// ��ѯ�����Ѿ���װ��Ӧ�ó���
		List<ApplicationInfo> listAppcations = pm
				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
		Collections.sort(listAppcations,
				new ApplicationInfo.DisplayNameComparator(pm));// ����
		List<AppInfo> appInfos = new ArrayList<AppInfo>(); // ������˲鵽��AppInfo
		// ��������������
		switch (filter) {
		case FILTER_THIRD_APP: // ������Ӧ�ó���
			appInfos.clear();
			for (ApplicationInfo app : listAppcations) {
				if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
					appInfos.add(getAppInfo(app));
				}
			}
			break;
		case FILTER_SYSTEM_APP: // ϵͳ����
			appInfos.clear();
			for (ApplicationInfo app : listAppcations) {
				if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
					appInfos.add(getAppInfo(app));
				}
			}
			return appInfos;
		default:
			return null;
		}
		return appInfos;
	}
	// ����һ��AppInfo���� ������ֵ
	private AppInfo getAppInfo(ApplicationInfo app) {
		AppInfo appInfo = new AppInfo();
		appInfo.setAppLabel((String) app.loadLabel(pm));
		appInfo.setAppIcon(app.loadIcon(pm));
		appInfo.setPkgName(app.packageName);
		return appInfo;
	}
}