package cn.ccsit.common.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
public class LicenseUtils {
    public static void main(String[] args) throws Exception {
        String str = "5294C4AE7890D068FCAF2010AE4064C2A91E05979A58ACE8E0F87C763467B86E1710B73E50A37817C46A6B1BD541702F26A4520975C4999DB3D8EB7ECD8BED4DE1A7E7C8A9CD2E417E1F238DEBCFE38DA65055D44C96C8C9D06B407241A42E151634EBDF8905450D99F2021011B32266FE75AD9E591D1AD88A5E80B714190EB213B3F70F74D895AC097FB4901BACE2B671CD69BAD076B6470D80C81511D5CB39DB51BB3A560F95E4D2363650884ED3FCCBC012EDC0E76A09";
        String a5483FA7 = decrypt(str, "A5483FA7");
        System.out.println(a5483FA7);
//        String str = "<?xml version=\"1.0\"?>\n" +
//                "<license xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
//                "  <type>S</type>\n" +
//                "  <key>9636084</key>\n" +
//                "</license>";
////        String s = formatXML(new License("S", "378377373"));
//        License o = parserXML(str);
//        System.out.println(o);
//        String s = serialize(new License("S", "48484848"));
//        System.out.println(s);
//        License deserialize = deserialize(s);
//        System.out.println(deserialize);
    }

    //解密数据
    public static String decrypt(String message, String key) throws Exception {
        byte[] bytesrc = convertHexString(message);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));

        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte);
    }

    public static byte[] convertHexString(String ss) {
        byte digest[] = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }

        return digest;
    }


    public static String serialize(License license){
        // 将employee对象序列化为XML
        XStream xStream = new XStream(new DomDriver());
        // 设置employee类的别名
        xStream.alias("license", License.class);
        String personXml = xStream.toXML(license);
        return personXml;
    }

    /**
     * 将XML反序列化还原为Java对象
     * @param personXml
     * @return
     */
    public static License deserialize(String personXml) {
        // 将employee对象序列化为XML
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("license", License.class);
        License license = (License) xStream.fromXML(personXml);
        return license;
    }

}
