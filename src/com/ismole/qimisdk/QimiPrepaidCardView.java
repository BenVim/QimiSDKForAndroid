package com.ismole.qimisdk;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * 直付通支付模块
 * Created by Ben on 7/16/13.
 */
public class QimiPrepaidCardView extends Activity {
	
    private Spinner mSpinner;
    private Button mbackBtn;
    private Button mHelpBtn;
    private Button mSubmit;
    
    private Button mB10Btn;
    private Button mB20Btn;
    private Button mB30Btn;
    private Button mB50Btn;
    private Button mB100Btn;
    private Button mB300Btn;
    private Button mB500Btn;
    private EditText mInputMoney;
    private EditText mInputCardNum;
    private EditText mInputCardPass;
    
    private float m_money;
    private ArrayAdapter<CharSequence> mAdapter;
    private int cardMonoeyList[]={ 10, 20, 30, 50, 100, 300, 500};//面额列表
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepaidcard_view);

        initView();
    }
    
    private void initView()
    {
    	mSpinner = (Spinner)findViewById(R.id.spinner);
        mAdapter = ArrayAdapter.createFromResource(
                this, R.array.countries, android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);
        
        mbackBtn = (Button) this.findViewById(R.id.backBtn);
        mHelpBtn = (Button) this.findViewById(R.id.helpBtn);
        mSubmit  = (Button) this.findViewById(R.id.submit);
        
        mB10Btn  = (Button) this.findViewById(R.id.b10Btn);
        mB20Btn  = (Button) this.findViewById(R.id.b20Btn);
        mB30Btn  = (Button) this.findViewById(R.id.b30Btn);
        mB50Btn  = (Button) this.findViewById(R.id.b50Btn);
        mB100Btn = (Button) this.findViewById(R.id.b100Btn);
        mB300Btn = (Button) this.findViewById(R.id.b300Btn);
        mB500Btn = (Button) this.findViewById(R.id.b500Btn);
        mInputMoney   = (EditText) this.findViewById(R.id.moneyEdit);
        mInputCardNum = (EditText) this.findViewById(R.id.cardnumEdit);
        mInputCardPass= (EditText) this.findViewById(R.id.cardpassEdit);
        
        addListen();
    }
    
    private void addListen()
    {
    	mbackBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
    	
    	mHelpBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	mB10Btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				update(10);
			}
		});
    	
    	mB20Btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				update(20);
			}
		});
    	
    	mB30Btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				update(30);
			}
		});
    	
    	mB50Btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				update(50);
			}
		});
    	
    	mB100Btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				update(100);
			}
		});
    	
    	mB300Btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				update(300);
			}
		});
    	
    	mB500Btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				update(500);
			}
		});
    	
//    	mInputMoney.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//			
//			@Override
//			public void onFocusChange(View arg0, boolean arg1) {
//				// TODO Auto-generated method stub
//				Log.e("d",mInputMoney.getText().toString());
//                m_money = Float.parseFloat(mInputMoney.getText().toString());
//                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(mInputMoney.getWindowToken(), 0);
//                //updateView(m_money);
//			}
//		});
    	
    	mInputMoney.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                //
                Log.e("d",mInputMoney.getText().toString());
                m_money = Float.parseFloat(mInputMoney.getText().toString());
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mInputMoney.getWindowToken(), 0);
                //update();
            }
        });
    	
    	
    	mSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());
    }
    
    //使用数组形式操作  
    class SpinnerSelectedListener implements OnItemSelectedListener{  
  
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,  
                long arg3) {  
        	//Log.e("dddd", mAdapter.getItem(arg2).toString());
        	Log.e("mAdapter_select_index", arg2+"");
        	int carMoney = cardMonoeyList[arg2];
            //view.setText("你的血型是："+m[arg2]);
        }
  
        public void onNothingSelected(AdapterView<?> arg0) { 
        	
        }  
    }
    
    private void update(float value)
    {
    	mInputMoney.setText(value+"");
    	m_money = value;
    }

    public void showToast()
    {
    	
    }
}