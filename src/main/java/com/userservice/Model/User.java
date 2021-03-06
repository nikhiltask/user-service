package com.userservice.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.userservice.Enum.BloodGroup;
import com.userservice.Enum.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;


@Document(collection = "UserService")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class User {

    @Id
    private String userID;

    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Middle name is required")
    private String middleName;

    @NotEmpty(message = "Last name is required")
    private String lastName;

    @NotEmpty(message = "Phone Number is required")
    @Size(min=10,max = 10)
    private String phoneNumber;

    @NotNull(message = "Date of Birth is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    private Gender gender;

    @NotEmpty(message = "Address Status is required")
    private String address ;

    @NotEmpty(message = "Employee Number is required")
    private String employeeNumber;


    private BloodGroup bloodGroup;

    @NotEmpty(message = "Email is required")
    @Pattern(regexp = "^[\\wA-Za-z0-9]+(?:\\.[\\wA-Za-z0-9+_-]+[A-Za-z0-9]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "please enter proper email")
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;

}
