package cn.njupt.votingsystem.handler;

import cn.njupt.votingsystem.model.RestResult;
import cn.njupt.votingsystem.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/10/31
 **/
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if(isAjaxRequest(httpServletRequest)){
            ResponseUtil.restResponse(httpServletResponse, HttpStatus.UNAUTHORIZED, RestResult.error(401, "请先登录"));
        }else{
            httpServletResponse.setStatus(401);
            httpServletResponse.sendRedirect("/login");
        }

    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
    }
}
