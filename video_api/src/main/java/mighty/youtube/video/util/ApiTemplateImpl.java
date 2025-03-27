package mighty.youtube.video.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

public class ApiTemplateImpl implements ApiTemplate {

    @Autowired
    RestTemplate restTemplate;

    public String addQueryParams(String url , Map<String , String> queryParams){
        if(queryParams.size()==0){
            return url;
        }
        int count = 1;
        url += "?";

        for(String key: queryParams.keySet()){
            url += key + "=" + queryParams.get(key);
            if(count<queryParams.size()){
                url += "&";
            }
            count++;
        }
        return url;
    }

    public URI createUrl(String apiUrl , String endPoint, Map<String , String> queryParams ){
        String url = apiUrl + endPoint;
        url = this.addQueryParams(url,queryParams);
        return URI.create(url);
    }

    @Override
    public Object makeGetCall(String apiUrl, String endPoint, Map<String, String> queryParams) {
        URI finalUrl = this.createUrl(apiUrl,endPoint,queryParams);
        RequestEntity request = RequestEntity.get(finalUrl).build();
        ResponseEntity<Object> response = restTemplate.exchange(finalUrl, HttpMethod.GET,request ,Object.class);

        return response.getBody();
    }

    @Override
    public Object makePostCall(String apiUrl, String endPoint, Map<String, String> queryParams, Object requestBody) {
        URI url = this.createUrl(apiUrl, endPoint, queryParams);
        RequestEntity request = RequestEntity.post(url).body(requestBody);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, request, Object.class);
        return response.getBody();
    }

    @Override
    public Object makePutCall(String apiUrl, String endPoint, Map<String, String> queryParams, Object requestBody) {
        URI url = this.createUrl(apiUrl, endPoint, queryParams);
        RequestEntity request = RequestEntity.put(url).body(requestBody);
        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.PUT, request, Object.class);
        return response.getBody();
    }
}
