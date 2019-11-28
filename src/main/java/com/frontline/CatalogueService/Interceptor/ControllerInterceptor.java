package com.frontline.CatalogueService.Interceptor;

import com.frontline.CatalogueService.utils.CertX509Handler;
import com.frontline.CatalogueService.utils.MSchainCertHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.cert.X509Certificate;;

@Component
public class ControllerInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        boolean certVerified;
        HandlerMethod method = (HandlerMethod) handler;
        MethodParameter[] parms = method.getMethodParameters();
        for (MethodParameter parm : parms) {
            logger.info(parm.getNestedGenericParameterType().getTypeName());
            System.out.println(parm.getNestedGenericParameterType().getTypeName());
        }
        X509Certificate[] certs = (X509Certificate[])request.getAttribute("javax.servlet.request.X509Certificate");
        X509Certificate certificate = certs[0];
        String commonName = CertX509Handler.getDomainName(certificate);
        System.out.println(commonName);
        certVerified = MSchainCertHandler.validateCertificate(commonName,certificate);
//        certVerified =true;
//        try {
//            SSLContext sslContext = SSLContext.getDefault();
//            SSLContext sslContext1 = SSLContext.getInstance("SSL");
//            System.out.println(SSLContext.getInstance("TLS"));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return certVerified;
    }

    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        logger.info("this is interceptor, postHandle method");
    }
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        logger.info("this is interceptor, afterCompletion method");
    }
}
