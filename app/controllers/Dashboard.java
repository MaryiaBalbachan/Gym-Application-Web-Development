/**
 * Dashboard Controller contains methods pertaining to member view: Member Index, add and delete Assessment and view
 * member profile.
 */
package controllers;
import models.Member;
import models.Assessment;
import play.Logger;
import play.mvc.Controller;


import java.util.List;

public class Dashboard extends Controller {

  /**Method renders Member dashboard page, displays assessments as well as
   * analytics on the top part of the page. If member has no assessments added yet,
   * analytics are pulled from registration information. Ie.StartingWeight
   *
   */
  public static void indexMember() {
    Logger.info("Rendering Member Dashboard");
    Member member = Accounts.getLoggedInMember();
    List<Assessment> assessments = member.assessments;
    if(assessments.size()!=0){
      Assessment assessment = assessments.get(assessments.size()-1);
      double bmi = GymUtility.calculateBMI(member, assessment);
      boolean isIdealBodyWeight = GymUtility.isIdealBodyWeight(member,assessment);
      String determineBMICategory = GymUtility.determineBMICategory(bmi);
      render("dashboard.html", assessments, member, bmi, isIdealBodyWeight, determineBMICategory);}
    else {
      double bmi = GymUtility.calculateStartBMI(member);
      boolean isIdealBodyWeight = GymUtility.isIdealBodyWeightStart(member);
      String determineBMICategory = GymUtility.determineBMICategory(bmi);
      render("dashboard.html", assessments, member, bmi, isIdealBodyWeight, determineBMICategory);}
    }

  /**Method allows to add Assessments by taking in member inputs into the fields.
   * @param weight
   * @param chest
   * @param thigh
   * @param upperarm
   * @param waist
   * @param hips
   */
  public static void addAssessment(float weight, float chest, float thigh,
                                   float upperarm, float waist, float hips) {
    Member member = Accounts.getLoggedInMember();
    Assessment assessment = new Assessment(weight, chest, thigh, upperarm, waist, hips);
    member.assessments.add(assessment);
    member.save();
    Logger.info("Adding Assessment" + weight);
    redirect("/dashboard");
  }

  /**Method allows to delete assessments by assessment Id associated with member Id.
   * @param id
   * @param assessmentid
   */
  public static void deleteAssessment(Long id, Long assessmentid) {
    Member member = Member.findById(id);
    Assessment assessment = Assessment.findById(assessmentid);
    member.assessments.remove(assessment);
    member.save();
    assessment.delete();
    Logger.info("Deleting ");
    redirect("/dashboard");
  }

  //Renders member profile page
  public static void profile() {
    Logger.info("Rendering Profile");
    Member member = Accounts.getLoggedInMember();
    List<Assessment> assessments = member.assessments;
    render("profile.html", member);

  }
}
