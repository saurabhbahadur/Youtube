package mighty.youtube.video.config;

import io.imagekit.sdk.config.Configuration;

import io.imagekit.sdk.ImageKit;
import mighty.youtube.video.util.ApiTemplate;
import mighty.youtube.video.util.ApiTemplateImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@org.springframework.context.annotation.Configuration
public class AppConfig {

    @Value("${image.url.endpoint}") //@Value annotation help us to get the value of a property defied ins app properties file
    String urlEndPoint;
    @Value("${image.private.key}")
    String privateKey;
    @Value("${image.public.key}")
    String publicKey;

    @Bean
    public ImageKit getImageKit(){
        ImageKit imageKit = ImageKit.getInstance();
        Configuration configuration = new Configuration(publicKey,privateKey,urlEndPoint);
        imageKit.setConfig(configuration);
        return imageKit;
    }

    @Bean
    public ApiTemplate getApiTemplate(){
        return new ApiTemplateImpl();
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
