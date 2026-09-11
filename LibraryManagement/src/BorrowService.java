import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BorrowService {

    // 1. Hiển thị all phiếu
    public void findAllBorrowedCards() {

        String sql = "{call all_borrow_cards()}";

        try {
            CallableStatement stmt = DB.conn.prepareCall(sql);
            ResultSet rs = stmt.executeQuery();
            System.out.println("=== DANH SÁCH PHIẾU MƯỢN ===");

            while (rs.next()) {

                System.out.println("ID: " + rs.getInt("card_id"));
                System.out.println("Tên sách: " + rs.getString("book_title"));
                System.out.println("Người mượn: " + rs.getString("borrower_name"));
                System.out.println("Ngày mượn: " + rs.getString("borrow_date"));
                System.out.println("Ngày trả: " + rs.getString("return_deadline"));
                System.out.println("Số lượng: " + rs.getInt("quantity"));
                System.out.println("Trạng thái: " + rs.getString("status"));
                System.out.println("-------------------------------------------");
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Phát hiện lỗi câu 1");
        }
    }

    // 2. Add phiếu
    public void addBorrowedCards(Scanner input) {

        System.out.println("=== THÊM PHIẾU MƯỢN ===");

        System.out.print("Tên sách: ");
        String bookTitle = input.nextLine();

        while (bookTitle.isEmpty()) {
            System.out.println("Tên sách không được để trống!");
            System.out.print("Tên sách: ");
            bookTitle = input.nextLine();
        }

        System.out.print("Tên người mượn: ");
        String borrowerName = input.nextLine();

        while (borrowerName.isEmpty()) {
            System.out.println("Tên người mượn không được để trống!");
            System.out.print("Tên người mượn: ");
            borrowerName = input.nextLine();
        }

        java.sql.Timestamp borrowDate = null;

        while (borrowDate == null) {
            System.out.print("Ngày mượn (yyyy-MM-dd HH:mm:ss): ");
            String date = input.nextLine();

            try {
                borrowDate = java.sql.Timestamp.valueOf(date);
            } catch (IllegalArgumentException e) {
                System.out.println("Ngày mượn không đúng định dạng!");
            }
        }

        java.sql.Timestamp returnDeadline = null;

        while (returnDeadline == null) {
            System.out.print("Hạn trả (yyyy-MM-dd HH:mm:ss): ");
            String date = input.nextLine();

            try {
                returnDeadline = java.sql.Timestamp.valueOf(date);
            } catch (IllegalArgumentException e) {
                System.out.println("Hạn trả không đúng định dạng!");
            }
        }

        while (returnDeadline.before(borrowDate)) {
            System.out.println("Hạn trả phải sau ngày mượn!");

            System.out.print("Hạn trả (yyyy-MM-dd HH:mm:ss): ");
            String date = input.nextLine();

            try {
                returnDeadline = java.sql.Timestamp.valueOf(date);
            } catch (IllegalArgumentException e) {
                System.out.println("Hạn trả không đúng định dạng!");
            }
        }

        System.out.print("Số lượng: ");

        while (input.hasNextInt() == false) {
            System.out.println("Số lượng phải là số nguyên!");
            input.nextLine();
            System.out.print("Số lượng: ");
        }

        int quantity = input.nextInt();
        input.nextLine();

        while (quantity <= 0) {
            System.out.println("Số lượng phải lớn hơn 0!");
            System.out.print("Số lượng: ");

            while (input.hasNextInt() == false) {
                System.out.println("Số lượng phải là số nguyên!");
                input.nextLine();
                System.out.print("Số lượng: ");
            }

            quantity = input.nextInt();
            input.nextLine();
        }

        System.out.print("Trạng thái (Borrowing/Returned/Overdue): ");
        String status = input.nextLine();

        while (status.equals("Borrowing") == false
                && status.equals("Returned") == false
                && status.equals("Overdue") == false) {

            System.out.println("Trạng thái không hợp lệ.");
            System.out.print("Trạng thái (Borrowing/Returned/Overdue): ");
            status = input.nextLine();
        }

        String sql = "{CALL add_borrow_cards(?, ?, ?, ?, ?, ?)}";

        try {
            CallableStatement stmt = DB.conn.prepareCall(sql);

            stmt.setString(1, bookTitle);
            stmt.setString(2, borrowerName);
            stmt.setTimestamp(3, borrowDate);
            stmt.setTimestamp(4, returnDeadline);
            stmt.setInt(5, quantity);
            stmt.setString(6, status);

            stmt.execute();

            System.out.println(" Thêm phiếu thành công");

            stmt.close();

        } catch (SQLException e) {
            System.out.println(" Thêm phiếu thất bại");
            e.printStackTrace();
        }
    }

    //3. Update tt phiếu
    public void updateBorrowedCards(Scanner input) {

        System.out.println("========== CẬP NHẬT PHIẾU MƯỢN ==========");

        System.out.print("Nhập ID phiếu mượn cần cập nhật: ");

        while (input.hasNextInt() == false) {
            System.out.println("ID phải là số nguyên!");
            input.nextLine();
            System.out.print("Nhập ID phiếu mượn cần cập nhật: ");
        }

        int cardId = input.nextInt();
        input.nextLine();

        while (cardId <= 0) {
            System.out.println("ID phải lớn hơn 0!");
            System.out.print("Nhập ID phiếu mượn cần cập nhật: ");

            while (input.hasNextInt() == false) {
                System.out.println("ID phải là số nguyên!");
                input.nextLine();
                System.out.print("Nhập ID phiếu mượn cần cập nhật: ");
            }

            cardId = input.nextInt();
            input.nextLine();
        }

        System.out.print("Tên sách: ");
        String bookTitle = input.nextLine();

        while (bookTitle.isEmpty()) {
            System.out.println("Tên sách không được để trống!");
            System.out.print("Tên sách: ");
            bookTitle = input.nextLine();
        }

        System.out.print("Tên người mượn: ");
        String borrowerName = input.nextLine();

        while (borrowerName.isEmpty()) {
            System.out.println("Tên người mượn không được để trống!");
            System.out.print("Tên người mượn: ");
            borrowerName = input.nextLine();
        }

        java.sql.Timestamp borrowDate = null;

        while (borrowDate == null) {
            System.out.print("Ngày mượn (yyyy-MM-dd HH:mm:ss): ");
            String date = input.nextLine();

            try {
                borrowDate = java.sql.Timestamp.valueOf(date);
            } catch (IllegalArgumentException e) {
                System.out.println("Ngày mượn không đúng định dạng!");
            }
        }

        java.sql.Timestamp returnDeadline = null;

        while (returnDeadline == null) {
            System.out.print("Hạn trả (yyyy-MM-dd HH:mm:ss): ");
            String date = input.nextLine();

            try {
                returnDeadline = java.sql.Timestamp.valueOf(date);
            } catch (IllegalArgumentException e) {
                System.out.println("Hạn trả không đúng định dạng!");
            }
        }

        while (returnDeadline.before(borrowDate)) {
            System.out.println("Hạn trả phải sau ngày mượn!");

            System.out.print("Hạn trả (yyyy-MM-dd HH:mm:ss): ");
            String date = input.nextLine();

            try {
                returnDeadline = java.sql.Timestamp.valueOf(date);
            } catch (IllegalArgumentException e) {
                System.out.println("Hạn trả không đúng định dạng!");
            }
        }

        System.out.print("Số lượng: ");

        while (input.hasNextInt() == false) {
            System.out.println("Số lượng phải là số nguyên!");
            input.nextLine();
            System.out.print("Số lượng: ");
        }

        int quantity = input.nextInt();
        input.nextLine();

        while (quantity <= 0) {
            System.out.println("Số lượng phải lớn hơn 0!");
            System.out.print("Số lượng: ");

            while (input.hasNextInt() == false) {
                System.out.println("Số lượng phải là số nguyên!");
                input.nextLine();
                System.out.print("Số lượng: ");
            }

            quantity = input.nextInt();
            input.nextLine();
        }

        System.out.print("Trạng thái (Borrowing/Returned/Overdue): ");
        String status = input.nextLine();

        while (status.equals("Borrowing") == false
                && status.equals("Returned") == false
                && status.equals("Overdue") == false) {

            System.out.println("Không hợp lệ");
            System.out.print("Trạng thái (Borrowing/Returned/Overdue): ");
            status = input.nextLine();
        }

        String sql = "{CALL update_by_id(?, ?, ?, ?, ?, ?, ?)}";

        try {
            CallableStatement stmt = DB.conn.prepareCall(sql);

            stmt.setInt(1, cardId);
            stmt.setString(2, bookTitle);
            stmt.setString(3, borrowerName);
            stmt.setTimestamp(4, borrowDate);
            stmt.setTimestamp(5, returnDeadline);
            stmt.setInt(6, quantity);
            stmt.setString(7, status);

            stmt.execute();

            System.out.println("Cập nhật phiếu mượn thành công.");

            stmt.close();

        } catch (SQLException e) {
            System.out.println("Cập nhật phiếu mượn thất bại.");
            e.printStackTrace();
        }
    }

    //4. Xóa phiếu
    public void deleteBorrowedCards(Scanner input) {

        System.out.print("Nhập ID phiếu cần xóa: ");
        int cardId = input.nextInt();
        input.nextLine();

        System.out.print("Bạn có sure? (Y/N): ");
        String confirm = input.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {
            String sql = "{CALL delete_by_id(?)}";

            try {
                CallableStatement stmt = DB.conn.prepareCall(sql);
                stmt.setInt(1, cardId);
                stmt.execute();

                System.out.println("Xóa phiếu thành công.");

                stmt.close();

            } catch (SQLException e) {
                System.out.println("Xóa phiếu thất bại.");
            }

        } else {
            System.out.println("Hủy xóa.");
        }
    }

    //5. Search ttin phiếu theo độc giả
    public void searchByBorrower(String keyword) {

        String sql = "{CALL cards_by_borrower(?)}";

        try {
            CallableStatement stmt = DB.conn.prepareCall(sql);
            stmt.setString(1, keyword);
            ResultSet rs = stmt.executeQuery();

            System.out.println("===THÔNG TIN THEO ĐỘC GIẢ===");

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("card_id"));
                System.out.println("Tên sách: " + rs.getString("book_title"));
                System.out.println("Người mượn: " + rs.getString("borrower_name"));
                System.out.println("Ngày mượn: " + rs.getString("borrow_date"));
                System.out.println("Ngày trả: " + rs.getString("return_deadline"));
                System.out.println("Số lượng: " + rs.getInt("quantity"));
                System.out.println("Trạng thái: " + rs.getString("status"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Tìm thất bại");
            e.printStackTrace();
        }
    }

    //6. Search ttin phiếu theo tên sách
    public void searchByTitle(Scanner input) {

        System.out.print("Nhập tên sách cần tìm: ");
        String keyword = input.nextLine();

        String sql = "{CALL search_by_title(?)}";

        try {
            CallableStatement stmt = DB.conn.prepareCall(sql);

            stmt.setString(1, keyword);

            ResultSet rs = stmt.executeQuery();

            System.out.println("===THÔNG TIN THEO TÊN SÁCH===");

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("card_id"));
                System.out.println("Tên sách: " + rs.getString("book_title"));
                System.out.println("Người mượn: " + rs.getString("borrower_name"));
                System.out.println("Ngày mượn: " + rs.getString("borrow_date"));
                System.out.println("Ngày trả: " + rs.getString("return_deadline"));
                System.out.println("Số lượng: " + rs.getInt("quantity"));
                System.out.println("Trạng thái: " + rs.getString("status"));
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Tìm kiếm thất bại.");
        }
    }
}