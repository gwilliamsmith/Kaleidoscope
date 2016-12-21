package Statistics;

import graphvisualizer.Graph;
import graphvisualizer.GraphTuple;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Class used to calculate and store statistics on line family groups.
 */
public class FamilyStatisticsTuple {

    private Graph field;                                                        //The Graph where all families can be found. Used for reference

    public ArrayList<GraphTuple> familyMembers = new ArrayList<>();             //All members of the family to collect statistics for
    public int familyID;                                                        //The ID number of the family
    public double averageLifespan;                                              //The average lifespan of the family in steps
    public double lifespanDeviation;                                            //The deviation in steps of the lifespan for the family from the mean
    public double lifespanVariation;                                            //The variation in steps of the lifespan for the family from the mean
    public double meanDeviation;                                                //The mean deviation of the family
    public Color averageColor;                                                  //The average color of the family
    public double proportionOfTotalLines;                                       //The proportion of total lines on the graph represented by the family

    /**
     * Constructor.
     *
     * @param familyIn An {@link ArrayList} of all lines in the family to record
     * statistics for
     * @param familyIDIn The ID number of the family
     * @param fieldIn The {@link Graph} where the family is located, used for
     */
    public FamilyStatisticsTuple(ArrayList<GraphTuple> familyIn, int familyIDIn, Graph fieldIn) {
        field = fieldIn;
        familyMembers = familyIn;
        familyID = familyIDIn;
        averageLifespan = calculateAverageLifespan();
        lifespanVariation = calculateLifespanVariation();
        lifespanDeviation = calculateLifespanDeviation();
        meanDeviation = calculateMeanDeviation();
        averageColor = calculateAverageColor();
        field.updateFamilyColorGradient(familyID - 1, averageColor);
    }//end public

    /**
     * Calculates the mean lifespan in steps of the family.
     *
     * @return The mean lifespan in steps of the family
     */
    private double calculateAverageLifespan() {
        double out = 0;
        for (GraphTuple gt : familyMembers) {
            out += gt.getStartHealth();
        }//end for
        out /= familyMembers.size();
        return out;
    }//end calculateAverageLifespan

    /**
     * Calculates the variation of lifespan in steps for the family.
     *
     * @return The variation of lifespan in steps for the family
     */
    private double calculateLifespanVariation() {
        double out;
        double sumOfSquaredDifferences = 0;
        for (GraphTuple gt : familyMembers) {
            sumOfSquaredDifferences += Math.pow((gt.getStartHealth() - averageLifespan), 2);
        }//end for
        out = sumOfSquaredDifferences / familyMembers.size();
        return out;
    }//end calculateLifespanVariation

    /**
     * Calculates the deviation from the mean average lifespan in steps for the
     * family. Uses {@link FamilyStatisticsTuple#calculateLifespanVariation()}.
     *
     * @return The deviation from the mean average lifespan in steps for the
     * family
     */
    private double calculateLifespanDeviation() {
        double out;
        out = Math.sqrt(lifespanVariation);
        return out;
    }//end calculateLifespanDeviation

    /**
     * Calculates the mean deviation of the family.
     *
     * @return The mean deviation of the family
     */
    private double calculateMeanDeviation() {
        double out = 0;
        for (GraphTuple gt : familyMembers) {
            out += Math.abs((gt.getStartHealth() - averageLifespan));
        }//end for
        return out / familyMembers.size();
    }//end calculateMeanDeviation

    /**
     * Calculates the average color of the lines in the family.
     *
     * @return The average color of lines in the family
     */
    private Color calculateAverageColor() {
        int redVal = 0;
        int greenVal = 0;
        int blueVal = 0;
        int numOfConnections = 0;
        for (GraphTuple gt : familyMembers) {
            if (!gt.isEdge(field)) {
                redVal += gt.getRed();
                greenVal += gt.getGreen();
                blueVal += gt.getBlue();
                numOfConnections++;
            }//end if
        }//end for
        if (numOfConnections > 0) {
            redVal = redVal / numOfConnections;
            greenVal = greenVal / numOfConnections;
            blueVal = blueVal / numOfConnections;
            return new Color(redVal, greenVal, blueVal);
        }//end if
        return new Color(Color.OPAQUE);
    }//end calculateAverageColor

    /**
     * Creates a string containing all numerical statistics with labels.
     *
     * @return A string containing all numerical statistics with labels
     */
    public String outputFormattedStatistics() {
        return "Family ID: " + familyID
                + ", # Of Family Members: " + familyMembers.size()
                + ", Average Lifespan: " + averageLifespan
                + ", Family Variation: " + lifespanVariation
                + ", Family Deviation: " + lifespanDeviation
                + ", Family Mean Deviation: " + meanDeviation;
    }//end outputStatistics

    /**
     * Creates a string containing only comma separated numerical statistics.
     *
     * @return A string, containing only comma separated numerical statistics
     */
    public String outputPureStatistics() {
        return familyMembers.size() + ","
                + proportionOfTotalLines + ","
                + averageLifespan + ","
                + lifespanVariation + ","
                + lifespanDeviation + ","
                + meanDeviation;
    }//end outputPureStatistics

    /**
     * Returns the average color of lines in the family.
     *
     * @return The average color of lines in the family
     */
    public Color getFamilyAverageColor() {
        return averageColor;
    }//end getFamilyAverageColor

}//end FamilyStatisticsTuple
