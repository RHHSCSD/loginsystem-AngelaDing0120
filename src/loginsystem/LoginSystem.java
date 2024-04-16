/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package loginsystem;

// imports used
import java.io.*;
import java.util.*;
import java.security.*;

/**
 * 2024/04/10
 * @author Angela Ding
 * ICS4U
 */
// ALL SYSTEM PRINTING IS FOR ME WHEN TESTING
public class LoginSystem {

    /**
     * Testing before creating an interface for the system
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // creating each class inorder to use
        LoginSystem i = new LoginSystem();
        
//        // testing
//        i.login("selinahong","100916");
//        i.register("Angela","15","12345678","Angelading0120","100916");
//        System.out.println(i.passwordStrength("Angelading0120"));
    }
    
    // opening the file (so I wouldn't have to open it in every method
    File userFile = new File("userFile.txt");
    
    /**
     * Registers a new user with their name, age, phone number, username, and password. 
     * Includes making sure the username is unique, no spaces
     * Includes making sure the password is strength and not in a list of weak passwords.
     * If the user registration is successful, the user's information would be stored into a file
     * 
     * @param name      first name of the new user
     * @param age       age of the new user
     * @param phoneNum  phone number of the new user
     * @param username  chosen username from the new user
     * @param password  chosen password from the new user
     * @return  An integer for the status of the registration for the future use in interface.
     *          1 if username exists, 2 if username has space include, 3 if password is weak, 4 if password is on the week list, 100 if successful, -1 if an IO exception occurs. 
     */
    // for a user to regester 
    public int register(String name, String age, String phoneNum, String username, String password){
        // check if there are any invalid in username
        if (usernameUnique(username) == false){
            return 1;
            // if the username already exists
        }
        else if (usernameSpace(username) == false){
            return 2;
            // if there is a space in the username
        }
        if (passwordStrength(password) == false){
            return 3;
            // if the password doesn't meet the currcumstances
        }
        else if(weakPasswordFile(password) == false){
            return 4;
        }
        // if everything is good and the user could create an account and store it inside the file
        else{
            // to create a salt for the new user
            String salt = salt();
            // salt and encryp the password given 
            password+=salt;
            password = encryp(password);
            // create new user
            User user1 = new User(name,age,phoneNum,username,password,salt); 
            try{
                // create a writer to append into file
                PrintWriter input = new PrintWriter(new FileWriter(userFile,true));
                // append to the file
                input.println(user1);
                // close the file
                input.close();
                return 100;
            }
            // catch any IOExceptions from using the file
            catch(IOException e){
                System.out.println("Error! Soemthing went wring");
                return -1;
            }
        }
    }
    
    /**
     * Checks if the username is unique among all users. 
     * Check that the username is not taken by others already or already existing
     * @param username      username to check for uniqueness
     * @return      True if the username is unique.
     *              False if it was found in the existing user's username.
     */
    public boolean usernameUnique(String username){
        // create an array for all the users
        ArrayList<User> allUsers = loadAllUser();
        // run through all the users and see if there username is the same as the one entered
        for (int i=0; i<allUsers.size(); i++){
            if(username.equals(allUsers.get(i).getUsername())){
                // if the same username was found
                return false;
            }
        }
        // if the username entered is not exisiting (could be used)
        return true;
    }
    
    /**
     * Checks if there are any spaces in the username (as spaces are not allowed in usernames).
     * @param username      username to check for spaces.
     * @return      True if there are no spaces
     *              False if spaces are found
     */
    public boolean usernameSpace(String username){
        // see if there is a space contained inside the username 
        if(username.contains(" ")){
            return false;
        }
        // if there are no spaces contained inside the username
        return true;
    }
    
    /**
     * Checks if the given password is strong based on the length and diverse characters.
     * Strong password needs to at least include one of each of lower case, upper case, numbers, and may not include spaces.
     * @param password      password to check
     * @return      True if password is strong.
     *              False if password is week
     */
    // to check if the password is strong enough
    public boolean passwordStrength(String password){
        // declare variables for future use
        boolean upper = false;
        boolean lower = false;
        boolean num = false;     
        // check if there is spaces contained insdie password
        if(password.contains(" ")){
            return false;
        }
        // check if the password has at least 8 letters
        else if(password.length() < 8){
            return false;
        }
        // checking letters inside the password
        for (int i=0; i<password.length(); i++){
            char letter = password.charAt(i);
            // if the character is upper case
            if(Character.isUpperCase(letter)){
                upper = true;
            }
            // if the character is lower case
            if(Character.isLowerCase(letter)){
                lower = true;
            }
            // if the character is number
            if(Character.isDigit(letter)){
                num = true;
            }
        }
        // check if the letters a least include 1 uppercase, 1 lowercase, and 1 number
        if (!upper||!lower||!num){
            return false;
        } 
        // if the password is a strong password
        return true;
    }
    
