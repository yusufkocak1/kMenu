package com.kocak.kmenuserver.log;

import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class LoggingHttpServletRequestWrapper extends MockHttpServletRequest {

    private final byte[] body;

    public LoggingHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super((ServletContext) request);
        body = request.getInputStream().readAllBytes();
    }

    @Override
    public ServletInputStream getInputStream() {
        return new DelegatingServletInputStream(new ByteArrayInputStream(body));
    }

    public byte[] getBody() {
        return body;
    }
}
