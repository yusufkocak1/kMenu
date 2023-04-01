package com.kocak.kmenuserver.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.io.*;

@Slf4j
public class KafkaLogFilter implements Filter {

    private final KafkaTemplate<String, LogMessage> kafkaTemplate;

    public KafkaLogFilter(KafkaTemplate<String, LogMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        // Özelleştirilmiş HttpServletRequestWrapper ve HttpServletResponseWrapper sınıflarını kullanarak istek ve cevap
        // mesajlarının body'lerine erişiyoruz.

        ContentCachingResponseWrapper responseCacheWrapperObject = new ContentCachingResponseWrapper(httpResponse);
        ContentCachingRequestWrapper requestCacheWrapperObject = new ContentCachingRequestWrapper(httpRequest);

        // filterChain'i özelleştirilmiş wrapper nesnelerimiz ile çalıştırıyoruz
        filterChain.doFilter(requestCacheWrapperObject, responseCacheWrapperObject);

        // Kafka'ya göndermek için LogMessage oluşturuyoruz
        LogMessage logMessageRequest = new LogMessage(
                new Header(LogLevel.INFO, "K-menu"),
                LogBody.builder(Arrays.toString(requestCacheWrapperObject.getContentAsByteArray()))
                        .sequenceNumber(System.currentTimeMillis())
                        .sourceClassName(getClass().getName())
                        .sourceMethodName("doFilter")
                        .threadID((int) Thread.currentThread().getId())
                        .build());

        LogMessage logMessageResponse = new LogMessage(new Header(LogLevel.INFO, "K-menu"),
                LogBody.builder(Arrays.toString(responseCacheWrapperObject.getContentAsByteArray()))
                        .sequenceNumber(System.currentTimeMillis())
                        .sourceClassName(getClass().getName())
                        .sourceMethodName("doFilter")
                        .threadID((int) Thread.currentThread().getId())
                        .build());

        // LogMessage'i Kafka'ya gönderiyoruz
        kafkaTemplate.send("k-menu", logMessageRequest);

        kafkaTemplate.send("k-menu", logMessageResponse);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}