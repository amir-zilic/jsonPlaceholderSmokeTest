package utilities;

import io.cucumber.java.it.Ma;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class User {
    private Integer userId;
    private int postId;
    private Integer albumId;
    private int commentId;
    private int profilePictureId;
    private String name;
    private String username;
    private String email;
    private String phone;
    private final String website = "randomSite.com";
    private final String postTitle = "Message oof the day";
    private final String postBody = "Hellu orld!";
    private final String commentBody = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam efficitur nisl ac interdum interdum";
    private final String albumTitle =  "Just me!";
    private final String pictureTitle = "My new profile pic";
    private final String pictureUrl = "https://via.placeholder.com/600/6dd9cb";
    private final String newPostBody = "Hello world!";
    private final String newPostTitle = "Message of the day";

    public JSONObject signInInfo(){
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("username",username);
        map.put("email",email);
        map.put("phone",phone);
        map.put("website",website);
        return new JSONObject(map);
    }

    public JSONObject newPost(){
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("title",postTitle);
        map.put("body",postBody);
        return new JSONObject(map);
    }

    public JSONObject newComment(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", username);
        map.put("email", email);
        map.put("body", commentBody);
        return new JSONObject(map);
    }

    public JSONObject newAlbum(){
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("title",albumTitle);
        return new JSONObject(map);
    }

        public JSONObject newPhoto(){
        Map<String,Object> map = new HashMap<>();
        map.put("title",pictureTitle);
        map.put("url",pictureUrl);
        return new JSONObject(map);
    }

        public JSONObject updatedPost(){
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("title",newPostTitle);
        map.put("body",newPostBody);
        return new JSONObject(map);
    }
}
