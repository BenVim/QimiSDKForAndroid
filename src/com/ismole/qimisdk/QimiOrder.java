package com.ismole.qimisdk;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

/**
 * 
 * 奇米支付宝订单显示
 * @author Ben
 *
 */
public class QimiOrder extends Activity{
	
	private RequestOrder mAuthTask;
	private float m_money;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qimi_order_view);
        
        Intent i = this.getIntent();
        m_money = i.getFloatExtra("money", 0);
        
        init();
    }
	
	private void init()
	{
		mAuthTask = new RequestOrder();
		mAuthTask.execute((Void) null);
	}
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

    }
	
	private String getSign(String uId, int sId)
    {
        String sign = uId+sId+getString(R.string.sign);
        QimiMD5 md5 = new QimiMD5();
        String m = md5.getMD5ofStr(sign);
        return m.toLowerCase(Locale.getDefault());
    }
	
	private void onLoginSucssful(String str)
	{
		try{
			JSONObject json = new JSONObject(str);
			String order = json.get("data").toString();
			
			Intent i = new Intent();
			i.putExtra("order", order);
			i.setClass(QimiOrder.this,	QimiAlipayView.class);
			this.setResult(RESULT_OK, i);
			this.finish();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
		
	/**********************************************/
	//请求订单数据
	public class RequestOrder extends AsyncTask<Void, Void, String> {
        @Override
        //uId=%s&sId=%d&sign=%s&money=%d&orderType=alipay&type=0
        protected String doInBackground(Void... params) {        	
        	String uId = QimiPlatform.getInstance().getM_userModel().getM_uId();
        	int sId = QimiPlatform.getInstance().getM_sId();
            String m = getSign(uId, sId);
            try{
                String httpUrl = getString(R.string.qimi_order_url);
                HttpPost httpRequest = new HttpPost(httpUrl);
                List<NameValuePair> mData = new ArrayList<NameValuePair>();
                mData.add(new BasicNameValuePair("uId", uId));
                mData.add(new BasicNameValuePair("sId", sId+""));
                mData.add(new BasicNameValuePair("type", "0"));
                mData.add(new BasicNameValuePair("money", m_money+""));
                mData.add(new BasicNameValuePair("orderType", "alipay"));
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
            showProgress(false);

                try
                {
                    JSONObject json = new JSONObject(strResult);
                    String s = json.get("s").toString();
                    if (s.equals("0"))
                    {
                    	String msg = json.get("data").toString();
                        onLoginSucssful(strResult);
                    }
                    else
                    {
                        String msg = json.getString("data");
                        Dialog alertDialog = new AlertDialog.Builder(QimiOrder.this).
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
            showProgress(false);
        }
    }
	/**********************************************/
}
    