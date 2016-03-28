package Statistics;

import graphvisualizer.Graph;
import graphvisualizer.GraphTuple;
import java.awt.Color;
import java.util.ArrayList;

public class FamilyStatisticsTuple {
    
    private Graph field;

    public ArrayList<GraphTuple> familyMembers = new ArrayList<>();
    public int familyID;
    public double averageLifespan;
    public double lifespanDeviation;
    public double lifespanVariation;
    public double meanDeviation;
    public Color averageColor;
    public double proportionOfTotalLines;

    public FamilyStatisticsTuple(ArrayList<GraphTuple> familyIn, int familyIDIn, Graph fieldIn) {
        familyMembers = familyIn;
        familyID = familyIDIn;
        averageLifespan = calculateAverageLifespan();
        lifespanVariation = calculateLifespanVariation();
        lifespanDeviation = calculateLifespanDeviation();
        meanDeviation = calculateMeanDeviation();
        averageColor = calculateAverageColor();
        field = fieldIn;
        field.updateFamilyColorGradient(familyID-1, averageColor);
    }//end public

    private double calculateAverageLifespan() {
        double out = 0;
        for (GraphTuple gt : familyMembers) {
            out += gt.getStartHealth();
        }//end for
        out /= familyMembers.size();
        return out;
    }//end calculateAverageLifespan

    private double calculateLifespanVariation() {
        double out;
        double sumOfSquaredDifferences = 0;
        for (GraphTuple gt : familyMembers) {
            sumOfSquaredDifferences += Math.pow((gt.getStartHealth() - averageLifespan), 2);
        }//end for
        out = sumOfSquaredDifferences / familyMembers.size();
        return out;
    }//end calculateLifespanVariation

    private double calculateLifespanDeviation() {
        double out;
        out = Math.sqrt(lifespanVariation);
        return out;
    }//end calculateLifespanDeviation
    
    private double calculateMeanDeviation(){
        double out = 0;
        for(GraphTuple gt : familyMembers){
            out += Math.abs((gt.getStartHealth() - averageLifespan));
        }//end for
        return out/familyMembers.size();
    }//end calculateMeanDeviation

    private Color calculateAverageColor() {
        int redVal = 0;
        int greenVal = 0;
        int blueVal = 0;
        int numOfConnections = 0;
        for (GraphTuple gt : familyMembers) {
            if (!gt.isEdge()) {
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
    
    public String outputFormattedStatistics(){
        return "Family ID: " + familyID 
                + ", # Of Family Members: " + familyMembers.size() 
                + ", Average Lifespan: " + averageLifespan 
                + ", Family Variation: " + lifespanVariation 
                + ", Family Deviation: " + lifespanDeviation
                + ", Family Mean Deviation: " + meanDeviation;
    }//end outputStatistics
    
    public String outputPureStatistics(){
        return familyMembers.size() + "," 
                + proportionOfTotalLines + ","
                + averageLifespan + "," 
                + lifespanVariation + "," 
                + lifespanDeviation + ","
                + meanDeviation;
    }//end outputPureStatistics

    public Color getFamilyAverageColor() {
        return averageColor;
    }//end getFamilyAverageColor

    public void setFamilyAverageColor(Color in) {
        averageColor = in;
    }//end setFamilyAverageColor

}//end FamilyStatisticsTuple
