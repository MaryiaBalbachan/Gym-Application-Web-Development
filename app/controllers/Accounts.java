/**
 * Accounts controller contains login, signup, authentication and update profile methods
 */


package controllers;
import models.Member;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;


public class Accounts extends Controller {

  //Renders signup page
  public static void signup() {
    Logger.info("Rendering Sign up page");
    render("signup.html");
  }

  //Renders login page
  public static void login() {
    render("login.html");
  }

  /**
   * Method creates a new Member object and saves new member   *
   *
   * @param firstname
   * @param lastname
   * @param gender
   * @param email
   * @param password
   * @param address
   * @param height
   * @param startingweight
   */
  public static void register(String firstname, String lastname, String gender, String email, String password,
                              String address, float height, float startingweight) {
    Logger.info("Registering new user " + email);
    Member member = new Member(firstname, lastname, gender, email, password, address, height, startingweight);
    member.save();
    redirect("/");
  }

  /**
   * Method provides email authentication, looks for a trainer/member email match in the
   * DB, renders member or trainer dashboard or kicks back to the login page if no matches
   * were found
   *
   * @param email
   * @param password
   */
  public static void authenticate(String email, String password) {
    Logger.info("Attempting to authenticate with " + email + ":" + password);
    Trainer trainer = Trainer.findByEmail(email);
    Member member = Member.findByEmail(email);
    if ((member != null) && (member.checkPassword(password) == true)) {
      Logger.info("Authentication successful");
      session.put("logged_in_Memberid", member.id);
      redirect("/dashboard");
    } else if ((trainer != null) && (trainer.checkPassword(password) == true)) {
      Logger.info("Authentication successful");
      session.put("logged_in_Trainerid", trainer.id);
      redirect("/trainerview");
    } else {
      Logger.info("Authentication failed");
      redirect("/login");
    }
  }

  //End session, logout Member/Trainer
  public static void logout() {
    session.clear();
    redirect("/");
  }

  //Returns Member object by memberId that is loggedIn at the moment, otherwise
  //goes back to login

  public static Member getLoggedInMember() {
    Member member = null;
    if (session.contains("logged_in_Memberid")) {
      String memberId = session.get("logged_in_Memberid");
      member = Member.findById(Long.parseLong(memberId));
    } else {
      login();
    }
    return member;
  }

  //Returns Trainer object by trainerId that is loggedIn at the moment, otherwise
  //goes back to login
  public static Trainer getLoggedInTrainer() {
    Trainer trainer = null;
    if (session.contains("logged_in_Trainerid")) {
      String trainerId = session.get("logged_in_Trainerid");
      trainer = Trainer.findById(Long.parseLong(trainerId));
    } else {
      login();
    }
    return trainer;
  }

  /**
   * Method updates member profile, retrieves logged in member information,
   * if form fields are not empty and weight and height fields are not 0,
   * changes to new details, stores in Member object, re displays the page.
   *
   * @param firstname
   * @param lastname
   * @param gender
   * @param email
   * @param password
   * @param address
   * @param height
   * @param startingweight
   */
  public static void updateProfile(String firstname, String lastname, String gender, String email, String password,
                                     String address, float height, float startingweight){
      Logger.info("Updating member profile " + firstname + lastname);

    Member member = getLoggedInMember();

    if (!firstname.equals("")) {
      member.firstname = firstname;
    }
    if (!lastname.equals("")) {
      member.lastname = lastname;
    }
    if (!gender.equals("")) {
      member.gender = gender;
    }
    if (!email.equals("")) {
      member.email = email;
    }
    if (!password.equals("")) {
      member.password = password;
    }
    if (!address.equals("")) {
      member.address = address;
    }
    if(height>0.0){
      member.height = height;}
    if(startingweight>0){
      member.startingweight = startingweight;}
    member.save();
    redirect("/profile");

    }

}

