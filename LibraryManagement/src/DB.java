import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    static Connection conn = null;
    static {
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_library_management", "root", "");
            System.out.println("Đã kết nối.");
        } catch (SQLException e){
            System.out.println("Chưa kết nối");
        }
    }
}