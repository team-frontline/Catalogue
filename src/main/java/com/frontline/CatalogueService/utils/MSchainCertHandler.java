package com.frontline.CatalogueService.utils;


import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.security.cert.X509Certificate;

public class MSchainCertHandler {
    public  static boolean validateCertificate(String cn, X509Certificate x509Certificate){
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("CN", cn);
        map.add("cert",x509Certificate.toString());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<JsonNode> response = restTemplate.postForEntity( "http://18.232.207.225:3000/api/eval", request , JsonNode.class );
        boolean validity = response.getBody().get("payload").get("validity").asBoolean();
        System.out.println("from mschain validity checker");
        System.out.println(response.getBody().toString());

        if(validity){
            return true;
        }else{
            return false;
        }
    }
}
