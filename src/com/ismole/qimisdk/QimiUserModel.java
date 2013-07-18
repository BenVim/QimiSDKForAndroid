package com.ismole.qimisdk;

import org.json.JSONObject;

/**
 * 用户MDEL
 * Created by Ben on 7/16/13.
 */
public class QimiUserModel {


    public String getM_uId() {
        return m_uId;
    }

    public void setM_uId(String m_uId) {
        this.m_uId = m_uId;
    }

    public String getM_userName() {
        return m_userName;
    }

    public void setM_userName(String m_userName) {
        this.m_userName = m_userName;
    }

    public String getM_avatarURL() {
        return m_avatarURL;
    }

    public void setM_avatarURL(String m_avatarURL) {
        this.m_avatarURL = m_avatarURL;
    }

    public String getM_email() {
        return m_email;
    }

    public void setM_email(String m_email) {
        this.m_email = m_email;
    }

    public String getM_sessionKey() {
        return m_sessionKey;
    }

    public void setM_sessionKey(String m_sessionKey) {
        this.m_sessionKey = m_sessionKey;
    }

    public int getM_sex() {
        return m_sex;
    }

    public void setM_sex(int m_sex) {
        this.m_sex = m_sex;
    }

    public int getM_level() {
        return m_level;
    }

    public void setM_level(int m_level) {
        this.m_level = m_level;
    }

    public int getM_score() {
        return m_score;
    }

    public void setM_score(int m_score) {
        this.m_score = m_score;
    }

    public int getM_vipLevel() {
        return m_vipLevel;
    }

    public void setM_vipLevel(int m_vipLevel) {
        this.m_vipLevel = m_vipLevel;
    }

    public int getM_vipPrivilege() {
        return m_vipPrivilege;
    }

    public void setM_vipPrivilege(int m_vipPrivilege) {
        this.m_vipPrivilege = m_vipPrivilege;
    }

    public int getM_birthday() {
        return m_birthday;
    }

    public void setM_birthday(int m_birthday) {
        this.m_birthday = m_birthday;
    }

    public int getM_regdata() {
        return m_regdata;
    }

    public void setM_regdata(int m_regdata) {
        this.m_regdata = m_regdata;
    }

    public int getM_experience() {
        return m_experience;
    }

    public void setM_experience(int m_experience) {
        this.m_experience = m_experience;
    }

    private String m_uId;
    private String m_userName;
    private String m_avatarURL;
    private String m_email;
    private String m_sessionKey;

    private int m_sex;
    private int m_level;
    private int m_score;
    private int m_vipLevel;
    private int m_vipPrivilege;
    private int m_birthday;
    private int m_regdata;
    private int m_experience;

    QimiUserModel()
    {

    }

    public void initData(String strResult)
    {
        try
        {
            JSONObject root = new JSONObject(strResult);
            if (!root.isNull("data"))
            {
                JSONObject data = root.getJSONObject("data");
                m_uId = "0";
                m_sessionKey = data.getString("session_key");

                if (!data.isNull("user"))
                {
                    JSONObject UserData = data.getJSONObject("user");

                    m_uId = UserData.getString("uid");
                    m_userName = UserData.getString("name");
                    m_avatarURL = UserData.getString("avatar");
                    m_sex = UserData.getInt("sex");
                    m_email = UserData.getString("email");
                    m_level = UserData.getInt("level");
                    m_score = UserData.getInt("score");
                    m_vipLevel=UserData.getInt("vip_level");
                    m_vipPrivilege = UserData.getInt("vip_privilege");
                    m_birthday = UserData.getInt("birthday");
                    m_regdata = UserData.getInt("regdata");
                    m_experience = UserData.getInt("experience");
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }


}
