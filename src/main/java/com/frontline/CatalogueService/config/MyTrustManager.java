package com.frontline.CatalogueService.config;

import java.security.cert.X509Certificate;

class MyTrustManager implements com.sun.net.ssl.X509TrustManager {

    @Override
    public boolean isClientTrusted(X509Certificate[] x509Certificates) {
        return false;
    }

    @Override
    public boolean isServerTrusted(X509Certificate[] x509Certificates) {
        return false;
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

}
