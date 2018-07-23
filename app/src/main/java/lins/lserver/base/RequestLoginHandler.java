package lins.lserver.base;

import com.yanzhenjie.andserver.RequestHandler;
import com.yanzhenjie.andserver.util.HttpRequestParser;

import org.apache.httpcore.HttpException;
import org.apache.httpcore.HttpRequest;
import org.apache.httpcore.HttpResponse;
import org.apache.httpcore.entity.StringEntity;
import org.apache.httpcore.protocol.HttpContext;

import java.io.IOException;
import java.util.Map;

public class RequestLoginHandler implements RequestHandler {

    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        Map<String, String> params = HttpRequestParser.parseParams(request);


        // Request params.
        String userName = params.get("username");
        String password = params.get("password");


        if ("123".equals(userName) && "123".equals(password)) {
            StringEntity stringEntity = new StringEntity("Login Succeed", "utf-8");
            response.setEntity(stringEntity);
        } else {
            StringEntity stringEntity = new StringEntity("Login Failed", "utf-8");
            response.setEntity(stringEntity);
        }
    }
}