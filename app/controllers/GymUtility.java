/**
 * GymUtility Controller contains only static methods that can be used across a range of applications.
 */

package controllers;
import models.Member;
import models.Assessment;
import models.Trainer;
import play.Logger;
import play.mvc.Controller;

public class GymUtility {
    /**
     * Method calculates member BMI if they have added assessments and returns double bmi rounded to
     * 2 decimal places.BMI is weight divided by the square of the height.
     * @param member
     * @param assessment
     * @return bmi to two decimal places.
     */
    public static double calculateBMI(Member member, Assessment assessment) {
        float height = member.getHeight();
        float weight = assessment.getWeight();
        double bmi = weight / height / height*10000;
        return toTwoDecimalPlaces(bmi);
    }

    /**
     * Method takes in start weight if there are no assessments added by the member.
     * BMI is weight divided by the square of the height.
     * @param member
     * @return bmi to two decimal places
     */
    public static double calculateStartBMI(Member member) {
        float height = member.getHeight();
        float weight = member.getStartingweight();
        double bmi = weight / height / height*10000;
        return toTwoDecimalPlaces(bmi);
    }

    /**
     * Helper Method to return num to two decimal places.
     * @param num
     * @return num
     */
    public static double toTwoDecimalPlaces(double num) {
        return (int) (num * 100) / 100.0;
    }

    /**
     *Returns the category the BMI belongs to, based on the following values:
     *     BMI less than 16 (exclusive) is "SEVERELY UNDERWEIGHT"
     *     BMI between 16 (inclusive) and 18.5 (exclusive) is "UNDERWEIGHT"
     *     BMI between 18.5 (inclusive) and 25(exclusive) is "NORMAL"
     *     BMI between 25 (inclusive) and 30 (exclusive) is "OVERWEIGHT"
     *     BMI between 30 (inclusive) and 35 (exclusive) is "MODERATELY OBESE"
     *     BMI greater than 35 (inclusive) and is "SEVERELY OBESE"
     * @param bmiValue
     * @return String
     */
    public static String determineBMICategory(double bmiValue) {
        if (bmiValue < 16) {
            return "SEVERELY UNDERWEIGHT";
        } else if (bmiValue >= 16 && bmiValue < 18.5) {
            return "UNDERWEIGHT";
        } else if (bmiValue >= 18.5 && bmiValue < 25) {
            return "NORMAL";
        } else if (bmiValue >= 25 && bmiValue < 30) {
            return "OVERWEIGHT";
        } else if (bmiValue >= 30 && bmiValue < 35) {
            return "MODERATELY OBESE";
        } else if (bmiValue >= 35) {
            return "SEVERELY OBESE";
        } else {
            return "INVALID BMI VALUE";
        }
    }

    /**True DEVINE formula
     * Boolean Method determines whether a member is ideal weight based on their gender, height and weight by applying
     * Devine formula. Takes in latest assessment weight value.
     *     For males, an ideal body weight is: 50 kg + 2.3 kg for each inch over 5 feet.
     *     For females, an ideal body weight is: 45.5 kg + 2.3 kg for each inch over 5 feet.
     *     Note: if no gender is specified, return the result of the female calculation.
     *     Note: if the member is 5 feet or less, return 50kg for male and 45.5kg for female.
     * @param member
     * @param assessment
     * @return boolean
     */
   /*public static boolean isIdealBodyWeight(Member member, Assessment assessment) {
        String gender = member.getGender();
        float weight = assessment.getWeight();
        double heightInInches = member.getHeight() * 0.3937;
        double idealWeightM = 50 + ((heightInInches - 60) * 2.3);
        double idealWeightF = 45.5 + ((heightInInches - 60) * 2.3);

        if ((heightInInches <= 60) && (gender.equalsIgnoreCase("m") && (weight > 49.8) && (weight < 50.2))) {
            return true;
        } else if ((heightInInches <= 60) && (gender.equalsIgnoreCase("f") && (weight > 45.3) && (weight < 45.7))) {
            return true;
        } else if ((gender.equalsIgnoreCase("m")) && (weight > idealWeightM - 0.2) && (weight < idealWeightM + 0.2)) {
            return true;
        } else if ((gender.equalsIgnoreCase("f")) && (weight > idealWeightF - 0.2) && (weight < idealWeightF + 0.2)) {
            return true;
        } else if ((gender.equalsIgnoreCase("Unspecified")) && (weight > idealWeightF - 0.2) && (weight < idealWeightF + 0.2)) {
            return true;
        } else {
            return false;
        }
    }*/
    /**
     * Boolean Method determines whether a member is ideal weight based on their gender, height and weight by applying
     * Devine formula. Takes in latest assessment weight value.
     * I have increased the margin from 0.2kg to 1kg for demonstration purposes
     * @param member
     * @param assessment
     * @return boolean
     */
    public static boolean isIdealBodyWeight(Member member, Assessment assessment) {
        String gender = member.getGender();
        float weight = assessment.getWeight();
        double heightInInches = member.getHeight() * 0.3937;
        double idealWeightM = 50 + ((heightInInches - 60) * 2.3);
        double idealWeightF = 45.5 + ((heightInInches - 60) * 2.3);

        if ((heightInInches <= 60) && (gender.equalsIgnoreCase("m") && (weight >= 49) && (weight <=51))) {
            return true;
        } else if ((heightInInches <= 60) && (gender.equalsIgnoreCase("f") && (weight > 44) && (weight < 46))) {
            return true;
        } else if ((gender.equalsIgnoreCase("m")) && (weight > idealWeightM - 1.0) && (weight < idealWeightM + 1.0)) {
            return true;
        } else if ((gender.equalsIgnoreCase("f")) && (weight > idealWeightF - 1.0) && (weight < idealWeightF + 1.0)) {
            return true;
        } else if ((gender.equalsIgnoreCase("Unspecified")) && (weight > idealWeightF - 1.0) && (weight < idealWeightF + 1.0)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Boolean Method determines whether a member is ideal weight based on their gender, height and weight by applying
     * Devine formula. When there are no assessments added it takes in Starting weight value at registration.
     * I have increased the margin from 0.2kg to 1kg for demonstration purposes
     * @param member
     * @return boolean
     */
    public static boolean isIdealBodyWeightStart(Member member) {
        String gender = member.getGender();
        float weight = member.getStartingweight();
        double heightInInches = member.getHeight() * 0.3937;
        double idealWeightM = 50 + ((heightInInches - 60) * 2.3);
        double idealWeightF = 45.5 + ((heightInInches - 60) * 2.3);

        if ((heightInInches <= 60) && (gender.equalsIgnoreCase("m") && (weight >= 49) && (weight <=51))) {
            return true;
        } else if ((heightInInches <= 60) && (gender.equalsIgnoreCase("f") && (weight > 44) && (weight < 46))) {
            return true;
        } else if ((gender.equalsIgnoreCase("m")) && (weight > idealWeightM - 1.0) && (weight < idealWeightM + 1.0)) {
            return true;
        } else if ((gender.equalsIgnoreCase("f")) && (weight > idealWeightF - 1.0) && (weight < idealWeightF + 1.0)) {
            return true;
        } else if ((gender.equalsIgnoreCase("Unspecified")) && (weight > idealWeightF - 1.0) && (weight < idealWeightF + 1.0)) {
            return true;
        } else {
            return false;
        }
    }

}

