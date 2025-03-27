package mighty.youtube.video.util;

import java.util.Map;

public interface ApiTemplate {

    public Object makeGetCall(String apiUrl ,String endPoint, Map<String , String> queryParam);

    public Object makePostCall(String apiUrl ,String endPoint, Map<String , String> queryParam , Object requestBody);

    public Object makePutCall(String apiUrl ,String endPoint, Map<String , String> queryParam , Object requestBody);
}
