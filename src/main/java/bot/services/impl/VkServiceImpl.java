package bot.services.impl;

import bot.models.Response;
import bot.models.VkNotification;
import bot.services.VkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
@Qualifier("vkService")
public class VkServiceImpl implements VkService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${vk.apiVersion}")
    private String version;
    @Value("${vk.method.messages.send}")
    private String method;
    @Value("${vk.url}")
    private String baseUr;
    @Value("${vk.token}")
    private String token;
    private final Random random = new Random();

    @Override
    public String sendMessage(VkNotification notification) {
        StringBuilder url = new StringBuilder(baseUr);
        url.append(method).append('?');
        url.append("user_id=").append(notification.getObject().getUserId());
        url.append("&random_id=").append(random.nextInt());
        url.append("&message=").append("Вы сказали:").append(notification.getObject().getBody());
        url.append("&access_token=").append(token);
        url.append("&v=").append(version);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity<Response> response = restTemplate.exchange(url.toString(), HttpMethod.POST, entity, Response.class);
        if(response.getStatusCode() == HttpStatus.OK)
            return "ok";

        throw new RuntimeException("Could not send message to the user " + "user_id");
    }
}
