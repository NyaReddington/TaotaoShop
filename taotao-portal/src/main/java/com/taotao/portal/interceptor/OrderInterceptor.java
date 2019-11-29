package com.taotao.portal.interceptor;

import com.taotao.common.CookieUtils;
import com.taotao.common.HttpUtil;
import com.taotao.common.SystemConstants;
import com.taotao.common.TaotaoResult;
import com.taotao.dubbo.service.UserService;
import com.taotao.pojo.TbUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 订单模块拦截器
 * 用户只有登录后才能操作订单相关功能
 */
@Slf4j
@Component
public class OrderInterceptor implements HandlerInterceptor {

//   @Value("${taotao.sso.login_url}")
   @Value("http://localhost:8082/page/showLogin")
   private String ssoLoginUrl;

//   @Value("${taotao.sso.token_url}")
   @Value("http://localhost:8082/user/token")
   private String ssoTokenUrl;

   @Reference
   private UserService userService;

   @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {


      // 获取Cookie中的token
      String token = CookieUtils.getCookieValue(request, "TT_TOKEN");


      /** ============================================================================ **/
      /** ============================================================================ **/
      // 获取用户原本想去的页面
      String redirectUrl = HttpUtil.getFullUrl(request);
//      String redirectUrl = request.getRequestURL().toString();
      /** ============================================================================ **/
      /** ============================================================================ **/

      // 如果用户没有token，重定向到登录页，并且附加原始url
      if(StringUtils.isBlank(token)) {
         PrintWriter writer = response.getWriter();
         response.sendRedirect(ssoLoginUrl+"?redirect=" + redirectUrl);
//         writer.println("<script charset='gbk'>alert('无法获取登录信息，请重新登录！');location.href='"+ssoLoginUrl+"?redirectUrl="+redirectUrl+"';</script>");
         writer.println("<script charset='gbk'>location.href='"+ssoLoginUrl+"?redirectUrl="+redirectUrl+"';</script>");
         return false;
      }

      // 拿从Cookie中获取的token信息请求SSO服务，获取真正的用户信息
//      String jsonData = HttpUtil.doGet(ssoTokenUrl + "/" + token);
      TaotaoResult taotaoResult = userService.getUserByToken(token);

      try {
         // 用户token正常，将token对应的用户信息保存到request作用域
//         TaotaoResult taotaoResult = TaotaoResult.formatToPojo(jsonData, TbUser.class);
         if(taotaoResult.getStatus().equals(SystemConstants.TAOTAO_RESULT_STATUS_OK)) {
            TbUser tbUser = (TbUser) taotaoResult.getData();
            System.out.println("当前登录用户：" + tbUser.getUsername());
            request.setAttribute("user", tbUser);
            return true;
         }

         PrintWriter writer = response.getWriter();
         // 用户会话过期后的处理
//         response.sendRedirect("<script>alert('登录信息失效，请重新登录');window.location.href='"+ssoLoginUrl+"';</script>");
//         writer.println("<script charset='gbk'>alert('无法获取登录信息，请重新登录！');location.href='"+ssoLoginUrl+"?redirectUrl="+redirectUrl+"';</script>");
         writer.println("<script charset='gbk'>location.href='"+ssoLoginUrl+"?redirectUrl="+redirectUrl+"';</script>");
//         response.sendRedirect(ssoLoginUrl+"?redirect=" + redirectUrl);
         return false;
      } catch (Exception e) {
         log.error("无法获取用户信息", e);
         return false;
      }
   }
}
