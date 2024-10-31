package com.hmall.smarthome.interceptor;

import com.hmall.smarthome.config.IotConfig;
import com.hmall.smarthome.server.IotService;
import lombok.RequiredArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements Interceptor {
    private final IotConfig iotConfig;
    private final IotService iotService;
    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

//    public AuthInterceptor(IotConfig iotConfig, IotService iotService) {
//        this.iotConfig = iotConfig;
//        this.iotService = iotService;
//    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("X-Auth-Token", iotConfig.getToken()) // 添加初始 token
                .build();

        Response response = chain.proceed(request);

        // 检查响应状态码
        if (response.code() == 401) {
            log.info("Token 过期，正在刷新...");
            synchronized (this) {
                String newToken = iotService.getToken();
                iotConfig.setToken(newToken);  // 更新配置中的 token
            }

            // 使用新的 token 重新构建请求
            Request newRequest = chain.request().newBuilder()
                    .header("X-Auth-Token", iotConfig.getToken())
                    .build();
            response.close();
            return chain.proceed(newRequest);  // 重新执行请求
        }
        return response;
    }
}
