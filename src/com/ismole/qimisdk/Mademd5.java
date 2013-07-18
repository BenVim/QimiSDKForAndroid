

package com.ismole.qimisdk;

/**
 Mademd5 mad=new Mademd5();
 System.out.println(mad.toMd5("0.0"));
 System.out.println(mad.toMd5(0.0D));
 System.out.println(mad.toMd5(0.0F));
 System.out.println(mad.toMd5(1));

 */


public class Mademd5
{
    public String toMd5(String sg)
    {
        return strTO(sg);
    }

    public  String toMd5(String sg1,String sg2)
    {
        return strTo(sg1,sg2);
    }

    public String toMd5(Integer itg)
    {
        return intTo(itg);
    }

    public String toMd5(Double dble)
    {
        return doubleTo(dble);
    }

    public String toMd5(Float flt)
    {
        return floatTo(flt);
    }

    public String toMd5(Long lng)
    {
        return longTo(lng);
    }
    
    private static String _md5(String str)
    {

        MD5 md5=new MD5();
        return (md5.getMD5ofStr(str));
    }
   

    private static String strTO(String sg)
    {
        return _md5(sg);
    }
    private static String strTo(String sg1,String sg2)
    {
        return _md5((sg1+sg2).toString());
    }
    private static String intTo(Integer itg)
    {
        return _md5(itg.toString());
    }
    private static String doubleTo(Double dble)
    {
        return _md5(dble.toString());
    }
    private static String floatTo(Float flt)
    {
        return _md5(flt.toString());
    }
    private static String longTo(Long lng)
    {
        return _md5(lng.toString());
    }
}
