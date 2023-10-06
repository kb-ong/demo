package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import kong.unirest.json.JSONObject;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import java.util.Map;

import org.w3c.dom.Entity;

@SpringBootApplication
@RestController
public class DemoApplication {

	class GetUriRedirectStrategy extends DefaultRedirectStrategy {

        @Override
        public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context) throws ProtocolException {        
            //boolean redirected = super.isRedirected(request, response, context);
        	boolean redirected = false;        	
        	if(response.getStatusLine().toString().indexOf("302") > 0)
        		redirected=true;
            Header[] allHeaders = response.getAllHeaders();
            for (Header header : allHeaders) {
                if (StringUtils.equals(header.getName(), "Location")) {
                    context.setAttribute("uri", header.getValue());
                }
            }
            Object uri = context.getAttribute("uri");
            response.setHeader("uri", String.valueOf(uri));            
            Stream.of(request.getAllHeaders()).forEach( p-> System.out.println(p));
            return redirected;
        }
    }
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@RequestMapping(value="/api/header", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map testH(@RequestHeader HttpHeaders headers) {
		StringBuilder builder = new StringBuilder();		
		return headers.toSingleValueMap();
	}	
	@RequestMapping("/api2/1")
	public String testapi1(){
		//System.out.println("This is my application API - 1 implemented with SpringBoot -image:riko20xx/web1");
		return "This is my application API - 1 implemented with SpringBoot -image:riko20xx/web2\n";
	}
	
	@RequestMapping("/api2/2")
	public String testapi2(){
		//ystem.out.println("This is my application API - 2 implemented with SpringBoot -image:riko20xx/web1");
		return "This is my application API - 2 implemented with SpringBoot -image:riko20xx/web2\n";
	}
	
	
	@RequestMapping("/api2/sidecar")
	public String sidecar(){
		ResponseEntity<String> response=null;
    	HttpHeaders headers = new HttpHeaders();
    	StringBuilder sbuilder = new StringBuilder();    	
	    try {
	        HttpEntity<String> request = new HttpEntity<String>(sbuilder.toString(), headers);
	        RequestConfig customizedRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).build();
	        CloseableHttpClient cclient=HttpClientBuilder
									        	.create()                        	
									        	.useSystemProperties()									        	
									        	.setRedirectStrategy(new GetUriRedirectStrategy()).build();
	        RestTemplate restTemplt = new RestTemplateBuilder()
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(
                		cclient))
                .build();
           response = restTemplt.getForEntity("http://api-service1.api1.svc.cluster.local/api1/1" ,String.class);		  
	    }
	    catch(Exception e) {
	           e.printStackTrace();
	    }	   
	    return response.getBody();
	}

	@RequestMapping(value="/getToken")
    public String publicEndpoint3(@RequestParam(name="url") String url ) {
		//String url="https://qa.federation.metlife.com/siteminderagent/forms/ldap/login.fcc";
		//String url="https://wfmaksapim.azure-api.net/login3/";
		ResponseEntity<String> response=null;
    	HttpHeaders headers = new HttpHeaders();
    	StringBuilder sbuilder = new StringBuilder();
    	sbuilder
    	  .append("target=-SM-HTTPS%3a%2f%2fqa%2efederation%2emetlife%2ecom%2faffwebservices%2fsecure%2fsecureredirect%2f%3fresponse_type%3dcode%26scope%3dopenid%26client_id%3d0009c6eb--7f72--129e--9687--53fd0a310000%26redirect_uri%3dhttps%3a%2f%2fwfmserver--fab0escgcgcuhjf7%2ez01%2eazurefd%2enet%2fapi%2flogin%2fgetOIDCToken%26SMPORTALURL%3dwUmu3-%2F9gScwAjMf7Cai5rYmRQyLUsiPzBlEdL9bYXqZxShiaVe56BTXATxAZX-%2FVyzTdKslxo-%2BMjT-%2FdGIsfCgECsKgWuEkoC7yss-%2F5V9yW1RQK-%2BACtt2pdoI3D9bEdz4LVJr-%2BHtcQKvqZ5TDXDN20UedNzkWYqF0N&USER=97503473&PASSWORD=Metlife%401234&BUTTON=");    	
	    try {
	        HttpEntity<String> request = new HttpEntity<String>(sbuilder.toString(), headers);
	        RequestConfig customizedRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).build();
	        CloseableHttpClient cclient=HttpClientBuilder
									        	.create()                        	
									        	.useSystemProperties()									        	
									        	.setRedirectStrategy(new GetUriRedirectStrategy()).build();
	        RestTemplate restTemplt = new RestTemplateBuilder()
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(
                		cclient))
                .build();
           response = restTemplt.postForEntity(url ,request, String.class);
		   //response = restTemplt.getForEntity(url , String.class);
	    }
	    catch(Exception e) {
	           e.printStackTrace();
	    }	   
	    return response.getBody();
    }	
	
	@RequestMapping(value="/testURL")
    public String publicEndpoint4(@RequestParam(name="url") String url ) {		
		ResponseEntity<String> response=null;
    	HttpHeaders headers = new HttpHeaders();
    	StringBuilder sbuilder = new StringBuilder();    	
	    try {
	        HttpEntity<String> request = new HttpEntity<String>(sbuilder.toString(), headers);
	        RequestConfig customizedRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).build();
	        CloseableHttpClient cclient=HttpClientBuilder
									        	.create()                        	
									        	.useSystemProperties()									        	
									        	.setRedirectStrategy(new GetUriRedirectStrategy()).build();
	        RestTemplate restTemplt = new RestTemplateBuilder()
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(
                		cclient))
                .build();
           response = restTemplt.getForEntity(url ,String.class);		  
	    }
	    catch(Exception e) {
	           e.printStackTrace();
	    }	   
	    return response.getBody();
    }

	@GetMapping(path = "/hello", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<JSONObject>> sayHello()
	{
		//Get data from service layer into entityList.
		List<JSONObject> entities = new ArrayList<JSONObject>();
		JSONObject entity = new JSONObject();
		entity.put("aa", "bb");
		entity.put("xx", "yy");
		entities.add(entity);
		return new ResponseEntity<>(entities, HttpStatus.OK);
	}
}
