
package com.userservice.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userservice.Enum.BloodGroup;
import com.userservice.Enum.Gender;
import com.userservice.Model.User;
import com.userservice.Model.UserDTO;
import com.userservice.Service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetUsers()throws Exception {
        List<UserDTO> UserDTO = createUserList();

        Mockito.when(userService.allUser(1, 2)).thenReturn(UserDTO);

        mockMvc.perform(get("/users?page=1&pageSize=2"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", Matchers.is("Nikhil")));
    }



    private List<UserDTO> createUserList() {
        List<UserDTO> users = new ArrayList<>();

        UserDTO user1 = new UserDTO();
        user1.setUserID("A123A");
        user1.setFirstName("Nikhil");
        user1.setMiddleName("J");
        user1.setLastName("S");
        user1.setPhoneNumber("9090909090");
        user1.setEmail("nikhil@gamil.com");
        user1.setEmployeeNumber("12345");
        user1.setBloodGroup(BloodGroup.O_POS);
        user1.setGender(Gender.MALE);

        UserDTO user2 = new UserDTO();
        user2.setUserID("A123A");
        user2.setFirstName("Nikhil");
        user2.setMiddleName("dev");
        user2.setLastName("fit");
        user2.setPhoneNumber("7896314520");
        user2.setEmail("nikhil@gamil.com");
        user2.setEmployeeNumber("12345");
        user2.setBloodGroup(BloodGroup.O_POS);
        user2.setGender(Gender.MALE);

        users.add(user1);
        users.add(user2);
        return users;
    }
    private static User createOneUserForEmail() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2022-06-28");
        User user = new User("1", "Nikhil", "Arun",
                "nik", "1023456789", c, Gender.MALE,
                "Ngp", "123", BloodGroup.A_POS, "nikhil@gamil.com", "789456");
        return user;
    }

    @Test
    public void testGetUserByID() throws Exception {
        UserDTO UserDTO = createOneUser();

        Mockito.when(userService.findID("1")).thenReturn(UserDTO);

        mockMvc.perform(get("/users/1"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.aMapWithSize(11)))
                .andExpect(jsonPath("$.firstName", Matchers.is("Nikhil")));
    }

    private UserDTO createOneUser() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        UserDTO user1 = new UserDTO();

        user1.setUserID("1");
        user1.setFirstName("Nikhil");
        user1.setMiddleName("Dev");
        user1.setLastName("Sid");
        user1.setPhoneNumber("7896314520");
        user1.setEmail("nikhil@gamil.com");
        user1.setDateOfBirth(c);
        user1.setEmployeeNumber("12345");
        user1.setBloodGroup(BloodGroup.O_POS);
        user1.setGender(Gender.MALE);
        return user1;
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        User User = createOneUserForEmail();

        Mockito.when(userService.userEmail("nikhil@gamil.com")).thenReturn(User);

        mockMvc.perform(get("/users/getUserByEmail/nikhil@gamil.com"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.aMapWithSize(12)))
                .andExpect(jsonPath("$.firstName", Matchers.is("Nikhil")));
    }

    @Test
    public void testDeleteUser() throws Exception {

        Mockito.when(userService.userDeleteById("")).thenReturn("Deleted");
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());

    }

    @Test
    public void testCreateUser() throws Exception {
        User user = createOneUserToPost();

        UserDTO UserDTO = createUserDTO();
        User userRequest = new User();
        Mockito.when(userService.addUser(userRequest)).thenReturn(UserDTO);
        mockMvc.perform(post("/users")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    private UserDTO createUserDTO() {
        UserDTO user = new UserDTO();
        user.setUserID("1");
        user.setFirstName("nik");
        user.setMiddleName("Liv");
        user.setLastName("Devil");
        user.setPhoneNumber("1245789630");
        user.setEmail("nikhil@gamil.com");
        user.setEmployeeNumber("12345");
        user.setBloodGroup(BloodGroup.O_POS);
        user.setGender(Gender.MALE);
        return user;
    }

    private User createOneUserToPost() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        User user = new User();
        user.setUserID("1");
        user.setFirstName("Nikhil");
        user.setMiddleName("N");
        user.setLastName("S");
        user.setPhoneNumber("9090909090");
        user.setEmail("nikhil@gamil.com");
        user.setDateOfBirth(c);
        user.setAddress("Ram ");
        user.setEmployeeNumber("12345");
        user.setBloodGroup(BloodGroup.O_POS);
        user.setGender(Gender.MALE);
        user.setPassword("12345");

        return user;
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = createOneUserToPost();
        UserDTO UserDTO = new UserDTO();
        User userRequest = new User();
        Mockito.when(userService.update(userRequest, "")).thenReturn(UserDTO);
        mockMvc.perform(put("/users/1")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

}
