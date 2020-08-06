package bot.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VkNotification {

    private String type;

    private NotifyObject object;

    @JsonProperty("group_id")
    private Integer groupId;

    private String secret;

}
