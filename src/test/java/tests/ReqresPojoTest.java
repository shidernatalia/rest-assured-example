package tests;

import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import specs.Specifications;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ReqresPojoTest {
    private final static String URL = "https://reqres.in";

    /**
     * 1. Send List Users GET request /api/users?page=2. Response body that given test API always returns is can be found
     * on reqres.in and corresponding POJO class is UserData
     * 2. Validate that avatar file name and users' names coincide
     * 3. Validate that users' emails end with reqres.in
     */
    @Test
    public void checkAvatarTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(200));
        List<UserData> users = given()
                .when()
                .get("/api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);

        users.forEach(user -> Assertions.assertTrue(user.getAvatar().contains(user.getId().toString())));
        Assertions.assertTrue(users.stream().allMatch(user -> user.getEmail().endsWith("@reqres.in")));

        List<String> avatars = users.stream().map(UserData::getAvatar).toList();
        List<String> ids = users.stream().map(user -> user.getId().toString()).toList();
        for (int i = 0; i < avatars.size(); i++) {
            Assertions.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }

    /**
     * Test successful user registration in the system.
     * 1. Send Register Successful POST request with JSON body
     * {
     * "email": "eve.holt@reqres.in",
     * "password": "pistol"
     * }
     * 2. Validate that registration is successful. Response that given test API always returns is
     * {
     * "id": 4,
     * "token": "QpwL5tke4Pnpja7X4"
     * }
     * 3. Registration is not successful because there is no password.
     * 4. Validate codes of errors
     */
    @Test
    public void successfulRegistrationTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(200));
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        RegisterUser user = new RegisterUser("eve.holt@reqres.in", "pistol");
        SuccessfulRegister successfulRegister = given()
                .body(user)
                .when()
                .post("/api/register")
                .then().log().all()
                .extract().as(SuccessfulRegister.class);

        Assertions.assertNotNull(successfulRegister.getId());
        Assertions.assertNotNull(successfulRegister.getToken());

        Assertions.assertEquals(id, successfulRegister.getId());
        Assertions.assertEquals(token, successfulRegister.getToken());
    }

    /**
     * Test unsuccessful user registration in the system.
     * 1. Send Register Unsuccessful POST request with JSON body
     * {
     * "email": "sydney@fife"
     * }
     * Given test API always returns following respose
     * {
     * "error": "Missing password"
     * }
     * 2. Validate the response code is 400
     * 3. Validate the error message in response body
     */
    @Test
    public void unSuccessfulRegistrationTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(400));
        RegisterUser user = new RegisterUser("sydney@fife", "");
        UnSuccessfulRegister unSuccessfulRegister = given()
                .body(user)
                .post("/api/register")
                .then().log().all()
                .extract().as(UnSuccessfulRegister.class);

        Assertions.assertEquals("Missing password", unSuccessfulRegister.getError());
    }

    /**
     * 1. Send List <Resource> GET Request: api/unknown
     * 2. Validate that the response contains data sorted by years
     */
    @Test
    public void sortedYearsTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(200));
        List<ColorsData> colorsData = given()
                .when()
                .get("api/unknown")
                .then().log().all()
                .extract().body().jsonPath().getList("data", ColorsData.class);

        List<Integer> years = colorsData.stream().map(ColorsData::getYear).collect(Collectors.toList());
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());

        Assertions.assertEquals(sortedYears, years);
    }

    /**
     * 1. Send DELETE request to delete a user with id = 2: /api/users/2
     * 2. Validate that response code is 204 (success status response code indicates that a request has succeeded)
     */
    @Test
    public void deleteUserTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(204));
        given()
                .when()
                .delete("/api/users/2")
                .then().log().all();
    }


}
