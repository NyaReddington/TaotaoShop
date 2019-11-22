package com.taotao.sso.service;

import com.taotao.common.TaotaoResult;
import com.taotao.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    TaotaoResult checkData(String param, Integer type);

    TaotaoResult register(TbUser tbUser);

    TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response);

    TaotaoResult getUserByToken(String token);

    TaotaoResult userLogout(String token, HttpServletRequest req, HttpServletResponse resp);
}
