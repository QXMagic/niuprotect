package com.niu.protect.tools.secret;

import kotlin.UByte;
public class HexTools {
    public static void printHexString(String hint, byte[] b) {
        System.out.print(hint);
        for (byte b2 : b) {
            String hex = Integer.toHexString(b2 & -1);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print(hex.toUpperCase() + " ");
        }
        System.out.println("");
    }

    public static String Bytes2HexString(byte[] b) {
        String ret = "";
        for (byte b2 : b) {
            String hex = Integer.toHexString(b2 & -1);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret = ret + hex.toUpperCase();
        }
        return ret;
    }

    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
        byte ret = (byte) (((byte) (_b0 << 4)) ^ _b1);
        return ret;
    }

    public static byte[] HexString2Bytes(String src) {
        return hexStrToByteArray(src);
    }

    public static byte[] hexStrToByteArray(String str) {
        if (str.length() % 2 > 0) {
            return new byte[4];
        }
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new byte[0];
        }
        byte[] byteArray = new byte[str.length() / 2];
        for (int i = 0; i < byteArray.length; i++) {
            try {
                String subStr = str.substring(i * 2, (i * 2) + 2);
                byteArray[i] = (byte) Integer.parseInt(subStr, 16);
            } catch (Exception e) {
                return new byte[4];
            }
        }
        return byteArray;
    }
}
