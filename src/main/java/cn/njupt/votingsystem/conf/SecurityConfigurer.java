package cn.njupt.votingsystem.conf;

import cn.njupt.votingsystem.model.RestResult;
import cn.njupt.votingsystem.pojo.User;
import cn.njupt.votingsystem.util.ResponseUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Describe: SpringSecurity配置类
 * @Author: tyf
 * @CreateTime: 2021/10/25
 **/
@Configuration
@EnableWebSecurity
/*开启方法权限认证*/
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

//    private final TigerLogoutSuccessHandler logoutSuccessHandler = new TigerLogoutSuccessHandler("/login");

    public static final String[] NO_AUTH_LIST = {
            "/index",
            "/login",
            "/register",
            "/static/**",
            "/images/**",
            "/css/**",
            "/js/**",
            "/channel/**"
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().formLogin().loginPage("/login").loginProcessingUrl("/login").permitAll()
                //失败处理
                .failureHandler((req, resp, e) -> ResponseUtil.restResponse(resp, HttpStatus.FORBIDDEN, RestResult.error(403, e.getMessage())))
                //成功处理
                .successHandler((req, resp, e) -> login()).defaultSuccessUrl("/root")
                .permitAll()
                .and().exceptionHandling()
                //请求登录处理，改变默认跳转登录页
                .authenticationEntryPoint((req, resp, e) -> ResponseUtil.restResponse(resp, HttpStatus.UNAUTHORIZED, RestResult.error(401, "请先登录")))
                //没有权限访问
                .accessDeniedHandler((req, resp, e) -> ResponseUtil.restResponse(resp, HttpStatus.FORBIDDEN, RestResult.error(403, "抱歉，你当前的身份无权访问")))
                //设置最大一人同时登陆
                .and().sessionManagement().maximumSessions(1)
                .expiredSessionStrategy(s -> ResponseUtil.restResponse(s.getResponse(), HttpStatus.FORBIDDEN, RestResult.error(499, "您的账号在别的地方登录，当前登录已失效")))
                .and()
                //设置登出
                .and().logout().logoutUrl("/logout").permitAll()
                .and().authorizeRequests().antMatchers(NO_AUTH_LIST).permitAll()
                .antMatchers("/*.svg", "/*.png", "/*.js", "/*.css").permitAll()
                .and().authorizeRequests().anyRequest().authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public User login(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword("***********");
        return user;
    }


}