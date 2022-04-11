package com.userservice.Service;
import com.userservice.ConstantFiles.ConstantNames;
import com.userservice.Enum.BloodGroup;
import com.userservice.Enum.Gender;
import com.userservice.Exception.UserNotFoundException;
import com.userservice.Model.User;
import com.userservice.Model.UserDTO;
import com.userservice.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;


    @Test
    void CreateUser() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        UserDTO userWithOutPassword = createOneUserToResponse();
        User userRequest = createOneUserRequestToPost();
        User user = new User();
        user.setFirstName("firstTest");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("741852963");
        user.setEmail("nikhil@gmail.com");
        user.setDateOfBirth(c);
        user.setEmployeeNumber("12345");
        user.setBloodGroup(BloodGroup.O_POS);
        user.setGender(Gender.MALE);
        user.setPassword("1234");

        when(userRepository.save(any(User.class))).thenReturn(user);
        User savedUser = userRepository.save(user);
        when(userService.addUser(userRequest)).thenReturn(userWithOutPassword);
        assertThat(savedUser.getFirstName()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("nikhil@gmail.com");
    }

    @Test
    void ExceptionThrownWhenEmailAlreadyRegistered() throws ParseException {
        User user = createOneUserToCheck();
        User user1 = createOneUserToUpdate();
        User userRequest = new User();
        User ofResult = user1;
        when(this.userRepository.save((User) any())).thenReturn(user);
        when(this.userRepository.findByemail((String) any())).thenReturn(ofResult);
        assertThrows(UserNotFoundException.class, () -> this.userService.addUser(userRequest));
        verify(this.userRepository).findByemail((String) any());
    }

    @Test
    void DeleteUserById() throws Exception {
        User user=createOneUserToCheck();
        when(this.userRepository.findById("1")).thenReturn(Optional.of(user));
        doNothing().when(this.userRepository).deleteById("1");
        when(this.userRepository.findById("1")).thenReturn(Optional.of(user));
        assertThat(this.userService.userDeleteById("1"));
        assertThrows(UserNotFoundException.class, () -> this.userService.userDeleteById("2"));
    }

    @Test
    void ExceptionThrownWhenUserNotFound() {
        Mockito.doThrow(UserNotFoundException.class).when(userRepository).deleteById(any());
        Exception userNotFoundException = assertThrows(UserNotFoundException.class, () -> userService.userDeleteById("1"));
        assertTrue(userNotFoundException.getMessage().contains(ConstantNames.ERROR_CODE));
    }

    @Test
    void testExceptionThrownWhenUserIdNotFound() throws Exception {
        User user1 = createOneUserToUpdate();
        UserDTO UserWithOutPassword = createOneUserToResponse();
        User userRequest = createOneUserRequestToPost();
        Mockito.when(userRepository.findById("1")).thenReturn(Optional.ofNullable(user1));
        assertThat(userService.update(userRequest, "1")).isEqualTo(UserWithOutPassword);
        assertThrows(UserNotFoundException.class, () -> userService.update(userRequest, null));
    }

    @Test
    void testGetUserById() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        User user = new User();
        user.setUserID("1");
        user.setFirstName("firstTest");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("741852963");
        user.setEmail("nikhil@gmail.com");
        user.setDateOfBirth(c);
        user.setEmployeeNumber("12345");
        user.setBloodGroup(BloodGroup.O_POS);
        user.setGender(Gender.MALE);
        user.setPassword("1234");

        UserDTO UserWithOutPassword = createOneUserToResponse();
        Mockito.when(userRepository.findById("1")).thenReturn(Optional.ofNullable(user));
        assertThat(userService.findID(user.getUserID())).isEqualTo(UserWithOutPassword);
        assertThrows(UserNotFoundException.class, () -> userService.findID(null));
    }

    @Test
    void testGetUserDetailsByEmail() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        User user = new User();
        user.setUserID("1");
        user.setFirstName("firstTest");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("7894561230");
        user.setEmail("nikhil@gmail.com");
        user.setDateOfBirth(c);
        user.setEmployeeNumber("12345");
        user.setBloodGroup(BloodGroup.O_POS);
        user.setGender(Gender.MALE);
        user.setPassword("1234");

        UserDTO UserWithOutPassword = createOneUserToResponse();
        Mockito.when(userRepository.findByemail("nikhil@gmail.com")).thenReturn(user);
        assertThat(userService.userEmail(user.getEmail())).isEqualTo(user);
        assertThrows(UserNotFoundException.class, () -> userService.userEmail(null));
    }

    @Test
    void testGetAllUsers() throws ParseException {
        User user = createOneUserToCheck();
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        PageImpl<User> pageImpl = new PageImpl<>(users);

        when(this.userRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        assertEquals(1, this.userService.allUser(1, 3).size());
        verify(this.userRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    private UserDTO createOneUserToResponse() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        UserDTO userWithOutPassword = new UserDTO();
        userWithOutPassword.setUserID("1");
        userWithOutPassword.setFirstName("firstTest");
        userWithOutPassword.setMiddleName("J");
        userWithOutPassword.setLastName("S");
        userWithOutPassword.setPhoneNumber("741852963");
        userWithOutPassword.setEmail("nikhil@gmail.com");
        userWithOutPassword.setDateOfBirth(c);
        userWithOutPassword.setEmployeeNumber("12345");
        userWithOutPassword.setBloodGroup(BloodGroup.O_POS);
        userWithOutPassword.setGender(Gender.MALE);
        return userWithOutPassword;
    }

    private User createOneUserRequestToPost() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        User userRequest = new User();
        userRequest.setUserID("1");
        userRequest.setFirstName("firstTest");
        userRequest.setMiddleName("J");
        userRequest.setLastName("S");
        userRequest.setPhoneNumber("741852963");
        userRequest.setEmail("nikhil@gmail.com");
        userRequest.setDateOfBirth(c);
        userRequest.setEmployeeNumber("12345");
        userRequest.setBloodGroup(BloodGroup.O_POS);
        userRequest.setGender(Gender.MALE);
        userRequest.setPassword("12345");
        return userRequest;
    }


    private User createOneUserToUpdate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        User user = new User();
        user.setUserID("1");
        user.setFirstName("firstTest");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("7894561230");
        user.setEmail("nikhil@gmail.com");
        user.setDateOfBirth(c);
        user.setEmployeeNumber("12345");
        user.setBloodGroup(BloodGroup.O_POS);
        user.setGender(Gender.MALE);
        user.setPassword("1234");

        return user;
    }

    private User createOneUserToCheck() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        User user = new User();
        user.setUserID("1");
        user.setFirstName("firstTest");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("741852963");
        user.setEmail("nikhil@gmail.com");
        user.setDateOfBirth(c);
        user.setEmployeeNumber("12345");
        user.setBloodGroup(BloodGroup.O_POS);
        user.setGender(Gender.MALE);
        user.setPassword("1234");

        return user;
    }


    private List<User> createUsersList1() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        List<User> users = new ArrayList<>();
        User UserWithOutPassword1 = new User();
        UserWithOutPassword1.setUserID("1");
        UserWithOutPassword1.setFirstName("firstTest");
        UserWithOutPassword1.setMiddleName("J");
        UserWithOutPassword1.setLastName("S");
        UserWithOutPassword1.setPhoneNumber("741852963");
        UserWithOutPassword1.setEmail("nikhil@gmail.com");
        UserWithOutPassword1.setDateOfBirth(c);
        UserWithOutPassword1.setEmployeeNumber("12345");
        UserWithOutPassword1.setBloodGroup(BloodGroup.O_POS);
        UserWithOutPassword1.setGender(Gender.MALE);

        User UserWithOutPassword2 = new User();
        UserWithOutPassword2.setUserID("2");
        UserWithOutPassword2.setFirstName("secondTest");
        UserWithOutPassword2.setMiddleName("J");
        UserWithOutPassword2.setLastName("S");
        UserWithOutPassword2.setPhoneNumber("7894561230");
        UserWithOutPassword2.setEmail("nikhil@gmail.com");
        UserWithOutPassword2.setDateOfBirth(c);
        UserWithOutPassword2.setEmployeeNumber("12345");
        UserWithOutPassword2.setBloodGroup(BloodGroup.O_POS);
        UserWithOutPassword2.setGender(Gender.MALE);

        users.add(UserWithOutPassword1);
        users.add(UserWithOutPassword2);
        return users;
    }

    private List<UserDTO> createUsersList() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        List<UserDTO> userWithOutPasswordList = new ArrayList<>();
        UserDTO userWithOutPassword = new UserDTO();
        userWithOutPassword.setUserID("1");
        userWithOutPassword.setFirstName("Nikhil");
        userWithOutPassword.setMiddleName("N");
        userWithOutPassword.setLastName("nik");
        userWithOutPassword.setPhoneNumber("7418529630");
        userWithOutPassword.setEmail("nikhil@gmail.com");
        userWithOutPassword.setDateOfBirth(c);
        userWithOutPassword.setEmployeeNumber("7045");
        userWithOutPassword.setBloodGroup(BloodGroup.O_POS);
        userWithOutPassword.setGender(Gender.MALE);

        userWithOutPasswordList.add(userWithOutPassword);
        return userWithOutPasswordList;
    }
}