/**
 * TrainerCtrl Controller contains methods used for Trainer view of the app: Trainer index, detailed member view,
 * delete individual assessments and members and add comments to member assessments.
 */
package controllers;
import java.util.List;
import models.Member;
import models.Trainer;
import models.Assessment;
import play.Logger;
import play.mvc.Controller;

public class TrainerCtrl extends Controller {

    //Renders trainerview Dashboard and displays all gym members
    public static void indexTrainer() {
        Logger.info("Rendering Trainer Dashboard");
        Trainer trainer = Accounts.getLoggedInTrainer();
        List<Member> members = Member.findAll();
        render("trainerview.html", trainer, members);
    }

    /**
     * Method renders Member dashboard page for trainer view, displays assessments as well as
     * analytics on the top part of the page. If member has no assessments added yet,
     * analytics are pulled from registration information. Ie.StartingWeight
     */
    public static void index(Long id) {
        Logger.info("Rendering MemberView Dashboard");
        Member member = Member.findById(id);
        List<Assessment> assessments = member.assessments;
        if (assessments.size() != 0) {
            Assessment assessment = assessments.get(assessments.size() - 1);
            double bmi = GymUtility.calculateBMI(member, assessment);
            boolean isIdealBodyWeight = GymUtility.isIdealBodyWeight(member, assessment);
            String determineBMICategory = GymUtility.determineBMICategory(bmi);
            render("memberview.html", assessments, member, bmi, isIdealBodyWeight, determineBMICategory);
        } else {
            double bmi = GymUtility.calculateStartBMI(member);
            boolean isIdealBodyWeight = GymUtility.isIdealBodyWeightStart(member);
            String determineBMICategory = GymUtility.determineBMICategory(bmi);
            render("memberview.html", assessments, member, bmi,isIdealBodyWeight, determineBMICategory);
        }
    }

    //Method deletes assessment for a member
    public static void deleteAssessmentTrainer(Long id, Long assessmentid) {
        Member member = Member.findById(id);
        Assessment assessment = Assessment.findById(assessmentid);
        member.assessments.remove(assessment);
        member.save();
        assessment.delete();
        Logger.info("Deleting trainerViewAssess ");
        redirect("/trainerview");
    }

    //Trainer can delete any member
    public static void deleteMember(Long id) {
        Trainer trainer = Accounts.getLoggedInTrainer();
        Member member = Member.findById(id);
        List<Assessment> assessments = member.assessments;
        Logger.info("Removing member" + member.firstname + member.lastname);
        trainer.members.remove(member);
        trainer.save();
        member.delete();
        redirect("/trainerview");
    }

    //Trainer can add comments to any assessment
    public static void addComment(Long id, String comment) {
        Logger.info("Adding trainer comment: " + comment);
        Member member = Member.findById(id);
        Assessment assessment = Assessment.findById(id);
        assessment.setComment(comment);
        assessment.save();
        redirect("/trainerview");
    }
}