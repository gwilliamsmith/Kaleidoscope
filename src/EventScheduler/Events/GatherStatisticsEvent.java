package EventScheduler.Events;

import Statistics.FamilyStatisticsTuple;
import SwingElements.Base;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GatherStatisticsEvent extends Event {

    public GatherStatisticsEvent(int stepTargetIn, String eventNameIn, boolean repeatIn, Base in) {
        super(stepTargetIn, eventNameIn, repeatIn,in);
    }//end constructor

    @Override
    public void takeAction() {
        int totalLines = 0;
        ArrayList<FamilyStatisticsTuple> familyStatisticsHolder = new ArrayList<>();
        for (int i = 1; i <= ref.getGraph().getFamilyCount(); i++) {
            familyStatisticsHolder.add(new FamilyStatisticsTuple(ref.getGraph().pullFamily(i), i, ref.getGraph()));
            totalLines += familyStatisticsHolder.get(i - 1).familyMembers.size();
        }//end for
        for (FamilyStatisticsTuple fst : familyStatisticsHolder) {
            fst.proportionOfTotalLines = (double) fst.familyMembers.size() / totalLines;
            if (Base.checkConnection()) {
                try {
                    Statement stmt = ref.getConn().createStatement();
                    int recordID = (int) (ref.getGraph().getStepCount() / 50);
                    String updateString = "INSERT INTO statistics VALUES (0,"
                            + "'" + ref.getGraph().getTestName() + "',"
                            + recordID + ","
                            + fst.familyID + ","
                            + fst.familyMembers.size() + ","
                            + fst.proportionOfTotalLines + ","
                            + fst.averageLifespan + ","
                            + fst.lifespanVariation + ","
                            + fst.lifespanDeviation + ","
                            + fst.meanDeviation + ","
                            + fst.averageColor.getRed() + ","
                            + fst.averageColor.getGreen() + ","
                            + fst.averageColor.getBlue()
                            + ");";
                    updateString = updateString.replace("NaN", "0.0");
                    System.out.println(updateString);
                    stmt.executeUpdate(updateString);
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }//end try catch
            }//end if
        }//end for
    }//end takeAction

}//end GatherStatisticsEvent
