package utils.cryption;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

public class Pkcs7Encoder {

    // 算法名称
    private static final String ALGORITHM_NAME = "AES";

    // 加解密算法/模式/填充方式
    private static final String KEY_ALGORITHM = "AES/CBC/PKCS7Padding";
    private static Key key;
    private static Cipher cipher;

    // 默认对称解密算法初始向量 iv
    private static byte[] iv = {0x30, 0x31, 0x30, 0x32, 0x30, 0x33, 0x30, 0x34, 0x30, 0x35, 0x30, 0x36, 0x30, 0x37, 0x30, 0x38};

    public static void init(byte[] keyBytes) {
        int base = 16;

        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }

        Security.addProvider(new BouncyCastleProvider());
        key = new SecretKeySpec(keyBytes, ALGORITHM_NAME);
        try {
            cipher = Cipher.getInstance(KEY_ALGORITHM, "BC");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密方法 -- 使用默认iv时
     *
     * @param content  要加密的字符串
     * @param keyBytes 加密密钥
     * @return
     */
    public static byte[] encrypt(byte[] content, byte[] keyBytes) {
        byte[] encryptedText = encryptOfDiyIV(content, keyBytes, iv);
        return encryptedText;
    }


    /**
     * 解密方法--使用默认iv时
     *
     * @param encryptedData 要解密的字符串
     * @param keyBytes      解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] encryptedData, byte[] keyBytes) {
        byte[] encryptedText = decryptOfDiyIV(encryptedData, keyBytes, iv);
        return encryptedText;
    }


    /**
     * 加密方法 --- 自定义对称解密算法初始向量 iv
     *
     * @param content  要加密的字符串
     * @param keyBytes 加密密钥
     * @param ivs      自定义对称解密算法初始向量 iv
     * @return
     */
    public static byte[] encryptOfDiyIV(byte[] content, byte[] keyBytes, byte[] ivs) {
        byte[] encryptedText = null;
        init(keyBytes);

        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivs));
            encryptedText = cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedText;
    }

    /**
     * 解密方法
     *
     * @param encryptedData 要解密的字符串
     * @param keyBytes      解密密钥
     * @param ivs           自定义对称解密算法初始向量 iv
     * @return
     */
    public static byte[] decryptOfDiyIV(byte[] encryptedData, byte[] keyBytes, byte[] ivs) {
        byte[] encryptedText = null;
        init(keyBytes);

        try {
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivs));
            encryptedText = cipher.doFinal(encryptedData);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encryptedText;
    }

}
