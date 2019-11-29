package com.taotao.dubbo.service;

import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface UserService {

    TaotaoResult checkData(String param, Integer type);

    TaotaoResult register(TbUser tbUser);

    TaotaoResult login(String username, String password);

    TaotaoResult getUserByToken(String token);

//    TaotaoResult userLogout(String token, HttpServletRequest req, HttpServletResponse resp);
    TaotaoResult userLogout(String token);
}
