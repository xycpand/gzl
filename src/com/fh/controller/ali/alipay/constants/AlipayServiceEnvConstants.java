/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.fh.controller.ali.alipay.constants;

/**
 * 支付宝服务窗环境常量（demo中常量只是参考，需要修改成自己的常量值）
 * 
 * @author taixu.zqq
 * @version $Id: AlipayServiceConstants.java, v 0.1 2014年7月24日 下午4:33:49 taixu.zqq Exp $
 */
public class AlipayServiceEnvConstants {

    /**支付宝公钥-从支付宝服务窗获取*/
    public static final String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

    /**签名编码-视支付宝服务窗要求*/
    public static final String SIGN_CHARSET      = "GBK";

    /**字符编码-传递给支付宝的数据编码*/
    public static final String CHARSET           = "GBK";

    /**签名类型-视支付宝服务窗要求*/
    public static final String SIGN_TYPE         = "RSA";

    /** 服务窗appId  */
    //TODO !!!! 注：该appId必须设为开发者自己的服务窗id  这里只是个测试id
    public static final String APP_ID            = "2015081400216007";

    //开发者请使用openssl生成的密钥替换此处  请看文档：https://fuwu.alipay.com/platform/doc.htm#2-1接入指南
    //TODO !!!! 注：该私钥为测试账号私钥  开发者必须设置自己的私钥 , 否则会存在安全隐患 
    public static final String PRIVATE_KEY       = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALGplizJClsgIFhGjee9hBi7Igq2CZMYDK9BgZ9UIAvsOCDlvogwfTNfClIpQdgzy7BXn6CNTvQTk3RBnHXzKaUwhJ9eerMkY6E8R6L/z7TBSpJOZUDMPFl43OSoJD/6bOJgnatcBww0+Q0dZL3wi/ocwQuS7Dd4md6XEi8GuShRAgMBAAECgYAcqi4Ov8kp9r8BPzD6KPu9OcQ+UiytTP4O8NTHLj2VbuGHfUoGkzxAI594ritvhLvikeCxeNs6SkZKRaPG7BrVxWnfbkD5hqWKEzHRTwQp5fCYLORZiSDrQiuzasS4lq2HDlZ3ZmOQWqkKgKpRnuXZKyRkCjhwTdUG3GcqXYXzgQJBANwY+y5E+c3kxK2mcTr6UkfLRUu2RlIG6Jp0xXiehFiU0GYtW5DY7A6vmyPTcmfeUwmoZu6ikvIn5Dh5m5vCIMkCQQDOpJBIzDh/6venqm454Qgq5gvyEADI4Z2I32RRp9YuaAgBwwVA6nbEyEd3+VgYBC3Bwp6TCc/CHZzJRByTrddJAkEAvTGgINrb3LfMtPSBGtB4kAxUacuqUPJtWfAMmy2v2DE7nslYj39YExuygS5OhqieuouOx4zqQcw2qrEmKNLLuQJBAI9R17iF29Hsl6O0Mwr1poKKV1KmAsVQpcBen+d6brR09siyCBzEAWfuOJNfXtgZXdr7Lpxwu6W4gBufn+pVMfECQE7IHKLEoBA2RFPkt5UevEKeYCk3AYSMy9vEgQruYGfx3aX/Cqiueb/eCq1+i6syQdCpFBxN/6Tqjer4JHbzDAU=";

    //TODO !!!! 注：该公钥为测试账号公钥  开发者必须设置自己的公钥 ,否则会存在安全隐患
    public static final String PUBLIC_KEY        = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxqZYsyQpbICBYRo3nvYQYuyIKtgmTGAyvQYGfVCAL7Dgg5b6IMH0zXwpSKUHYM8uwV5+gjU70E5N0QZx18ymlMISfXnqzJGOhPEei/8+0wUqSTmVAzDxZeNzkqCQ/+mziYJ2rXAcMNPkNHWS98Iv6HMELkuw3eJnelxIvBrkoUQIDAQAB";

    
    /**支付宝网关*/
    public static final String ALIPAY_GATEWAY    = "https://openapi.alipay.com/gateway.do";

    /**授权访问令牌的授权类型*/
    public static final String GRANT_TYPE        = "authorization_code";
}
