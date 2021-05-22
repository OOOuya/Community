package com.example.community.community.controller;

import com.example.community.community.dto.AccessTokenDto;
import com.example.community.community.dto.GitHubUserDto;
import com.example.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
/**
 * description: 授权交互
 */
public class AuthorizeController {
    @Autowired
    private AccessTokenDto accessToken;

    @Value("${github.client.id}")
    String clientId;

    @Value("${github.client.secrets}")
    String clientSecrets;

    @Value("${github.redirect.url}")
    String redirectUrl;
    
    @Autowired
    private GithubProvider githubProvider;
    /**
     * description:
     * 1. 捕捉到登录后github重定向放回的url，获取返回的参数code和state
     * 2. 设置第二次api的url参数。(https://github.com/login/oauth/access_token)后面跟的参数
     * 3. 使用OKHTTP进行第二次post请求
     * 4. 获取到access_token之后，进行第三次API的get请求,获取返回的用户信息
     * @return index页面
     */
    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state) throws Exception {
        //设置第二次api的url参数。
        accessToken.setCode(code);
        accessToken.setState(state);
        accessToken.setClient_id(clientId);
        accessToken.setClient_secret(clientSecrets);
        accessToken.setRedirect_uri(redirectUrl);
        //使用OKHTTP进行第二次post请求
        String accessToken = githubProvider.getAccessToken(this.accessToken);
        //获取到access_token之后，进行第三次API的get请求,获取到返回的用户信息
        final GitHubUserDto user = githubProvider.getUser(accessToken);
        return "index";
    }
}
