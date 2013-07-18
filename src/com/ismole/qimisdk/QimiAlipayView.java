package com.ismole.qimisdk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 支付宝
 * Created by Ben on 7/16/13.
 */
public class QimiAlipayView extends Activity {

    static String TAG = "Pay";
    private ProgressDialog mProgress = null;

    private Button mB10Btn;
    private Button mB20Btn;
    private Button mB30Btn;
    private Button mB50Btn;
    private Button mB100Btn;
    private Button mB300Btn;
    private Button mB500Btn;
    private Button mbackBtn;
    private Button mhelpBtn;
    private Button mSubmitBtn;
    private TextView mMcash;
    private EditText mInputMoneyEdit;

    private float m_money;
    private String m_parent;
    private String m_seller;
    private String m_private;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay_view);

        Intent i = getIntent();
        m_money = i.getFloatExtra("money", 0);

        init();
    }

    private void init()
    {
        mB10Btn = (Button)findViewById(R.id.b10Btn);
        mB20Btn = (Button)findViewById(R.id.b20Btn);
        mB30Btn = (Button)findViewById(R.id.b30Btn);
        mB50Btn = (Button)findViewById(R.id.b50Btn);
        mB100Btn = (Button)findViewById(R.id.b100Btn);
        mB300Btn = (Button)findViewById(R.id.b300Btn);
        mB500Btn = (Button)findViewById(R.id.b500Btn);
        mbackBtn     = (Button)findViewById(R.id.backBtn);
        mhelpBtn     = (Button)findViewById(R.id.helpBtn);
        mMcash       = (TextView)findViewById(R.id.mCashTxt);
        mInputMoneyEdit = (EditText)findViewById(R.id.editMoney);
        mSubmitBtn   = (Button)findViewById(R.id.submit);
        update();
        addList();
    }

    private void update()
    {
        mMcash.setText(m_money*10+"");
        mInputMoneyEdit.setText(m_money+"");
    }

    private void addList()
    {
        mbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //back;
                finish();
            }
        });

        mhelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //help;
            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //submit;
            	//alixPay();
            	/*
            	String order, 
    			String product, 
    			String productDes, 
    			String appScheme, 
    			String parent, 
    			String seller, 
    			String notifyUrl,
    			String alipayKey, 
    			float  price*/
            }
        });

        mB10Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //10
                m_money = 10;
                update();
            }
        });

        mB20Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //20
                m_money = 20;
                update();
            }
        });

        mB30Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //30
                m_money = 30;
                update();

            }
        });

        mB50Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //50
                m_money = 50;
                update();
            }

        });

        mB100Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //100
                m_money = 100;
                update();
            }
        });

        mB300Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //300
                m_money = 300;
                update();
            }
        });

        mB500Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //500
                m_money = 500;
                update();
            }
        });

        mInputMoneyEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                //

                Log.e("d",mInputMoneyEdit.getText().toString());
                m_money = Float.parseFloat(mInputMoneyEdit.getText().toString());

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mInputMoneyEdit.getWindowToken(), 0);


                update();
            }
        });
    }
    
    /**
     * request Order;
     */
    private void loadOrder()
    {
    	
    }

    public void alixPay(String order, 
			String product, 
			String productDes, 
			String appScheme, 
			String parent, 
			String seller, 
			String notifyUrl,
			String alipayKey, 
			float  price) {

		m_parent = parent;
		m_seller = seller;
		m_private = alipayKey;
		price = 0.01f;
		
		// check to see if the MobileSecurePay is already installed.
				// 检测安全支付服务是否安装
				MobileSecurePayHelper mspHelper = new MobileSecurePayHelper(this);
				boolean isMobile_spExist = mspHelper.detectMobile_sp();
				if (!isMobile_spExist)
					return;
				// check some info.
				// 检测配置信息
				if (!checkInfo()) {
					BaseHelper
							.showDialog(
									QimiAlipayView.this,
									"提示",
									"缺少partner或者seller，请在src/com/alipay/android/appDemo4/PartnerConfig.java中增加。",
									R.drawable.infoicon);
					return;
				}
				// start pay for this order.
				// 根据订单信息开始进行支付
				try {
					// prepare the order info.
					// 准备订单信息
					String orderInfo = "partner=" + "\"" + parent + "\"";
					orderInfo += "&";
					orderInfo += "seller=" + "\"" + seller + "\"";
					orderInfo += "&";
					orderInfo += "out_trade_no=" + "\"" + order + "\"";
					orderInfo += "&";
					orderInfo += "subject=" + "\"" + product + "\"";
					orderInfo += "&";
					orderInfo += "body=" + "\"" + productDes + "\"";
					orderInfo += "&";
					orderInfo += "total_fee=" + "\"" + price + "\"";
					orderInfo += "&";
					orderInfo += "notify_url=" + "\"" + notifyUrl + "\"";
					
					// 这里根据签名方式对订单信息进行签名
					String signType = getSignType();
					String strsign = sign(signType, orderInfo);
					Log.v("sign:", strsign);
					
					// 对签名进行编码
					strsign = URLEncoder.encode(strsign);
					// 组装好参数
					String info = orderInfo + "&sign=" + "\"" + strsign + "\"" + "&"
							+ getSignType();
					Log.v("orderInfo:", info);
					// start the pay.
					// 调用pay方法进行支付
					MobileSecurePayer msp = new MobileSecurePayer();
					boolean bRet = msp.pay(info, mHandler, AlixId.RQF_PAY, this);
					Log.e("TestLog", "Paying....");
					if (bRet) {
						// show the progress bar to indicate that we have started
						// paying.
						// 显示“正在支付”进度条
						closeProgress();
						mProgress = BaseHelper.showProgress(this, null, "正在支付", false, true);
					} else
						;
				} catch (Exception ex) {
					Toast.makeText(QimiAlipayView.this, R.string.remote_call_failed,
							Toast.LENGTH_SHORT).show();
				}
	}
	
	String getOrderInfo(int position) {
		String strOrderInfo = "partner=" + "\"" + PartnerConfig.PARTNER + "\"";
		return strOrderInfo;
	}
	
	/**
	 * get the out_trade_no for an order. 获取外部订单号
	 * 
	 * @return
	 */
	String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
		Date date = new Date();
		String strKey = format.format(date);

		java.util.Random r = new java.util.Random();
		strKey = strKey + r.nextInt();
		strKey = strKey.substring(0, 15);
		return strKey;
	}
	
	/**
	 * sign the order info. 对订单信息进行签名
	 */
	String sign(String signType, String content) {
		return Rsa.sign(content, this.m_private);
	}
	
	String getSignType() {
		String getSignType = "sign_type=" + "\"" + "RSA" + "\"";
		return getSignType;
	}
	/**
	 * get the char set we use. 获取字符集
	 */
	String getCharset() {
		String charset = "charset=" + "\"" + "utf-8" + "\"";
		return charset;
	}
	/**
	 * check some info.the partner,seller etc. 检测配置信息
	 */
	private boolean checkInfo() {
		String partner = this.m_parent;
		String seller = this.m_seller;
		if (partner == null || partner.length() <= 0 || seller == null
				|| seller.length() <= 0)
			return false;

		return true;
	}
	// 这里接收支付结果，支付宝手机端同步通知
		private Handler mHandler = new Handler() {
			public void handleMessage(Message msg) {
				try {
					String strRet = (String) msg.obj;

					Log.e(TAG, strRet);	// strRet范例：resultStatus={9000};memo={};result={partner="2088201564809153"&seller="2088201564809153"&out_trade_no="050917083121576"&subject="123456"&body="2010新款NIKE 耐克902第三代板鞋 耐克男女鞋 386201 白红"&total_fee="0.01"&notify_url="http://notify.java.jpxx.org/index.jsp"&success="true"&sign_type="RSA"&sign="d9pdkfy75G997NiPS1yZoYNCmtRbdOP0usZIMmKCCMVqbSG1P44ohvqMYRztrB6ErgEecIiPj9UldV5nSy9CrBVjV54rBGoT6VSUF/ufjJeCSuL510JwaRpHtRPeURS1LXnSrbwtdkDOktXubQKnIMg2W0PreT1mRXDSaeEECzc="}
					switch (msg.what) {
					case AlixId.RQF_PAY: {
						//
						closeProgress();

						BaseHelper.log(TAG, strRet);

						// 处理交易结果
						try {
							// 获取交易状态码，具体状态代码请参看文档
							String tradeStatus = "resultStatus={";
							int imemoStart = strRet.indexOf("resultStatus=");
							imemoStart += tradeStatus.length();
							int imemoEnd = strRet.indexOf("};memo=");
							tradeStatus = strRet.substring(imemoStart, imemoEnd);
							
							//先验签通知
							ResultChecker resultChecker = new ResultChecker(strRet);
							int retVal = resultChecker.checkSign();
							// 验签失败
							if (retVal == ResultChecker.RESULT_CHECK_SIGN_FAILED) {
								BaseHelper.showDialog(
										QimiAlipayView.this,
										"提示",
										getResources().getString(
												R.string.check_sign_failed),
										android.R.drawable.ic_dialog_alert);

							} else {// 验签成功。验签成功后再判断交易状态码
								if(tradeStatus.equals("9000"))//判断交易状态码，只有9000表示交易成功
									BaseHelper.showDialog(QimiAlipayView.this, "提示","支付成功。交易状态码："+tradeStatus, R.drawable.infoicon);
								else if (tradeStatus.equals("4000"))
								{
									BaseHelper.showDialog(QimiAlipayView.this, "提示","系统异常。交易状态码："+tradeStatus, R.drawable.infoicon);
								}else if(tradeStatus.equals("4001"))
								{
									BaseHelper.showDialog(QimiAlipayView.this, "提示","数据格式不正确。交易状态码："+tradeStatus, R.drawable.infoicon);
								}
								else if (tradeStatus.equals("4003"))
								{
									BaseHelper.showDialog(QimiAlipayView.this, "提示","该用户绑定的支付宝账户被冻结或不允许支付。交易状态码："+tradeStatus, R.drawable.infoicon);
								}
								else if (tradeStatus.equals("4004"))
								{
									BaseHelper.showDialog(QimiAlipayView.this, "提示","该用户已解除绑定。交易状态码："+tradeStatus, R.drawable.infoicon);
								}
								else if (tradeStatus.equals("4005"))
								{
									BaseHelper.showDialog(QimiAlipayView.this, "提示","绑定失败或没有绑定。交易状态码："+tradeStatus, R.drawable.infoicon);
								}
								else if (tradeStatus.equals("4006"))
								{
									BaseHelper.showDialog(QimiAlipayView.this, "提示","订单支付失败。交易状态码："+tradeStatus, R.drawable.infoicon);
								}
								else if (tradeStatus.equals("4010"))
								{
									BaseHelper.showDialog(QimiAlipayView.this, "提示","重新绑定账户。交易状态码："+tradeStatus, R.drawable.infoicon);
								}
								else if (tradeStatus.equals("6000"))
								{
									BaseHelper.showDialog(QimiAlipayView.this, "提示","支付服务正在进行升级操作。交易状态码："+tradeStatus, R.drawable.infoicon);
								}else if (tradeStatus.equals("6001"))
								{
									BaseHelper.showDialog(QimiAlipayView.this, "提示","用户中途取消支付操作。交易状态码："+tradeStatus, R.drawable.infoicon);
								}
								else if (tradeStatus.equals("6002"))
								{
									BaseHelper.showDialog(QimiAlipayView.this, "提示","网络连接异常。交易状态码："+tradeStatus, R.drawable.infoicon);
								}else 
								BaseHelper.showDialog(QimiAlipayView.this, "提示", "支付失败。交易状态码:"
										+ tradeStatus, R.drawable.infoicon);
							}

						} catch (Exception e) {
							e.printStackTrace();
							BaseHelper.showDialog(QimiAlipayView.this, "提示", strRet,
									R.drawable.infoicon);
						}
					}
						break;
					}

					super.handleMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
	
		/**
		 * the OnCancelListener for lephone platform. lephone系统使用到的取消dialog监听
		 */
		static class AlixOnCancelListener implements
				DialogInterface.OnCancelListener {
			Activity mcontext;

			AlixOnCancelListener(Activity context) {
				mcontext = context;
			}

			public void onCancel(DialogInterface dialog) {
				mcontext.onKeyDown(KeyEvent.KEYCODE_BACK, null);
			}
		}

		//
		// close the progress bar
		// 关闭进度框
		void closeProgress() {
			try {
				if (mProgress != null) {
					mProgress.dismiss();
					mProgress = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * 返回键监听事件
		 */
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				BaseHelper.log(TAG, "onKeyDown back");

				this.finish();
				return true;
			}

			return false;
		}

		//
		@Override
		public void onDestroy() {
			super.onDestroy();
			Log.v(TAG, "onDestroy");

			try {
				mProgress.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}

