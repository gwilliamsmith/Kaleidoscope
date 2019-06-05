package EventScheduler;

import EventScheduler.Events.Event;
import java.util.ArrayList;
import java.util.Iterator;

public class EventScheduler {

    private ArrayList<Event> events = new ArrayList<>();

    public void checkSchedule(long stepCount) {
        for (Iterator<Event> iterator = events.iterator(); iterator.hasNext();) {
            Event e = iterator.next();
            if (e.getRepeat()) {
                if (stepCount % e.getStepTarget() == 0) {
                    e.takeAction();
                }//end if
            }//end if
            else {
                if (e.getStepTarget() == stepCount) {
                    e.takeAction();
                    iterator.remove();
                }//end if
            }//end else
        }//end for
    }//end checkSchedule

    public void addEvent(Event e) {
        events.add(e);
    }//end addEvent

    public void removeEvent(Event e) {
        events.remove(e);
    }//end removeEvent

    public void clearSchedule() {
        events.clear();
    }//end clearSchedule

    public ArrayList<Event> getRepeatedEvents() {
        ArrayList<Event> out = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            Event e = events.get(i);
            if (e.getRepeat()) {
                out.add(e);
            }//end if
        }//end for
        return out;
    }//end getRepeatedEvents

    public ArrayList<Event> getSingleEvents() {
        ArrayList<Event> out = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            Event e = events.get(i);
            if (!e.getRepeat()) {
                out.add(e);
            }//end if
        }//end for
        return out;
    }//end getSingleEvents

    public ArrayList<String> getRepeatedEventsDisplayList() {
        ArrayList<String> out = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            Event e = events.get(i);
            if (e.getRepeat()) {
                String item = e.getEventName() + " - " +
                        e.getStepTarget();
                out.add(item);
            }//end if
        }//end for
        return out;
    }//end get RepeatedEventsDisplayList

    public ArrayList<String> getSingleEventsDisplayList() {
        ArrayList<String> out = new ArrayList<>();
        for(int i=0;i<events.size();i++){
            Event e = events.get(i);
            if (!e.getRepeat()) {
                String item = e.getEventName() + " - " +
                        e.getStepTarget();
                out.add(item);
            }//end if
        }//end for
        return out;
    }//end getSingleEventsDisplayList

}//end EventScheduler class
