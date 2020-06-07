/**
 * Member model stores Member object fields as well as ArrayList of Assessments.
 */
package models;
import play.db.jpa.Model;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends Model{
  public String firstname;
  public String lastname;
  public String gender;
  public String email;
  public String password;
  public String address;
  public float height;
  public float startingweight;
  public int bmi;



  @OneToMany(cascade = CascadeType.ALL)
  public List<Assessment> assessments = new ArrayList<Assessment>();

  public Member(String firstname, String lastname, String gender,String email, String password,String address,
                float height,float startingweight)
  {
    this.firstname=firstname;
    this.lastname=lastname;
    setGender(gender);
    this.email=email;
    this.password=password;
    this.address=address;
    this.height=height;
    this.startingweight=startingweight;
  }

  //User input validation
  public void setGender(String gender) {
    if((gender.equalsIgnoreCase("F")||(gender.equalsIgnoreCase("M")))){
      this.gender=gender;
    }
    else{
      this.gender = "Unspecified";
    }
  }
  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getGender() {
    return gender;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public String getAddress() {
    return address;
  }

  public float getHeight() {
    return height;
  }

  public float getStartingweight() {
    return startingweight;
  }

  public int getBmi() {
    return bmi;
  }

  public List<Assessment> getAssessments() {
    return assessments;
  }


  public static Member findByEmail(String email)
  {
    return find("email", email).first();
  }

  public boolean checkPassword(String password)
  {
    return this.password.equals(password);
  }
}
