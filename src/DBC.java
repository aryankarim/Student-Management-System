import java.sql.*;
import java.util.ArrayList;

class DBC {
    private Connection con;
    private ArrayList<String[]> allStudent = new ArrayList<>(1); //Arraylist that contains String Arrays

    private String connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/aryandb?serverTimezone=UTC", "root", "");
        } catch (SQLException | ClassNotFoundException e) {
            return "fail";
        }
        return "success";
    }

    String insert(int id,String fname,String lname, String email,String gender,float average){
        if(connect().compareTo("fail")==0){
            return "You have a connection Error! Please check your DB Driver or Server!!";
        }
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate("insert into students values (" + id + ",'" + fname + "','" + lname + "','" + email + "','" + gender + "'," + average + ");");
            return "Student Enrolled";
        } catch (SQLIntegrityConstraintViolationException e) {
            return "That ID already exist in our DB! please change it!";
        } catch (SQLException e) {
            return "The DB couldn't carry out your request! Consider Updating your software!";
        } finally {
            try {if (con != null) con.close();} catch (Exception ignored) {}
        }
    }
    ArrayList<String[]> search(int id){
        allStudent.clear();
        if(connect().compareTo("fail")==0){
            allStudent.add(new String[]{"Server"});
            return allStudent;
        }
        //ive explained below why ive done this
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("select * from students where id = " + id + ";")) {
            if (rs.next()) {
                String grade;
                if (rs.getFloat(6) < 60) {
                    grade = "Failed";
                } else {
                    grade = "Passed";
                }
                String[] array = new String[]{String.valueOf(rs.getInt(1)), rs.getString(2)
                        , rs.getString(3), rs.getString(4), rs.getString(5), grade};
                allStudent.add(array);
                return allStudent;
            } else {
                allStudent.add(new String[]{"Usernotfound"});
                return allStudent;
            }
        } catch (SQLException e) {
            allStudent.add(new String[]{"query"});
            return allStudent;
        } finally { try { if (con != null) con.close();} catch (Exception ignored) {}
        }
    }
    ArrayList<String[]> searchAll(){
        allStudent.clear();
        if(connect().compareTo("fail")==0){
            allStudent.add(new String[]{"server"});
            return allStudent;
        }
        // I used TRY WITH RESOURCES to close the closable classes automatically, to avoid resource leakage.
        // Even tho this is unnecessary in this APP, but in real life e.g.(in backend to frontend connections)
        // this will be very necessary.
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("select * from students;")) {
            if (rs.next()) {
                do {
                    String grade;
                    if (rs.getFloat(6) < 60) {
                        grade = "Failed";
                    } else {
                        grade = "Passed";
                    }
                    String[] array = new String[]{String.valueOf(rs.getInt(1)), rs.getString(2)
                            , rs.getString(3), rs.getString(4), rs.getString(5), grade};
                    allStudent.add(array);
                } while (rs.next());
                return allStudent;
            } else {
                allStudent.add(new String[]{"empty"});
                return allStudent;
            }
        } catch (SQLException e) {
            allStudent.add(new String[]{"query"});
            return allStudent;
        } finally {
            try { if (con != null) con.close();} catch (Exception ignored) {}
        }
    }
    String updateEmail(int id,String newEmail){
        if(connect().compareTo("fail")==0){
            return "Server is down!!!";
        }
        try (Statement stmt = con.createStatement()) {
            int rs = stmt.executeUpdate("update students set email='" + newEmail + "' where id =" + id + ";");
            if (rs == 0) {
                return "No user exist with that ID!!";
            }
        } catch (SQLException e) {
            return "Error updating! use latest  mySQL and JDK version!";
        } finally {
            try {if (con != null) con.close();} catch (Exception ignored) {}
        }
        return "Email updated!!";
    }
    String delete(int id){
        Statement stmt = null;
        try{
            if(connect().compareTo("fail")==0){
                return "Server is down!!!";
            }
            stmt = con.createStatement();
            int rs = stmt.executeUpdate("delete from students where id ="+id+";");
            if(rs==0){
                return "No user exist with that ID!!";
            }
        } catch (SQLException e) {
            return "Error updating! use latest mySQL and JDK version!";
        }finally {
            try { if (stmt != null) stmt.close(); } catch (Exception ignored) {}
            try { if (con != null) con.close(); } catch (Exception ignored) {}
        }
        return "Student deleted!!";
    }
}
