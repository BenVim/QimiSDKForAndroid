package com.ismole.qimisdk;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.ismole.qimisdk.QimiLoginView.UserLoginTask;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button loginBtn;
    private Button registerBtn;
    private Button rechargetCenterBtn;
    private Button mloginGoBtn;
    private int serverID = 15;
    private UserLoginTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        String key = this.getString(R.string.sign);
        int sId = 15;
        int appId = Integer.parseInt(this.getString(R.string.appID));
        
        QimiPlatform.getInstance().setM_sId(sId);
        QimiPlatform.getInstance().setM_appId(appId);
        QimiPlatform.getInstance().setM_appKey(key);

        loginBtn = (Button)this.findViewById(R.id.button);
        loginBtn.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {
                Intent itent=new Intent();
                itent.setClass(MainActivity.this, QimiLoginView.class);
                startActivity(itent);
                //Pay.this.finish();
            }
        });

        registerBtn = (Button)this.findViewById(R.id.button2);
        registerBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent itent=new Intent();
                itent.setClass(MainActivity.this, QimiRegisterView.class);
                startActivity(itent);
            }
        });

        rechargetCenterBtn = (Button)findViewById(R.id.button3);
        rechargetCenterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itent=new Intent();
                itent.putExtra("money", 10.5f);
                itent.setClass(MainActivity.this, QimiRechargeCenter.class);
                startActivity(itent);
            }
        });
        
        mloginGoBtn = (Button)this.findViewById(R.id.loginGoBtn);
        mloginGoBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				logining();
			}
		});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private void logining()
    {
    	mAuthTask = new UserLoginTask();
        mAuthTask.execute((Void) null);
    }
    
    private String getSign()
    {
        String m_appid = getString(R.string.appID);
        String m_email = "41284739@qq.com";
        String m_pass  = "123456";

        String sign ="appid="+m_appid+"do=loginemail="+m_email+"mod=Userpassword="+m_pass+getString(R.string.sign);
        QimiMD5 md5 = new QimiMD5();
        return md5.getMD5ofStr(sign).toLowerCase();
    }
    
    private void onLoginSucssful(String strResult)
    {
        QimiUserModel qimiUserModel = new QimiUserModel();
        qimiUserModel.initData(strResult);
        QimiPlatform.getInstance().setM_userModel(qimiUserModel);
    }
    
    public class UserLoginTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

        	String u = "41284739@qq.com";
            String p = "123456";
            String m = getSign();
            try{
                String httpUrl = getString(R.string.api);
                HttpPost httpRequest = new HttpPost(httpUrl);
                List<NameValuePair> mData = new ArrayList<NameValuePair>();
                mData.add(new BasicNameValuePair("mod", "User"));
                mData.add(new BasicNameValuePair("do", "login"));
                mData.add(new BasicNameValuePair("appid", getString(R.string.appID).toString()));
                mData.add(new BasicNameValuePair("email", u));
                mData.add(new BasicNameValuePair("password", p));
                mData.add(new BasicNameValuePair("sign", m));

                HttpEntity httpentity = new UrlEncodedFormEntity(mData, "UTF-8");
                httpRequest.setEntity(httpentity);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse httpResponse = httpclient.execute(httpRequest);
                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                {
                    String strResult = EntityUtils.toString(httpResponse.getEntity());
                    return strResult;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return "0";
        }

        @Override
        protected void onPostExecute(final String strResult) {
            mAuthTask = null;
            //showProgress(false);
            try
            {
                JSONObject json = new JSONObject(strResult);
                String s = json.get("status").toString();
                if (s.equals("100"))
                {
                    onLoginSucssful(strResult);
                    Dialog alertDialog = new AlertDialog.Builder(MainActivity.this).
                            setTitle(s).
                            setMessage(getString(R.string.login_sucssful)).
                            setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    
                                    dialog.dismiss();
                                }
                            }).create();
                    alertDialog.show();
                }
                else
                {
                    String msg = json.getString("error");
                    Dialog alertDialog = new AlertDialog.Builder(MainActivity.this).
                            setTitle(s).
                            setMessage(msg).
                            setPositiveButton(getString(R.string.ok), null).create();
                    alertDialog.show();
                }
            }catch (Exception e)
            {

            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            //showProgress(false);
        }
    }
    
}
