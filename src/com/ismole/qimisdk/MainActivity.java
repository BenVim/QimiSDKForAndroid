package com.ismole.qimisdk;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button loginBtn;
    private Button registerBtn;
    private Button rechargetCenterBtn;
    private Button mloginGoBtn;
    private int serverID = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
				Intent itent=new Intent();
                itent.putExtra("money", 10.5f);
                itent.setClass(MainActivity.this, QimiLoginView.class);
                startActivity(itent);
			}
		});

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
