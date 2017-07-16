package EventScheduler.Events;

import Statistics.FamilyStatisticsTuple;
import SwingElements.Base;
import java.util.ArrayList;

public class OutputStatisticsEvent extends Event {

    public OutputStatisticsEvent(int stepTargetIn, String eventNameIn, boolean repeatIn, Base in) {
        super(stepTargetIn, eventNameIn, repeatIn, in);
    }//end constructor

    @Override
    public void takeAction() {
        int totalLines = 0;
        ArrayList<FamilyStatisticsTuple> familyStatisticsHolder = new ArrayList<>();
        for (int i = 1; i <= ref.getGraph().getFamilyCount(); i++) {
            familyStatisticsHolder.add(new FamilyStatisticsTuple(ref.getGraph().pullFamily(i), i, ref.getGraph()));
            totalLines += familyStatisticsHolder.get(i - 1).familyMembers.size();
        }//end for
        for (int i=0;i<familyStatisticsHolder.size();i++) {
            FamilyStatisticsTuple fst = familyStatisticsHolder.get(i);
            fst.proportionOfTotalLines = (double) fst.familyMembers.size() / totalLines;
            int recordID = (int) (ref.getGraph().getStepCount() / 50);
            StringBuilder updateString = new StringBuilder("FamilyID: ");
            updateString.append(fst.familyID);
            updateString.append(" # of members: ");
            updateString.append(fst.familyMembers.size());
            updateString.append(" Family population proportion: ");
            updateString.append(fst.proportionOfTotalLines);
            updateString.append(" Average lifespan: ");
            updateString.append(fst.averageLifespan);
            updateString.append(" Lifespan variation: ");
            updateString.append(fst.lifespanVariation);
            updateString.append(" Lifespan deviation: ");
            updateString.append(fst.lifespanDeviation);
            updateString.append(" Mean deviation: ");
            updateString.append(fst.meanDeviation);
            String updateOut = updateString.toString();
            updateOut = updateOut.replace("NaN", "0.0");
            System.out.println(updateOut);
        }//end for
    }//end takeAction
}//end OutputStatisticsEventClass
