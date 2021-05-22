package com.example.community.community.provider;

import com.alibaba.fastjson.JSON;
import com.example.community.community.dto.AccessTokenDto;
import com.example.community.community.dto.GitHubUserDto;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
/**
 * description: Github的API的HTTP访问请求
 */
public class GithubProvider {
    MediaType JSONtype = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    /**
     * description: 通过设置的参数accessTokenDto，向Github进行post请求得到access_token
     *
     * @Param: AccessTokenDto accessToken
     * @return token
     */
    public String getAccessToken(AccessTokenDto accessTokenDto) throws Exception{
        //得到请求体,通过json，把对象转换成String
        RequestBody body = RequestBody.create(JSONtype, JSON.toJSONString(accessTokenDto));
        //设置请求体
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        //得到响应体
        try (Response response = client.newCall(request).execute()) {
            //String: access_token=gho_16C7e42F292c6912E7710c838347Ae178B4a&token_type=bearer
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            System.out.println(token);
            return token;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * description: 通过得到的token，向Githubapi进行get请求得到用户信息
     *
     * @Param: token
     * @return user
     */
    public GitHubUserDto getUser(String accessToken) throws Exception{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization", "token "+accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GitHubUserDto user = JSON.parseObject(string, GitHubUserDto.class);
            System.out.println(user.toString());
            return user;
        }
    }
}
