package com.fh.controller.ali.alipay.menu;

import java.util.ArrayList;
import java.util.List;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayMobilePublicMenuAddRequest;
import com.alipay.api.response.AlipayMobilePublicMenuAddResponse;
import com.fh.controller.ali.alipay.factory.AlipayAPIClientFactory;
import com.fh.util.JsonToMap;
import com.fh.util.PageData;

public class MenuController {

	public static void main(String[] args) {
		PageData pd = new PageData();
		
		List<PageData> list = new ArrayList<PageData>();
		
		
		PageData p1 = new PageData();
		p1.put("actionParam", "ZFB_HFCZ");
		p1.put("actionType", "out");
		p1.put("name", "话费充值");
		
		
		
		PageData p3 = new PageData();
		p3.put("actionParam", "http://example.com/b.php");
		p3.put("actionType", "link");
		p3.put("name", "帐号绑定");
		
		List<PageData> ls = new ArrayList<PageData>();
		
		PageData p21 = new PageData();
		p21.put("actionParam", "ZFB_YECX");
		p21.put("actionType", "out");
		p21.put("name", "余额查询");
		
		PageData p22 = new PageData();
		p22.put("actionParam", "95188");
		p22.put("actionType", "tel");
		p22.put("name", "联系我们");
		
		PageData p23 = new PageData();
		p23.put("actionParam", "http://example.com/a.php");
		p23.put("actionType", "link");
		p23.put("name", "话费查询");
		
		ls.add(p21);
		ls.add(p22);
		ls.add(p23);
		
		PageData p2 = new PageData();
		p2.put("name", "查询");
		p2.put("subButton", ls);
		
		list.add(p1);
		list.add(p2);
		list.add(p3);
		
		pd.put("button", list);
		
		System.out.println(JsonToMap.map2json(pd));

        AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();

        // 使用SDK，构建群发请求模型
        AlipayMobilePublicMenuAddRequest request = new AlipayMobilePublicMenuAddRequest();
        request.setBizContent(JsonToMap.map2json(pd));

        try {

            // 使用SDK，调用群发接口发送图文消息
        	AlipayMobilePublicMenuAddResponse response = alipayClient.execute(request);

            //这里只是简单的打印，请开发者根据实际情况自行进行处理
            if (null != response && response.isSuccess()) {
                System.out.println("消息发送成功 : response = " + response.getBody());
            } else {
                System.out
                    .println("消息发送失败 code=" + response.getCode() + "msg=" + response.getMsg());
            }
        } catch (AlipayApiException e) {
            System.out.println("消息发送失败");
        }

    }
	
}
