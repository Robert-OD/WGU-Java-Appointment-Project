package Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

public class Logger {

    public static void appointmentAddLog(String customerName, ZonedDateTime startTime) throws IOException {
        File logger = new File("Log.txt");
        FileWriter fw = new FileWriter(logger, true);
        PrintWriter pw = new PrintWriter(fw);
        pw.println("User: " + MainData.getUser().getUserName() + "      has added a new appointment with customer: " + customerName + "     On: " + startTime + "       at: " + ZonedDateTime.now());
        pw.close();
    }
    public static void customerAddLog(String customerName) throws IOException {
        File logger = new File("Log.txt");
        FileWriter fw = new FileWriter(logger, true);
        PrintWriter pw = new PrintWriter(fw);
        pw.println("User: " + MainData.getUser().getUserName() + "      has added a new customer: " + customerName + "      at: " + ZonedDateTime.now());
        pw.close();
    }
    public static void loginLogger(String userName) throws IOException {
        File logger = new File("Log.txt");
        FileWriter fw = new FileWriter(logger, true);
        PrintWriter pw = new PrintWriter(fw);
        pw.println("User: " + MainData.getUser().getUserName() + "      has signed in at: " + TimeDateFormat.convertToDateTimeFormat(ZonedDateTime.now()));
        pw.close();
    }
}
