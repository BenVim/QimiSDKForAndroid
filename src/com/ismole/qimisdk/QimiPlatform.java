package com.ismole.qimisdk;

/**
 * Qimi SDK MainClass
 * 奇米平台主类
 * Created by Ben on 7/16/13.
 */
public class QimiPlatform {
    private static QimiPlatform instance = null;

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









}
