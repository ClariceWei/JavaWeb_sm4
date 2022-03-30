package com.sm4;

import java.util.Scanner;

public class TestSm4 {
	public static void main(String[] args) {
		try{
			Scanner input=new Scanner(System.in);
			System.out.print("请输入要加密的账户：");
			String name=input.next();
			System.out.print("请输入要加密的口令：");
			String pass=input.next();
			
            String json = "{\"用户名\":\""+name+"\",\"密码\":\""+pass+"\"}";
            System.out.println("加密前："+json);
            //自定义的32位16进制秘钥
            String key = "86C63180C2806ED1F47B859DE501215B";
            String cipher = Sm4Util.encryptEcb(key,json);//sm4加密
            System.out.println("加密后："+cipher);
            System.out.println("校验："+Sm4Util.verifyEcb(key,cipher,json));//校验加密前后是否为同一数据
            json = Sm4Util.decryptEcb(key,cipher);//解密
            System.out.println("解密后："+json);
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

}
