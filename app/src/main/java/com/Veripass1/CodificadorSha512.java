package com.Veripass1;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 
import android.util.Log;

public class CodificadorSha512 {

	
	public static final String SHA512 = "SHA512";
	 
    public static String getEncoded(String texto) throws NoSuchAlgorithmException {
        MessageDigest md;
        String output = "";
        try {
            md= MessageDigest.getInstance(SHA512);
            md.update(texto.getBytes());
            byte[] mb = md.digest();
            for (int i = 0; i < mb.length; i++) {
                byte temp = mb[i];
                String s = Integer.toHexString(new Byte(temp));
                while (s.length() < 2) {
                    s = "0" + s;
                }
                s = s.substring(s.length() - 2);
                output += s;
            }
        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        Log.d("SHA","Output: "+output);
        return output;
    }
	
	
}
