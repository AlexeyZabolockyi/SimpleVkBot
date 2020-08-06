package bot.services;

import bot.models.VkNotification;

public interface VkService {

    String sendMessage(VkNotification notification);
}
