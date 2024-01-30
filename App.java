import java.io.*;
import java.sql.*;

public class App {
public static void main (String args[]) throws SQLException, IOException {
    try {
        Class.forName("oracle.jdbc.driver.OracleDriver");
    } catch (ClassNotFoundException x) {
        System.out.println("Driver could not be loaded.");
    }
    String dbacct, passwrd, name;
    String grade;
    int credit;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // BufferedReader for input
    //dbacct = readEntry("Enter database account: ");
    System.out.println("Enter database account: ");
    dbacct = br.readLine();
    //passwrd = readEntry("Enter password: ", br);
    System.out.println("Enter password: ");
    passwrd = br.readLine();
    //Connection conn = DriverManager.getConnection("jdbc:oracle:thin:"+dbacct+"/"+passwrd);
    
    Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", dbacct, passwrd);
    String stmt1 = "select G.Grade, C.Credit_hours from STUDENT S, GRADE_REPORT G, SECTION SEC, COURSE C where G.Student_number=S.Student_number AND G.Section_identifier=SEC.Section_identifier AND SEC.Course_number=C.Course_number AND S.Name=?";
    PreparedStatement p = conn.prepareStatement(stmt1);
    //name = readEntry("Please enter your name: ");
    System.out.println("Please enter your name: ");
    name = br.readLine();
    p.clearParameters();
    p.setString(1, name);
    ResultSet r = p.executeQuery();
    double count=0, sum=0, avg=0;
    
    while(r.next()) {
        grade = r.getString(1);
        credit = r.getInt(2);
        switch (grade) {
            case "A": sum=sum+(4*credit); count=count+1; break;
            case "B": sum=sum+(3*credit); count=count+1; break;
            case "C": sum=sum+(2*credit); count=count+1; break;
            case "D": sum=sum+(1*credit); count=count+1; break;
            case "F": sum=sum+(0*credit); count=count+1; break;
            default: System.out.println("This grade "+grade+" will not be calculated."); break;
        }
    };
    avg = sum/count;
    System.out.println("Student named "+name+" has a grade point average "+avg+".");
    r.close();
}
}