    /**
     * Checks if the given password is contained in a list of weak passwords
     * @param password      password to check
     * @return      True if the password is not in the list of weak passwords
     *              False if the password is in the list of weak passwords
     */
    // to check if the password is contained in the bad password file
    public boolean weakPasswordFile(String password){
        // open up the file
        File weak = new File("dictbadpass.txt");
        try{
            // open a scanner to read inside file
            Scanner find = new Scanner(weak);
            // while there is a next line in the file
            while(find.hasNextLine()){
                // read in the line of password
                String word = find.nextLine();
                // if password entered equals to bad password in file
                if (password.equals(word)){
                    return false;
                }
            }
            // close the file
            find.close();
        }
        // catch any IOExceptions from using the file
        catch(IOException e){
            System.out.println("Error! Soemthing went wring");
        } 
        // if the password is not in the bad password file
        return true;
    }
   
    /**
     * Loads all users from a file into an array list and returns it back for future uses dealing with existing users.
     * @return      An array list with all the User objects with loaded user information
     */
    // for loading all information into an array for use in looking inside the file and operations that need to loop inside the file
    public ArrayList<User> loadAllUser(){
        // create an array list
        ArrayList<User> Users = new ArrayList<User>();
        try{
            // create a scanner for use inside file
            Scanner find = new Scanner(userFile);
            // for all the way to the last line of the file
            while (find.hasNextLine()){
                // read in a whole line
                String info = find.nextLine(); 
                // split information based off of a space
                String[] infoSplit = info.split(" ");
                // create a new user using the split informations from each line
                User infoUser = new User(infoSplit);
                // adding the new user to the arraylist
                Users.add(infoUser);
            }
            // close the scanner
            find.close();
        }
        // catch any IOExceptions from using the file
        catch(IOException e){
            System.out.println("Error! Soemthing went wring");
        }  
        // gives the whole arraylist with all the user organized for use in operations
        return Users;
    }
    
    /**
     * Log user in with provided username and password.
     * Includes making sure the password is correct.
     * Includes making sure the username actually exists.
     * 
     * @param username      username for login
     * @param password      password for login
     * @return      An integer for the status of login attempt.
     *              1 if the username doesn't exist, 2 if the password is wrong, 100 if successful.
     */
    // check if user can login
    public int login(String username, String password){
        // declare variable for future use
        boolean success = false;
        // create an array list from a method that cold all users in the file
        ArrayList<User> allUsers = loadAllUser();
        
        // run through all the users in the array
        for (int i=0; i<allUsers.size(); i++){
            // if the username entered is paired with a username in the file
            if(username.equals(allUsers.get(i).getUsername())){
                // ad the salt stored in the file already and encryp after salting 
                password+=allUsers.get(i).getSalt();
                password = encryp(password);
                // if the password after salting and encrpting is paired with the file as well
                if(password.equals(allUsers.get(i).getPassword())){
                    // also included in the interface
                    System.out.println("Welcome back, user");
                    return 100;
                }
                else{
                    // if the username is paired but the password is not
                    // also included in the interface
                    System.out.println("Wrong password...");
                    return 2;
                }
            }
        }
        // if the username couldn't be found
        return 1;
    }
    
    /**
     * Encrypt the given password using SHA-512 hash algorithm.
     * @param password      password to encrypt
     * @return      The encrypted password as a hexadecimal string
     */
    // method for encrpting the password
    public String encryp(String password){
        // declare variable
        String encryptedPassword = "";
        try{
            // java helper class to perform encryption
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            // give the helper function the password
            md.update(password.getBytes());
            // perform the encryption
            byte byteData[] = md.digest();
            // to express the byte data as a hexadecimal number
            for (int i=0; i<byteData.length; ++i){
                encryptedPassword += (Integer.toHexString((byteData[i] & 0xFF) | 0x100).substring(1,3));
            }
        }
        // catch any exception from using the file
        catch(NoSuchAlgorithmException e){
            System.out.println("Error! Soemthing went wring");
        }  
        // return the password that is encrypted
        return encryptedPassword;
    }
    
    /**
     * Generate a random salt that is 4 letters long used for password hashing
     * @return      a random 4 letter salt string 
     */
    // method for creating a salt for the password
    public String salt(){
        // create random generator
        Random random = new Random();
        // declaring variable
        String salted = "";
        // generating four random letters or numbers or signs 
        for (int i=0; i<4; i++){
            double rand = random.nextInt(100)+33;
            // adding each random together into the four digit salt
            salted+=(char)rand;
        }
        // return a salt for the password
        return salted;
    }
}
