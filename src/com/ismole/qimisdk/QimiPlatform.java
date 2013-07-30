package com.ismole.qimisdk;

/**
 * Qimi SDK MainClass
 * Created by Ben on 7/16/13.
 */
public class QimiPlatform {
    private static QimiPlatform instance = null;
    private int m_sId;
    private int m_appId;
    private String m_appKey;

    public boolean isLogined() {
        return isLogined;
    }

    private boolean isLogined = false;

    public QimiUserModel getM_userModel() {
        return m_userModel;
    }

    public void setM_userModel(QimiUserModel m_userModel) {
        isLogined = true;
        this.m_userModel = m_userModel;
    }

    private QimiUserModel m_userModel;

    public static QimiPlatform getInstance()
    {
        if (instance==null)
        {
            instance = new QimiPlatform();
        }
        return instance;
    }

	public String getM_appKey() {
		return m_appKey;
	}

	public void setM_appKey(String m_appKey) {
		this.m_appKey = m_appKey;
	}

	public int getM_appId() {
		return m_appId;
	}

	public void setM_appId(int m_appId) {
		this.m_appId = m_appId;
	}

	public int getM_sId() {
		return m_sId;
	}

	public void setM_sId(int m_sId) {
		this.m_sId = m_sId;
	}









}
