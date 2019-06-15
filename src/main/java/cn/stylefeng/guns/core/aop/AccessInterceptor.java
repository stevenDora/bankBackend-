package cn.stylefeng.guns.core.aop;

import cn.stylefeng.guns.modular.system.annotate.AccessLimit;
import cn.stylefeng.guns.modular.system.constant.PayApiEnum;
import cn.stylefeng.guns.modular.system.dto.ResponseResult;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;


@Service
public class AccessInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = LoggerFactory.getLogger(AccessInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/**
		 * 获取调用 获取主要方法
		 */
		if(handler instanceof HandlerMethod) {
			logger.info("打印拦截方法handler ：{} ",handler);
			HandlerMethod hm = (HandlerMethod)handler;
			AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
			if(accessLimit == null) {
				return true;
			}
			else {
				/*render(response, PayApiEnum.DEPOSIT_FAILED_MERCHANT_REPEAT_SUBMIT);
				return false;*/
			}

		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	private void render(HttpServletResponse response, PayApiEnum cm)throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		OutputStream out = response.getOutputStream();
		String str  = JSON.toJSONString(ResponseResult.builder()
				.data(null)
				.code(PayApiEnum.DEPOSIT_FAILED_MERCHANT_REPEAT_SUBMIT.getCode())
				.msg(PayApiEnum.DEPOSIT_FAILED_MERCHANT_REPEAT_SUBMIT.getDesc()).build());
		out.write(str.getBytes("UTF-8"));
		out.flush();
		out.close();
	}



}
