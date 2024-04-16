/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginsystem;

/**
 * 2024/04/10
 * @author angela
 * ICS4U - MR.RD
 */
public class User {
    // declared variables
    private String name; //firstName (no spaces - as it will affect how I did the formating and spliting of the file)
    private String age;
    private String phoneNum;
    private String username;
    private String password;
    private String salt;
    
    // constructor for a new account in regestration
    public User(String name, String age, String phoneNum, String username, String password, String salt){
        this.name = name;
        this.age = age;
        this.phoneNum = phoneNum;
        this.username = username;
        this.password = password;
        this.salt = salt;
    }
    
    // constructor for old acccount (not used - for testing in the beginning)
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
    
    // to create each user into file a user when seperating each piece of information in file 
    // which makes it easier for using comparing
    public User(String[] account){
        name = account[0];
        age = account[1];
        phoneNum = account[2];
        username = account[3];
        password = account[4];
        salt = account[5];
    }
    
    // so username could be accessed outside
    public String getUsername(){
        return username;
    }
    
    // so password could be accessed outside
    public String getPassword(){
        return password;
    }
    
    // so salt could be accessed outside
    public String getSalt(){
        return salt;
    }
    
    // the formate I want when printing to the file
    @Override
    public String toString(){
        return (name+" "+age+" "+phoneNum+" "+username+" "+password+" "+salt);
    } 
}
