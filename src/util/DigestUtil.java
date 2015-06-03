package util;

import java.io.UnsupportedEncodingException;  
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;
import java.util.Set;
import java.util.HashSet;

public class DigestUtil {
	
    public static String hex(byte[] arr) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < arr.length; ++i) {  
            sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1,  
                    3));  
        }  
        return sb.toString();
    }
    
    
    /** 
     * md5或者sha-1加密 
     *  
     * @param inputText 
     *            要加密的内容 
     * @param algorithmName 
     *            加密算法名称：md5或者sha-1，不区分大小写 
     * @return 
     */  
    public static String digest(String inputText, String algorithmName) {  
        if (inputText == null || "".equals(inputText.trim())) {  
            throw new IllegalArgumentException("input the content");  
        }  
        if (algorithmName == null || "".equals(algorithmName.trim())) {  
            algorithmName = "md5";  
        }  
        String encryptText = null;  
        try {  
            MessageDigest m = MessageDigest.getInstance(algorithmName);  
            m.update(inputText.getBytes("UTF8"));  
            byte s[] = m.digest();   
            return hex(s);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return encryptText;  
    }  
    
    /**
     * filter the random set returned by all Key servers
     * @param RandomSet 
     * @return candidate randon numbers
     */
    public static Set<Double> intersaction(Set<Double> canrand, Set<Double> randomSet) {
    	Set<Double> result = new HashSet<Double>(canrand);
    	result.retainAll(randomSet);
    	return result;
    }

    
    
    /**
     * change double to byte[] value
     * @param d
     * @return
     */
    public static byte[] doubleToByte(double d) {
    	byte[] b = new byte[8];
    	long l = Double.doubleToLongBits(d);
    	for(int i=0;i<b.length;i++) {
    		b[i] = new Long(l).byteValue();
    		l=l>>8;
    	}
    	
    	return b;
    }
    
  /**
   * change byte array to double value
   * @param b
   * @return
   */
    public static double byteToDouble(byte[] b){  
      long l;  
    
      l=b[0];  
      l&=0xff;  
      l|=((long)b[1]<<8);  
      l&=0xffff;  
      l|=((long)b[2]<<16);  
      l&=0xffffff;  
      l|=((long)b[3]<<24);  
      l&=0xffffffffl;  
      l|=((long)b[4]<<32);  
      l&=0xffffffffffl;  
    
      l|=((long)b[5]<<40);  
      l&=0xffffffffffffl;  
      l|=((long)b[6]<<48);  
    
      l|=((long)b[7]<<56);  
      return Double.longBitsToDouble(l);  
    }
    
    //整数到字节数组的转换  
    public static byte[] intToByte(int number) {  
      int temp = number;  
      byte[] b=new byte[4];  
      for (int i=b.length-1; i>-1; i--) {  
        b[i] = new Integer(temp&0xff).byteValue();      //将最高位保存在最低位  
        temp = temp >> 8;       //向右移8位  
      }  
      return b;  
    }  
    
    //字节数组到整数的转换  
    public static int byteToInt(byte[] b) {  
      int s = 0;  
      for (int i = 0; i < 3; i++) {  
        if (b[i] >= 0)  
          s = s + b[i];  
        else  
    
          s = s + 256 + b[i];  
        s = s * 256;  
      }  
      if (b[3] >= 0)       //最后一个之所以不乘，是因为可能会溢出  
        s = s + b[3];  
      else  
        s = s + 256 + b[3];  
      return s;  
    }  
}
