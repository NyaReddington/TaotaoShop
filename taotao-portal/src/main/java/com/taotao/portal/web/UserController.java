package com.taotao.portal.web;

import com.taotao.common.CookieUtils;
import com.taotao.common.ExceptionUtil;
import com.taotao.common.JsonUtils;
import com.taotao.common.TaotaoResult;
import com.taotao.dubbo.service.UserService;
import com.taotao.pojo.TbUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {


   @Reference
   private UserService userService;

   /**
    * 注册 信息检查
    * @param param
    * @param type
    * @return
    */
   @RequestMapping("/check/{param}/{type}")
   public TaotaoResult checkData(@PathVariable String param, @PathVariable Integer type) {
      TaotaoResult taotaoResult = userService.checkData(param, type);
      return taotaoResult;
   }


   /**
    * 用户注册
    * @param tbUser
    * @return
    */
   @RequestMapping(value = "/register", method = RequestMethod.POST)
   public TaotaoResult registerUser(TbUser tbUser) {
      TaotaoResult taotaoResult = userService.register(tbUser);
      return taotaoResult;
   }


   /**
    * 用户登录
    * @param username
    * @param password
    * @return
    */
   @RequestMapping(value = "/login", method = RequestMethod.POST)
   public TaotaoResult registerUser(String username, String password, HttpServletRequest req, HttpServletResponse resp) {
      /*SSOUtil ssoUtil = new SSOUtil();
      ssoUtil.setRequest(req);
      ssoUtil.setResponse(resp);*/

      /*HashMap<Object, Object> hashMap = new HashMap<>();
      hashMap.put("req", req);
      hashMap.put("resp", resp);
      String httpServletJSON = JSON.toJSONString(hashMap);*/
      TaotaoResult taotaoResult = userService.login(username, password);
      if (taotaoResult.getStatus() == 200) {
         String token = taotaoResult.getMsg();
         CookieUtils.setCookie(req, resp, "TT_TOKEN", token, (int) TimeUnit.MINUTES.toSeconds(30));
         return TaotaoResult.ok(token);
      }
      return null;
   }


   /**
    * 按照Token获取user登录信息
    * @param token
    * @param callback
    * @return
    */
   @CrossOrigin(origins = {"localhost", "127.0.0.1", "www.taotao.com"})
   @RequestMapping(value = "/token/{token}", method = RequestMethod.GET)
   public Object getUserByToken(@PathVariable String token, @RequestParam(required = false) String callback) {
      TaotaoResult taotaoResult = userService.getUserByToken(token);
      if(!StringUtils.isBlank(callback)) {
         return callback+"(" + JsonUtils.objectToJson(taotaoResult) +");";
      }
      return taotaoResult;
   }


   /**
    * 安全退出
    * @param token
    * @return
    */
   @RequestMapping(value = "/logout/{token}")
   @ResponseBody
   @CrossOrigin
   public Object logOutByToken(@PathVariable String token, HttpServletRequest req, HttpServletResponse resp) {
      TaotaoResult result = null;
      try {
//         result = userService.userLogout(token, req, resp);
         result = userService.userLogout(token);
      } catch (Exception e) {
         e.printStackTrace();
         return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
      }
//      return "redirect:http://localhost:8082";
      return result;
   }



}
