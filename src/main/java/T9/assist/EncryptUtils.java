package T9.assist;


import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtils {
    private static String EncryptStr(String strSrc,String encName){
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try{
            if (encName == null || encName.equals("")){
                encName = "MD5";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = byte2Hex(md.digest());
        }catch (NoSuchAlgorithmException e){
            System.out.println("Invalid algorithm.");
            return null;
        }
        return strDes;
    }

    public static String EncryptByMD5(String str){
        return EncryptStr(str,"MD5");
    }

    public static String to_MD5(String s){
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9',
                            'a','b','c','d','e','f'};
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i=0;i<j;i++){
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }catch (Exception e){
            return null;
        }
    }

    public static String EncryptBySHA1(String str){
        return EncryptStr(str,"SHA-1");
    }

    public static String EncryptBySHA256(String str){
        return EncryptStr(str,"SHA-256");
    }

    private static String byte2Hex(byte[] bts){
        String des = "";
        String tmp = null;
        for (int i=0;i<bts.length;i++){
            tmp = Integer.toHexString(bts[i]);
            if (tmp.length() == 1){
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    public static String union(String str,String key){
        int strLen = str.length();
        int keyLen = key.length();
        Character[] s = new Character[strLen+keyLen];

        boolean flag = true;
        int strIdx = 0;
        int keyIdx = 0;
        for (int i=0;i<s.length;i++){
            if (flag){
                if (strIdx<strLen){
                    s[i] = str.charAt(strIdx);
                    strIdx++;
                }
                if (keyIdx<keyLen){
                    flag = false;
                }
            }else {
                if (keyIdx<keyLen){
                    s[i] = key.charAt(keyIdx);
                    keyIdx++;
                }
                if (strIdx<strLen){
                    flag = true;
                }
            }
        }
        return StringUtils.join(s);
    }

    public static String encrypt(String str,String key){
        if (str == null || str.length() == 0 || StringUtils.isBlank(key)){
            return encrypt(str);
        }
        return encrypt(union(str,key));
    }

    public static String encrypt(String str){
        String encryptStr = EncryptByMD5(str);
        if (encryptStr != null){
            encryptStr = encryptStr + encryptStr.charAt(0)
                    + encryptStr.charAt(2)+encryptStr.charAt(4);
            encryptStr = EncryptByMD5(encryptStr);
        }
        return encryptStr;
    }

}
