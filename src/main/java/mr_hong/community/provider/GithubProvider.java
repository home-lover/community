package mr_hong.community.provider;

import com.alibaba.fastjson.JSON;
import mr_hong.community.dto.AccessTokenDto;
import mr_hong.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component                //会将此放到spring容器里面，用的时候只需要@autowired就可以自动识别
public class GithubProvider {
    public String getAccessToken(AccessTokenDto accessTokenDto){                   //利用OKHTTP完成post
         MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String []split=string.split("&");
            String token=split[0];
            String access_token=token.split("=")[1];
            //System.out.println(access_token);
            return access_token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public GithubUser getUser(String accessToken){        //利用OKHTTP完成get
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+ accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string =response.body().string();
            GithubUser githubUser=JSON.parseObject(string,GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
