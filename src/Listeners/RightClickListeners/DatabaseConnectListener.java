package Listeners.RightClickListeners;

import SwingElements.Base;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * {@link ActionListener} child, used to connect to a local database when the
 * corresponding menu button is pressed in the {@link Base} class.
 */
public class DatabaseConnectListener implements ActionListener {

    private Base ref;

    /**
     * Constructor.
     *
     * @param in {@link Base} object, used for reference
     */
    public DatabaseConnectListener(Base in) {
        ref = in;
    }//end constructor

    @Override
    /**
     * Method performed on trigger. Checks to ensure that the driver is
     * accessible, then attempts to connect to the local mySQL server. If
     * successful, tells the program which database to use.
     */
    public void actionPerformed(ActionEvent e) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            ref.setConn(DriverManager.getConnection("jdbc:mysql://localhost", "root", "password"));
            Statement stmt = ref.getConn().createStatement();
            stmt.executeQuery("Use sys");
            System.out.println("Success");
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println(ex.toString());
        } //end try catch block

    }//end actionPerformed

}//end DatabaseConnectListener
