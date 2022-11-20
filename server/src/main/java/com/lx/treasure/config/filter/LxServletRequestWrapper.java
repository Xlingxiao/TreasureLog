package com.lx.treasure.config.filter;

import com.lx.treasure.common.utils.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Auther: Lx
 * @Date: 2020/7/11 12:47
 * @Description: 重写HttpServletRequestWrapper类
 */
@SuppressWarnings("unused")
public class LxServletRequestWrapper extends HttpServletRequestWrapper {

    private byte[] requestBody;

    public LxServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        String bodyString = StreamUtils.streamToString(request.getInputStream());
        requestBody = bodyString.getBytes();
    }

    public void setRequestBody(String body) {
        requestBody = body.getBytes();
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() {
                return inputStream.read();
            }
        };
    }

}
