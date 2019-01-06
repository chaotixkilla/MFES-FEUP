package model;
import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class User {
  private String username;
  private String email;
  private String password;

  public void cg_init_User_1(final String name, final String mail, final String pass) {

    username = name;
    email = mail;
    password = pass;
    return;
  }

  public User(final String name, final String mail, final String pass) {

    cg_init_User_1(name, mail, pass);
  }

  public String getUsername() {

    return username;
  }

  public String getEmail() {

    return email;
  }

  public String getPassword() {

    return password;
  }

  public User() {}

  public String toString() {

    return "User{"
        + "username := "
        + Utils.toString(username)
        + ", email := "
        + Utils.toString(email)
        + ", password := "
        + Utils.toString(password)
        + "}";
  }
}
