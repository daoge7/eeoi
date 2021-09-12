package cn.ccsit.common.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.UUID;

public class Des8Util {

    private static final String PASSWORD_CRYPT_KEY = "A5483FA7";

    // private final static String DES = "DES";
    // private static final byte[] desKey;
    // 解密数据
    public static String decrypt(String message) throws Exception {

        byte[] bytesrc = convertHexString(message);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(
                PASSWORD_CRYPT_KEY.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(
                PASSWORD_CRYPT_KEY.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte);
    }

    public static String encrypt(String message) {
        String result = "";
        try {
            message = java.net.URLEncoder.encode(message, "utf-8");
            result = toHexString(encrypt(message, PASSWORD_CRYPT_KEY))
                    .toUpperCase();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
        return result;
    }

    public static byte[] encrypt(String message, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return cipher.doFinal(message.getBytes("UTF-8"));
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

    public static String toHexString(byte b[]) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2)
                plainText = "0" + plainText;
            hexString.append(plainText);
        }
        return hexString.toString();
    }

//    public static void main(String[] args) throws Exception {
////        String license = "5294C4AE7890D068FCAF2010AE4064C2A91E05979A58ACE8E0F87C763467B86E1710B73E50A37817C46A6B1BD541702F26A4520975C4999DB3D8EB7ECD8BED4DE1A7E7C8A9CD2E417E1F238DEBCFE38DA65055D44C96C8C9D06B407241A42E151634EBDF8905450D99F2021011B32266FE75AD9E591D1AD88A5E80B714190EB213B3F70F74D895AC097FB4901BACE2B671CD69BAD076B6470974F6951D1E681AD0ECF9E20BCCA3D7E6F79E580681F41801A0ECD29C28DB0F";
//        String timestamp = String.valueOf(System.currentTimeMillis());
//        System.out.println(timestamp);
////        String value = "1597479963125S5294C4AE7890D068FCAF2010AE4064C2A91E05979A58ACE8E0F87C763467B86E1710B73E50A37817C46A6B1BD541702F26A4520975C4999DB3D8EB7ECD8BED4DE1A7E7C8A9CD2E417E1F238DEBCFE38DA65055D44C96C8C9D06B407241A42E151634EBDF8905450D99F2021011B32266FE75AD9E591D1AD88A5E80B714190EB213B3F70F74D895AC097FB4901BACE2B671CD69BAD076B6470974F6951D1E681AD0ECF9E20BCCA3D7E6F79E580681F41801A0ECD29C28DB0F";
//        String licence = "5294C4AE7890D068FCAF2010AE4064C2A91E05979A58ACE8E0F87C763467B86E1710B73E50A37817C46A6B1BD541702F26A4520975C4999DB3D8EB7ECD8BED4DE1A7E7C8A9CD2E417E1F238DEBCFE38DA65055D44C96C8C9D06B407241A42E151634EBDF8905450D99F2021011B32266FE75AD9E591D1AD88A5E80B714190EB213B3F70F74D895AC27F934ED487CE085BF0CBAD027A0898615BD437103ECDAD8876769885C0092B57BA0809DBC3AFE977A7DF4D4707035072A54EBAEF173A3A9";
//        String value = timestamp + "C" + licence;
//        System.out.println("加密数据:" + value);
//        String a = encrypt(value);
//        System.out.println("加密后的数据为:" + a);
//        System.out.println(UUID.randomUUID());
//        //System.out.println(decrypt("0D6103E3F9482511A0C05009570AC4DDF8CC325C07C4766C"));
//        //String voyageData="{\"imoNo\":\"9712890\",\"voyageCode\":\"1917\",\"voyagePorts\":[{\"recordType\":\"0\",\"portcn\":\"NINGBO\",\"porten\":\"Ningbo\",\"portid\":\"CNNGB\",\"isEu\":\"0\",\"arrTm\":\"2019-09-10 10:35:00\",\"arrZone\":8.0,\"deptTm\":\"2019-09-12 06:30:00\",\"deptZone\":8.0,\"distance\":1235,\"inPort\":\"0\",\"portWork\":\"2\",\"shorePower\":\"\",\"voyagePortloadings\":[{\"loadingType\":\"1\",\"cargoTons\":43196,\"ballastTons\":\"200\",\"allBoxNum\":\"0\",\"heavyBoxNum\":\"0\",\"peopleNum\":\"0\",\"carsNum\":\"0\"},{\"loadingType\":\"0\",\"cargoTons\":0,\"ballastTons\":\"20000\",\"allBoxNum\":\"0\",\"heavyBoxNum\":\"0\",\"peopleNum\":\"0\",\"carsNum\":\"0\"}],\"voyagePortoils\":[{\"oilId\":\"02\",\"oilName\":\"TEST\",\"arrTons\":\"594.30\",\"deptTons\":\"1253.9\",\"addTons\":692.13,\"addTm\":\"\",\"outTons\":\"\",\"outTm\":\"\",\"correctTons\":\"\",\"correctTm\":\"\"},{\"oilId\":\"007\",\"oilName\":\"MDO/MGO\",\"arrTons\":\"55.5\",\"deptTons\":\"55.5\",\"addTons\":692.13,\"addTm\":\"\",\"outTons\":\"\",\"outTm\":\"\",\"correctTons\":\"\",\"correctTm\":\"\"}]},{\"recordType\":\"0\",\"portcn\":\"CAOFEIDIAN\",\"porten\":\"Caofeidian\",\"portid\":\"CNCFD\",\"isEu\":\"0\",\"arrTm\":\"2019-08-28 18:30:00\",\"arrZone\":8.0,\"deptTm\":\"2019-08-29 10:45:00\",\"deptZone\":8.0,\"distance\":762,\"inPort\":\"0\",\"portWork\":\"1\",\"shorePower\":\"\",\"voyagePortloadings\":[{\"loadingType\":\"1\",\"cargoTons\":0,\"ballastTons\":\"16000\",\"allBoxNum\":\"0\",\"heavyBoxNum\":\"0\",\"peopleNum\":\"0\",\"carsNum\":\"0\"},{\"loadingType\":\"0\",\"cargoTons\":43196.15,\"ballastTons\":\"200\",\"allBoxNum\":\"0\",\"heavyBoxNum\":\"0\",\"peopleNum\":\"0\",\"carsNum\":\"0\"}],\"voyagePortoils\":[{\"oilId\":\"02\",\"oilName\":\"TEST\",\"arrTons\":\"787.00\",\"deptTons\":\"782.40\",\"addTons\":\"\",\"addTm\":\"\",\"outTons\":\"\",\"outTm\":\"\",\"correctTons\":\"\",\"correctTm\":\"\"},{\"oilId\":\"007\",\"oilName\":\"MDO/MGO\",\"arrTons\":\"55.5\",\"deptTons\":\"0\",\"addTons\":\"\",\"addTm\":\"\",\"outTons\":\"\",\"outTm\":\"\",\"correctTons\":\"\",\"correctTm\":\"\"}]},{\"recordType\":\"0\",\"portcn\":\"NINGBO\",\"porten\":\"Ningbo\",\"portid\":\"CNNGB\",\"isEu\":\"0\",\"arrTm\":\"2019-08-20 10:00:00\",\"arrZone\":8.0,\"deptTm\":\"2019-08-22 07:10:00\",\"deptZone\":8.0,\"distance\":732,\"inPort\":\"0\",\"portWork\":\"2\",\"shorePower\":\"\",\"voyagePortloadings\":[{\"loadingType\":\"1\",\"cargoTons\":61741.85,\"ballastTons\":\"200\",\"allBoxNum\":\"0\",\"heavyBoxNum\":\"0\",\"peopleNum\":\"0\",\"carsNum\":\"0\"},{\"loadingType\":\"0\",\"cargoTons\":0,\"ballastTons\":\"20000\",\"allBoxNum\":\"0\",\"heavyBoxNum\":\"0\",\"peopleNum\":\"0\",\"carsNum\":\"0\"}],\"voyagePortoils\":[{\"oilId\":\"02\",\"oilName\":\"TEST\",\"arrTons\":\"934.80\",\"deptTons\":\"892.1\",\"addTons\":\"\",\"addTm\":\"\",\"outTons\":\"\",\"outTm\":\"\",\"correctTons\":\"\",\"correctTm\":\"\"},{\"oilId\":\"007\",\"oilName\":\"MDO/MGO\",\"arrTons\":\"56\",\"deptTons\":\"55.5\",\"addTons\":\"\",\"addTm\":\"\",\"outTons\":\"\",\"outTm\":\"\",\"correctTons\":\"\",\"correctTm\":\"\"}]}]}";
////		String json=HttpRequestUtil.sendPost("http://172.26.200.93:8060/eeoimis/api/voyageData2CCS","timestamp="+timestamp+"&token=" +a+
////				"&voyageData="+voyageData);
////		System.out.println("返回:" + json);
////		String value = "B47CA0F681D2FF971BEDC1DD2F2C0604E193F285CCDC259B";
//        System.out.println("解密数据:" + decrypt(a));
////		String a = decrypt(value);
////		System.out.println("解密后的数据为:" + a);
//        //System.out.println(decrypt("0D6103E3F9482511A0C05009570AC4DDF8CC325C07C4766C"));
////        int byteValue = Integer.parseInt("S5", 16);
//    }
public static void main(String[] args) {
    System.out.println(true && false);
}
}
