package bot.controllers;

import bot.models.VkNotification;;
import bot.services.impl.VkServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = {"/vk/"})
public class VkController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    VkServiceImpl vkService;

    @Value("${vk.confirmationCode}")
    private String confirmationCode;
    @Value("${vk.secretCode}")
    private String secretCode;


    @RequestMapping(value = "/callback", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity confirm(
            @RequestBody VkNotification notification,
            HttpServletRequest request,
            HttpServletResponse response ) {
        String reqType = notification.getType();
        switch (reqType) {
            case "confirmation":
                if(secretCode != null && !secretCode.isEmpty()) {
                    if (!secretCode.equals(notification.getSecret()))
                        return ResponseEntity.status(500).body("Wrong secret key");
                }

                return ResponseEntity.ok().body(confirmationCode);
            case "message_new":
                String res = vkService.sendMessage(notification);
                return ResponseEntity.ok().body(res);
            default:
                return ResponseEntity.status(400).body("Unsupported object type");
        }
    }
}
