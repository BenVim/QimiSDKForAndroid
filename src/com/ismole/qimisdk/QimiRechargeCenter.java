package com.ismole.qimisdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Qimi充值中心
 * Created by Ben on 7/16/13.
 */
public class QimiRechargeCenter extends Activity {

    private TextView mUserNameView;
    private TextView mMoneyView;
    private Button backBtn;
    private Button helpBtn;
    private ImageButton qimiImageBtn;
    private ImageButton alipayImageBtn;
    private ImageButton szImageBtn;
    private ImageButton ltImageBtn;
    private ImageButton dxImageBtn;
    private QimiUserModel mUserModel;
    private float m_money;//充值的金额

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechargecenter_view);
        Intent mIntent = this.getIntent();
        m_money =mIntent.getFloatExtra("money", 0);
        init();
    }

    private void init()
    {
        if (!QimiPlatform.getInstance().isLogined())
        {
            Intent itent=new Intent();
            itent.setClass(QimiRechargeCenter.this, QimiLoginView.class);
            startActivity(itent);
            finish();
            return;
        }

        mUserNameView = (TextView)findViewById(R.id.userName);
        mMoneyView    = (TextView)findViewById(R.id.moneyTxt);
        backBtn       = (Button)findViewById(R.id.backBtn);
        helpBtn       = (Button)findViewById(R.id.helpBtn);
        qimiImageBtn  = (ImageButton)findViewById(R.id.qimiBtn);
        alipayImageBtn= (ImageButton)findViewById(R.id.alipayBtn);
        szImageBtn    = (ImageButton)findViewById(R.id.szBtn);
        ltImageBtn    = (ImageButton)findViewById(R.id.ltBtn);
        dxImageBtn    = (ImageButton)findViewById(R.id.dxBtn);
        mUserModel    = QimiPlatform.getInstance().getM_userModel();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //help
            }
        });
        update();
        addLister();
    }

    private void update()
    {
        mUserNameView.setText(mUserModel.getM_email());
        mMoneyView.setText(m_money+"");
    }

    private void addLister()
    {
        qimiImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //qimi

            }
        });

        alipayImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //alipay
                Intent itent=new Intent();
                itent.putExtra("money", m_money);
                itent.setClass(QimiRechargeCenter.this, QimiAlipayView.class);
                startActivity(itent);
            }
        });

        szImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sz
                Intent itent=new Intent();
                itent.setClass(QimiRechargeCenter.this, QimiPrepaidCardView.class);
                startActivity(itent);
            }
        });

        ltImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lt
                Intent itent=new Intent();
                itent.setClass(QimiRechargeCenter.this, QimiPrepaidCardView.class);
                startActivity(itent);
            }
        });

        dxImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dx
                Intent itent=new Intent();
                itent.setClass(QimiRechargeCenter.this, QimiPrepaidCardView.class);
                startActivity(itent);
            }
        });
    }
}