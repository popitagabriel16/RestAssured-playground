import com.google.gson.Gson;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.API.Structures.Models.User;
import org.API.Structures.Models.getUser;
import org.API.Structures.Requests.GetRequest;
import org.API.Structures.Requests.PostRequest;
import org.API.Structures.Requests.PutRequest;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Test
{

    private PostRequest apiClient;
    private GetRequest apiClient2;
    private PutRequest apiClient3;
    private WebDriver driver;
    private String url = "https://reqres.in/api";

    @Before
    public void setUp() {
        apiClient = new PostRequest(url);
        apiClient2 = new GetRequest(url);
        apiClient3 = new PutRequest(url);
//        WebDriverManager.chromedriver().setup(); // Set up ChromeDriver using WebDriverManager
//        driver = new ChromeDriver();
    }

//    @org.junit.Test
//    public void testListComparison() {
//        // Sample lists for comparison
//        List<String> expectedList = Arrays.asList("apple", "banana", "orange");
//        List<String> actualList = Arrays.asList("apple", "banana", "orange");
//
//        assertThat(actualList).
//
//        // AssertJ list comparison
//        assertThat(actualList)
//                .isNotNull()
//                .isNotEmpty()
//                .hasSameSizeAs(expectedList)
//                .containsExactlyElementsOf(expectedList);
//
//
//    }


    @org.junit.Test
    public void createUserAndVerifyUi() {
        String createUserEndpoint = "/users";
        User newUser = new User("John", "Engineer");
        String requestBody = new Gson().toJson(newUser);

        Response response = apiClient.createUser(createUserEndpoint, requestBody);

        int statusCode = response.getStatusCode();
        assert statusCode == 201 : "Expected status code 201, but found: " + statusCode;

        String responseBody = response.getBody().asString();
        String userId = JsonPath.from(responseBody).getString("id");

        // String userUiUrl = "https://example.com/users/" + userId;
        // driver.get(userUiUrl);

        // Perform UI actions to locate and verify user details
        // Perform additional verifications on UI elements
    }

    @org.junit.Test
    public void createUserWithLombokBuilder() {
        String createUserEndpoint = "/users";

        User newUser = User.builder()
                .name("Test").job("test").name("testtest")
                .build();
        String requestBody = new Gson().toJson(newUser);

        Response response =
                 given().baseUri(url)
                .body(requestBody)
                .when().post(createUserEndpoint);

        int statusCode = response.getStatusCode();
        assert statusCode == 201 : "Expected status code 201, but found: " + statusCode;

        String responseBody = response.getBody().asString();
        String userId = JsonPath.from(responseBody).getString("id");

        // String userUiUrl = "https://example.com/users/" + userId;
        // driver.get(userUiUrl);

        // Perform UI actions to locate and verify user details
        // Perform additional verifications on UI elements
    }

    @org.junit.Test
    public void getUserAndVerifyDetailsWithSelenium() {
        // Assume userId is retrieved from somewhere
        String userId = "1";
        getUser newUser = new getUser("George");

        // Make GET request to retrieve user information
        Response getUserResponse = apiClient2.getUser("/users/" + userId);

        int statusCode = getUserResponse.getStatusCode();
        assert statusCode == 200 : "Expected status code 200, but found: " + statusCode;

        String responseBody = getUserResponse.getBody().asString();

        getUserResponse.then()
                .body("data.first_name", equalTo(newUser.getName()));
//                .body("job", equalTo(newUser.getJob()));

        User user = new Gson().fromJson(responseBody, User.class);

        // Use Selenium to navigate to UI and verify user details
        String userUiUrl = "https://example.com/users/" + userId;
        //driver.get(userUiUrl);

        // String userNameOnUi = driver.findElement(By.id("user-name")).getText();
        // String userJobOnUi = driver.findElement(By.id("user-job")).getText();

        // assert userNameOnUi.equals(user.getName()) : "User name on UI doesn't match";
        // assert userJobOnUi.equals(user.getJob()) : "User job on UI doesn't match";

        //driver.quit();
    }

    @org.junit.Test
    public void updateUser() {
        //Arrange
        String updateUserEndpoint = "/users/1"; // Assuming the user ID to update is 1
        User updatedUser = new User("UpdatedName", "UpdatedEngineer"); // Update user details here
        String requestBody = new Gson().toJson(updatedUser);

        // Act: Send PUT request to update user
        Response response = apiClient3.updateUser(updateUserEndpoint, requestBody);

        // Assert: Verify the response
        int statusCode = response.getStatusCode();
        assert statusCode == 200 : "Expected status code 200, but found: " + statusCode;

        // Optionally, you can retrieve and verify the updated user details from the response
        String responseBody = response.getBody().asString();
        User updatedUserResponse = new Gson().fromJson(responseBody, User.class);
        // Perform assertions on updatedUserResponse if needed
    }

}
