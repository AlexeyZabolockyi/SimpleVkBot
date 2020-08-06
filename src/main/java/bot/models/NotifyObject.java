package bot.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NotifyObject {

    private Integer id;

    private Long date;

    private Integer out;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("read_state")
    private Integer readState;

    private String title;

    private String body;

    @JsonProperty("owner_ids")
    private List<Integer> ownerIds;
}
