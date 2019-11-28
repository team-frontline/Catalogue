package com.frontline.CatalogueService.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class LoggingDispatcherServlet extends DispatcherServlet {
    private final Log logger = LogFactory.getLog(getClass());

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        HandlerExecutionChain handler = getHandler(request);

        try {
            super.doDispatch(request, response);
        } finally {
            log(request, response, handler);
            updateResponse(response);
        }
    }

    private void log(HttpServletRequest requestToCache, HttpServletResponse responseToCache,
                     HandlerExecutionChain handler) {
        LogMessage log = new LogMessage();
        log.setHttpStatus(responseToCache.getStatus());
        log.setHttpMethod(requestToCache.getMethod());
        log.setPath(requestToCache.getRequestURI());
        log.setClientIp(requestToCache.getRemoteAddr());
        log.setAuthType(requestToCache.getAuthType());
        log.setResponse(getResponsePayload(responseToCache));
        log.setPayload(getRequestPayLoad(requestToCache));
        logger.info(log);
    }

    private String getResponsePayload(HttpServletResponse response) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response,
                ContentCachingResponseWrapper.class);
        return getContentFromWrapper(wrapper);
    }

    private String getRequestPayLoad(HttpServletRequest request) {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        return getContentFromWrapper(wrapper);
    }

    private void updateResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response,
                ContentCachingResponseWrapper.class);
        responseWrapper.copyBodyToResponse();
    }

    private String getContentFromWrapper(Object wrapper) {
        if (wrapper != null) {
            byte[] buf;
            String encoding;
            try {
                ContentCachingRequestWrapper ReqWrapper = (ContentCachingRequestWrapper) wrapper;
                buf = ReqWrapper.getContentAsByteArray();
                encoding = ReqWrapper.getCharacterEncoding();
            } catch (ClassCastException e) {
                ContentCachingResponseWrapper ResWrapper = (ContentCachingResponseWrapper) wrapper;
                buf = ResWrapper.getContentAsByteArray();
                encoding = ResWrapper.getCharacterEncoding();
            }

            if (buf.length > 0) {
                int length = Math.min(buf.length, 5120);
                try {
                    return new String(buf, 0, length, encoding);
                } catch (UnsupportedEncodingException ex) {
                    // NOOP
                }
            }
        }
        return "[unknown]";
    }

}
