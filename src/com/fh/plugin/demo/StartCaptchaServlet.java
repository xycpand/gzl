package com.fh.plugin.demo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.plugin.GeetestLib;


/**
 * 使用Get的方式返回：challenge和capthca_id 此方式以实现前后端完全分离的开发模式 专门实现failback
 * 
 * @author zheng
 *
 */
public class StartCaptchaServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Conifg the parameter of the geetest object
		GeetestLib gtSdk = new GeetestLib();
		gtSdk.setCaptchaId(GeetestConfig.getCaptcha_id());
		gtSdk.setPrivateKey(GeetestConfig.getPrivate_key());

		gtSdk.setGtSession(request);//如果是同一会话多实例，可以使用此函数的另一重载实现式，设置不同的key即可

		String resStr = "{}";

		if (gtSdk.preProcess() == 1) {
			// gt server is in use
			resStr = gtSdk.getSuccessPreProcessRes();
			gtSdk.setGtServerStatusSession(request, 1);

		} else {
			// gt server is down
			resStr = gtSdk.getFailPreProcessRes();
			gtSdk.setGtServerStatusSession(request, 0);
		}

		PrintWriter out = response.getWriter();
		out.println(resStr);

	}
}