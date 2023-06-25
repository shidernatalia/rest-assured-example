package tests;

import model.retrofit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tests.endpoints.UserService;

import java.io.IOException;
import java.util.ArrayList;

public class RetrofitTest {
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://reqres.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private final UserService userService = retrofit.create(UserService.class);

    @Test
    public void listUsersTest() throws IOException {
        int page = 1;
        userService.getUserList(page).execute();
        Response<ListUsersRootResponse> response = userService.getUserList(page).execute();
        Assertions.assertTrue(response.isSuccessful());
        ListUsersRootResponse body = response.body();
        Assertions.assertEquals(page, body.getPage());
        ArrayList<UserResponse> data = body.getData();
        Assertions.assertTrue(data.size() > 0);
    }

    @Test
    public void getUserByIdTest() throws IOException {
        int id = 2;
        Response<SingleUserResponse> response = userService.getUserById(id).execute();
        Assertions.assertTrue(response.isSuccessful());
        SingleUserResponse body = response.body();
        Assertions.assertEquals(id, body.getData().getId());
    }

    @Test
    public void testCreateUser() throws IOException {
        String correctTimePattern = """
                [a-zA-Z]{3}\\s[a-zA-Z]{3}\\s\\d{2}\\s\\d{2}:\\d{2}:\\d{2}\\s[a-zA-Z]{4}\\s\\d{4}""";
        String name = "Test Name";
        String job = "Test Job";
        UserCreateRequest userCreateRequest = new UserCreateRequest(name, job);

        Response<UserCreateResponse> response = userService.createUser(userCreateRequest).execute();
        Assertions.assertTrue(response.isSuccessful());

        UserCreateResponse body = response.body();
        Assertions.assertEquals(name, body.getName());
        Assertions.assertEquals(job, body.getJob());
        Assertions.assertTrue(body.getCreatedAt().toString().matches(correctTimePattern));
    }

    @Test
    public void testUpdateUser() throws IOException {
        String name = "morpheus";
        String job = "zion resident";

        UserCreateRequest userCreateRequest = new UserCreateRequest(name, job);
        Response<UpdateUserResponse> response = userService.updateUser(2, userCreateRequest).execute();

        Assertions.assertTrue(response.isSuccessful());
        Assertions.assertTrue(isTimePatternCorrect(response.body().getUpdatedAt().toString()));
    }

    @Test
    public void testDeleteUser() throws IOException {
        Response<Void> response = userService.deleteUserById(4).execute();
        Assertions.assertTrue(response.isSuccessful());
        Assertions.assertEquals(204, response.code());
    }

    private boolean isTimePatternCorrect(String time) {
        return time.matches("""
                [a-zA-Z]{3}\\s[a-zA-Z]{3}\\s\\d{2}\\s\\d{2}:\\d{2}:\\d{2}\\s[a-zA-Z]{4}\\s\\d{4}""");
    }
}
