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
        for(int i=0;i<familyStatisticsHolder.size();i++){
            FamilyStatisticsTuple fst = familyStatisticsHolder.get(i);
            fst.proportionOfTotalLines = (double) fst.familyMembers.size() / totalLines;
            if (!Base.checkConnection()) {
                try {
                    Statement stmt = ref.getConn().createStatement();
                    int recordID = (int) (ref.getGraph().getStepCount() / 50);
                    StringBuilder updateString = new StringBuilder("INSERT INTO statistics VALUES (0,");
                    updateString.append("'");
                    updateString.append(ref.getGraph().getTestName());
                    updateString.append("',");
                    updateString.append(recordID);
                    updateString.append(",");
                    updateString.append(fst.familyID);
                    updateString.append(",");
                    updateString.append(fst.familyMembers.size());
                    updateString.append(",");
                    updateString.append(fst.proportionOfTotalLines);
                    updateString.append(",");
                    updateString.append(fst.averageLifespan);
                    updateString.append(",");
                    updateString.append(fst.lifespanVariation);
                    updateString.append(",");
                    updateString.append(fst.lifespanDeviation);
                    updateString.append(",");
                    updateString.append(fst.meanDeviation);
                    updateString.append(",");
                    updateString.append(fst.averageColor.getRed());
                    updateString.append(",");
                    updateString.append(fst.averageColor.getGreen());
                    updateString.append(",");
                    updateString.append(fst.averageColor.getBlue());
                    updateString.append(");");
                    String updateOut = updateString.toString();
                    updateOut = updateOut.replace("NaN", "0.0");
                    System.out.println(updateOut);
                    stmt.executeUpdate(updateOut);
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }//end try catch
            }//end if
        }//end for
    }//end takeAction

}//end GatherStatisticsEvent
