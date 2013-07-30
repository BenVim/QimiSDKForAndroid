package com.ismole.qimisdk;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
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

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Ben on 7/16/13.
 */
public class QimiLoginView extends Activity {

    private Button loginBtn;
    private Button registerBtn;
    private EditText userName;
    private EditText userPass;
    private View mLoginStatusView;
    private View mLoginFormView;
    private String mEmail;
    private String mPassword;
    private TextView mLoginStatusMessageView;
    private Boolean isRemeber;
    private UserLoginTask mAuthTask = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        isRemeber =true;
        init();
    }

    private void init()
    {
        loginBtn    = (Button)this.findViewById(R.id.loginBtn);
        registerBtn = (Button)this.findViewById(R.id.registerBtn);
        userName    = (EditText)this.findViewById(R.id.userName);
        userPass    = (EditText)this.findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.login_form);
        mLoginStatusView = findViewById(R.id.login_status);
        mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view)
            {
                //
                attemptLogin();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itent=new Intent();
                itent.setClass(QimiLoginView.this, QimiRegisterView.class);
                startActivity(itent);
            }
        });


        userName.setOnTouchListener(new EditText.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()== MotionEvent.ACTION_DOWN) {
                    String str = userName.getText().toString();
                    if (str.equals(getString(R.string.username)))
                    {
                        userName.setText("");
                    }
                }

                return false;
            }
        });

       userPass.setOnTouchListener(new EditText.OnTouchListener(){
           @Override
           public boolean onTouch(View v, MotionEvent event)
           {
               if (event.getAction() == MotionEvent.ACTION_DOWN)
               {
                   String str = userPass.getText().toString();
                   if (str.equals(getString(R.string.userpass)))
                   {
                       userPass.setText("");
                   }
               }
               return false;
           }
       });
    }

    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        userName.setError(null);
        userPass.setError(null);

        // Store values at the time of the login attempt.
        mEmail = userName.getText().toString();
        mPassword = userPass.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(mPassword)) {
            userPass.setError(getString(R.string.error_field_required));
            focusView = userPass;
            cancel = true;
        } else if (mPassword.length() < 4) {
            userPass.setError(getString(R.string.error_invalid_password));
            focusView = userPass;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mEmail)) {
            userName.setError(getString(R.string.error_field_required));
            focusView = userName;
            cancel = true;
        } else if (!mEmail.contains("@")) {
            userName.setError(getString(R.string.error_invalid_email));
            focusView = userName;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();



        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
            showProgress(true);
            mAuthTask = new UserLoginTask();
            mAuthTask.execute((Void) null);
        }
    }


    private String getSign()
    {
        String m_appid = getString(R.string.appID);
//        String m_mod   = "User";
//        String m_do    = "login";
        String m_email = userName.getText().toString();
        String m_pass  = userPass.getText().toString();

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

    private void dispose()
    {
        this.finish();
    }


    public class UserLoginTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            String u = userName.getText().toString();
            String p = userPass.getText().toString();
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
            showProgress(false);

                try
                {
                    JSONObject json = new JSONObject(strResult);
                    String s = json.get("status").toString();
                    if (s.equals("100"))
                    {
                        onLoginSucssful(strResult);
                        Dialog alertDialog = new AlertDialog.Builder(QimiLoginView.this).
                                setTitle(s).
                                setMessage(getString(R.string.login_sucssful)).
                                setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dispose();
                                        dialog.dismiss();
                                    }
                                }).create();
                        alertDialog.show();
                    }
                    else
                    {
                        String msg = json.getString("error");
                        Dialog alertDialog = new AlertDialog.Builder(QimiLoginView.this).
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(
                    android.R.integer.config_shortAnimTime);

            mLoginStatusView.setVisibility(View.VISIBLE);
            mLoginStatusView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginStatusView.setVisibility(show ? View.VISIBLE
                                    : View.GONE);
                        }
                    });

            mLoginFormView.setVisibility(View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mLoginFormView.setVisibility(show ? View.GONE
                                    : View.VISIBLE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


}
