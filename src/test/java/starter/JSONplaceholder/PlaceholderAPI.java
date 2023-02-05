package starter.JSONplaceholder;

import io.cucumber.java.BeforeStep;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.checkerframework.checker.units.qual.C;
import org.testng.Assert;
import utilities.User;
import utilities.Utils;

import static io.restassured.RestAssured.*;

public class PlaceholderAPI {

    public static User newUser = User.builder().name(Utils.randomFullName()).username(Utils.randomUsername()).email(Utils.randomEmail()).phone(Utils.randomPhoneNumber()).build();

    @BeforeStep
    public void preCondition() {baseURI = Utils.BASE_URL;}

    @Step("POST new user account details")
    public void postNewUser() {
        Response response = SerenityRest.given().
                contentType(ContentType.JSON).
                body(newUser.signInInfo().toJSONString()).
                when().
                post("/users");

        Assert.assertEquals(response.getStatusCode(),201,Utils.responseError);
        newUser.setUserId(response.jsonPath().get("id"));
        Assert.assertEquals(response.jsonPath().get("name"),newUser.getName(),Utils.badResponseAPI("name"));
        Assert.assertEquals(response.jsonPath().get("username"),newUser.getUsername(),Utils.badResponseAPI("username"));
        Assert.assertEquals(response.jsonPath().get("email"),newUser.getEmail(),Utils.badResponseAPI("email"));
        Assert.assertEquals(response.jsonPath().get("phone"),newUser.getPhone(),Utils.badResponseAPI("phone"));
        Assert.assertEquals(response.jsonPath().get("website"),newUser.getWebsite(),Utils.badResponseAPI("website"));
    }

    @Step("POST new media post")
    public void postNewPost() {
       Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .body(newUser.newPost().toJSONString())
                .when()
                .post("/posts");

        Assert.assertEquals(response.getStatusCode(),201,Utils.responseError);
        newUser.setPostId(response.jsonPath().get("id"));
        Assert.assertEquals(response.jsonPath().get("title"),newUser.getPostTitle(),Utils.badResponseAPI("title"));
        Assert.assertEquals(response.jsonPath().get("body"),newUser.getPostBody(),Utils.badResponseAPI("body"));
    }

    @Step("POST new comment")
    public void addComment() {
        Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .pathParams("postId",newUser.getPostId())
                .body(newUser.newComment().toJSONString())
                .post("/posts/{postId}/comments");

        Assert.assertEquals(response.getStatusCode(),201,Utils.responseError);
        newUser.setCommentId(response.jsonPath().get("id"));
        Assert.assertEquals(response.jsonPath().get("name"),newUser.getUsername(),Utils.badResponseAPI("name"));
        Assert.assertEquals(response.jsonPath().get("email"),newUser.getEmail(),Utils.badResponseAPI("email"));
        Assert.assertEquals(response.jsonPath().get("body"),newUser.getCommentBody(),Utils.badResponseAPI("body"));
    }

    @Step("POST new album")
    public void createAlbum() {
        Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .body(newUser.newAlbum().toJSONString())
                .post("/albums");

        Assert.assertEquals(response.getStatusCode(),201,Utils.responseError);
        newUser.setAlbumId(response.jsonPath().get("id"));
        Assert.assertEquals(response.jsonPath().get("userId"),newUser.getUserId(),Utils.badResponseAPI("userId"));
        Assert.assertEquals(response.jsonPath().get("title"),newUser.getAlbumTitle(),Utils.badResponseAPI("title"));
    }

    @Step("POST new photo")
    public void uploadPhoto() {
        Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .pathParams("albumId",newUser.getAlbumId())
                .body(newUser.newPhoto().toJSONString())
                .post("/albums/{albumId}/photos");

        Assert.assertEquals(response.getStatusCode(),201,Utils.responseError);
        newUser.setProfilePictureId(response.jsonPath().get("id"));
        Assert.assertEquals(response.jsonPath().get("title"),newUser.getPictureTitle(),Utils.badResponseAPI("title"));
        Assert.assertEquals(response.jsonPath().get("url"),newUser.getPictureUrl(),Utils.badResponseAPI("url"));
        Assert.assertEquals(response.jsonPath().get("albumId"),String.valueOf(newUser.getAlbumId()),Utils.badResponseAPI("albumId"));
    }

    @Step("PUT uploaded media post")
    public void updatePost() {
        Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .pathParams("postId",newUser.getPostId()-1)
                .body(newUser.updatedPost().toJSONString())
                .put("/posts/{postId}");

        Assert.assertEquals(response.getStatusCode(),200,Utils.responseError);
        Assert.assertEquals(response.jsonPath().get("userId"),newUser.getUserId(),Utils.badResponseAPI("userId"));
        Assert.assertEquals(response.jsonPath().get("title"),newUser.getNewPostTitle(),Utils.badResponseAPI("title"));
        Assert.assertEquals(response.jsonPath().get("body"),newUser.getNewPostBody(),Utils.badResponseAPI("body"));
    }

    @Step("GET account info")
    public void getAccount() {
        Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .pathParams("userId",newUser.getUserId()-1)
                .get("/users/{userId}");

        Assert.assertEquals(response.getStatusCode(),200,Utils.responseError);
        Assert.assertEquals(response.jsonPath().get("id"),new Integer(newUser.getUserId()-1),Utils.badResponseAPI("userId"));
    }

    @Step("GET user comment")
    public void getComment(){
        Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .pathParams("postId",newUser.getPostId()-1)
                .queryParam("id",newUser.getCommentId()-1)
                .get("/posts/{postId}/comments");

        Assert.assertEquals(response.getStatusCode(),200,Utils.responseError);
        Assert.assertEquals(response.jsonPath().getString("[0].id"),String.valueOf(newUser.getCommentId()-1),Utils.badResponseAPI("commentId"));
        Assert.assertEquals(response.jsonPath().getString("[0].postId"),String.valueOf(newUser.getPostId()-1),Utils.badResponseAPI("postId"));
    }

    @Step("GET user profile photo")
    public void getUserPhoto() {
        Response response = SerenityRest.given()
                .contentType(ContentType.JSON)
                .pathParams("albumId",newUser.getAlbumId()-1)
                .queryParam("id","5000")
                .get("/albums/{albumId}/photos");

        Assert.assertEquals(response.getStatusCode(),200,Utils.responseError);
        Assert.assertEquals(response.jsonPath().getString("[0].url"),newUser.getPictureUrl(),Utils.badResponseAPI("url"));
    }

    @Step("DELETE user account")
    public void deleteAccount() {
        SerenityRest.given()
                .pathParams("userId",newUser.getUserId())
                .delete("/users/{userId}")
                .then()
                .statusCode(200);
    }
}
