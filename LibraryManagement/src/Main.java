import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        BorrowService service = new BorrowService();
        int choice;

        Scanner input = new Scanner(System.in);
        do {
            System.out.println("================= LIBRARY MANAGEMENT =================");
            System.out.println("1. Danh sách tất cả phiếu mượn");
            System.out.println("2. Thêm mới phiếu mượn");
            System.out.println("3. Cập nhật thông tin phiếu mượn");
            System.out.println("4. Xóa phiếu mượn");
            System.out.println("5. Tìm kiếm phiếu mượn theo tên độc giả");
            System.out.println("6. Tìm kiếm phiếu mượn theo tên sách");
            System.out.println("7. Thoát");

            System.out.println("Vui lòng nhập lựa chọn của bạn:");

            choice = input.nextInt();
            input.nextLine();

            switch (choice){
                case 1:
                    System.out.println("1. Danh sách tất cả phiếu mượn");
                    service.findAllBorrowedCards();
                    break;
                case 2:
                    System.out.println("2. Thêm mới phiếu mượn");
                    service.addBorrowedCards(input);
                    break;
                case 3:
                    System.out.println("3. Cập nhật thông tin phiếu mượn");
                    service.updateBorrowedCards(input);
                    break;
                case 4:
                    System.out.println("4. Xóa phiếu mượn");
                    service.deleteBorrowedCards(input);
                    break;
                case 5:
                    System.out.println("5. Tìm kiếm phiếu mượn theo tên độc giả");
                    System.out.print("Nhập tên độc giả cần tìm: ");
                    String keyword = input.nextLine();
                    service.searchByBorrower(keyword);
                    break;
                case 6:
                    System.out.println("6. Tìm kiếm phiếu mượn theo tên sách");
                    service.searchByTitle(input);
                    break;
                case 7:
                    System.out.println("7. Thoát");
                    System.out.println("Đã thoát chương trình.");
                    break;
                default:
                    System.out.println("Chưa hỗ trợ");
            }
        } while(choice != 7);
        input.close();
    }
}