package starter.JSONplaceholder;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

public class newUserAccountCreationAndBasicUseSteps {

    @Steps
    PlaceholderAPI placeholderAPI;

    @Given("user creates an account")
    public void createNewUserAccountAndVerify(){
        placeholderAPI.postNewUser();
    }

    @When("user creates a post")
    public void createNewPost(){placeholderAPI.postNewPost();}

    @Then("user comments on their post, creates an album and uploads a photo")
    public void commentOnPost(){
        placeholderAPI.addComment();
        placeholderAPI.createAlbum();
        placeholderAPI.uploadPhoto();
    }

    @Then("user is able too see their posts, comments and photos")
    public void checkAccountDetails(){
        placeholderAPI.getAccount();
        placeholderAPI.getComment();
        placeholderAPI.getUserPhoto();
    }

    @Then("user updates their post")
    public void updateUserPost(){ placeholderAPI.updatePost();}

    @Then("user deletes their account")
    public void deleteUserAccount(){ placeholderAPI.deleteAccount();}
}
