package com.example.farmstock;
//Developed By Pranshu Ranjan
public class User {
    public String Username,Email,Password,Gender,State,City,Address,ProfileImageuRL;

    public User(){

    }

    public User(String username, String email, String password, String gender, String state, String city, String address, String profileImageuRL) {
        Username = username;
        Email = email;
        Password = password;
        Gender = gender;
        State = state;
        City = city;
        Address = address;
        ProfileImageuRL = profileImageuRL;
    }
}
