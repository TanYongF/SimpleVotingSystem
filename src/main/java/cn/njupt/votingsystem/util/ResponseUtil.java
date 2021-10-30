package cn.njupt.votingsystem.util;

import cn.njupt.votingsystem.model.RestResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Describe: Restful风格Response
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
public class ResponseUtil {
    public static <T> void restResponse(HttpServletResponse resp, HttpStatus status, RestResult<T> responseVo) throws IOException {
        resp.setStatus(status.value());
        resp.setContentType(MediaType.APPLICATION_JSON.toString());
        resp.getOutputStream().write(new ObjectMapper().writeValueAsBytes(responseVo));
    }
}