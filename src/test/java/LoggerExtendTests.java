import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.API.Structures.Models.User;
import org.API.Structures.Models.getUser;
import org.API.Structures.base.BaseSetup;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class LoggerExtendTests extends BaseSetup {

    private static final Logger logger = LogManager.getLogger(LoggerExtendTests.class);

    @Test
    public void getUserAndVerifyDetailsWithSelenium() {
        ExtentTest test = extent.createTest("getUserAndVerifyDetailsWithSelenium", "Test to verify user details");

        Configurator.setRootLevel(Level.INFO);


        // Now INFO level logs and higher (WARN, ERROR, etc.) will be captured
        logger.info("This is an info message");
        logger.warn("This is a warning message");
        logger.error("This is an error message");
        logger.debug("This is a debug message"); // This won't be captured because the logging level is set to INFO

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
        String userUiUrl = "https://example.com/users/" + userId;

        // Logging
        logger.info("User details retrieved successfully.");
        logger.debug("User: " + user.toString());
        logger.info("User UI URL: " + userUiUrl);

        // Extent report logging
        if (statusCode == 200) {
            test.log(Status.PASS, "User details retrieved successfully");
        } else {
            test.log(Status.FAIL, "Failed to retrieve user details");
        }
        extent.flush();
    }
}