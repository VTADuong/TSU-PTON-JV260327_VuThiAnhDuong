CREATE DATABASE db_library_management;
USE db_library_management;

CREATE TABLE borrow_cards (
    card_id INT AUTO_INCREMENT PRIMARY KEY,
    book_title VARCHAR(150) NOT NULL,
    borrower_name VARCHAR(100) NOT NULL,
    borrow_date DATETIME NOT NULL,
    return_deadline DATETIME NOT NULL,
    quantity INT NOT NULL,
    status VARCHAR(30) NOT NULL
);

INSERT INTO borrow_cards
(book_title, borrower_name, borrow_date, return_deadline, quantity, status)
VALUES
('Sách Ngữ Văn', 'Nguyễn Văn A', '2026-09-01 08:00:00', '2026-09-15 17:00:00', 1, 'Borrowing'),
('Sách Toán Học', 'Trần Thị B', '2026-09-02 09:00:00', '2026-09-16 17:00:00', 1, 'Borrowing'),
('Khoa Học Tự Nhiên', 'Lê Văn C', '2026-09-03 10:00:00', '2026-09-17 17:00:00', 2, 'Returned'),
('Tin học', 'Phạm Thị D', '2026-09-04 14:00:00', '2026-09-18 17:00:00', 1, 'Overdue'),
('Thể Dục', 'Hoàng Văn E', '2026-09-05 15:00:00', '2026-09-19 17:00:00', 1, 'Borrowing');

DELIMITER //

-- Thực hiện lấy danh sách tất cả các phiếu mượn 
CREATE PROCEDURE all_borrow_cards()
BEGIN 
	SELECT*FROM borrow_cards;
END //

-- Thực hiện thêm mới một phiếu mượn
CREATE PROCEDURE add_borrow_cards(
	IN p_book_title VARCHAR(150),
    IN p_borrower_name VARCHAR(100),
    IN p_borrow_date TIMESTAMP,
    IN p_return_deadline TIMESTAMP,
    IN p_quantity INT,
    IN p_status VARCHAR(30)
)
BEGIN 
	INSERT INTO borrow_cards(book_title, borrower_name, borrow_date, return_deadline, quantity, status)
    VALUE (p_book_title, p_borrower_name, p_borrow_date, p_return_deadline, p_quantity, p_status);
END //

-- Thực hiện lấy danh sách phiếu mượn theo tên độc giả theo borrower_name
CREATE PROCEDURE cards_by_borrower(
	IN p_borrower_name VARCHAR(100)
)
BEGIN
	SELECT*FROM borrow_cards
     WHERE LOWER(borrower_name) LIKE CONCAT('%', LOWER(p_borrower_name), '%');
END //

-- Thực hiện cập nhật thông tin phiếu mượn theo card_id
CREATE PROCEDURE update_by_id(
	IN p_card_id INT,
	IN p_book_title VARCHAR(150),
    IN p_borrower_name VARCHAR(100),
    IN p_borrow_date DATETIME,
    IN p_return_deadline DATETIME,
    IN p_quantity INT,
    IN p_status VARCHAR(30)
)
BEGIN
	UPDATE borrow_cards
    SET book_title = p_book_title,
        borrower_name = p_borrower_name,
        borrow_date = p_borrow_date,
        return_deadline = p_return_deadline,
        quantity = p_quantity,
		status = p_status
    WHERE card_id = p_card_id;
END //

-- Thực hiện xóa phiếu mượn theo card_id
CREATE PROCEDURE delete_by_id(
	IN p_card_id INT
)
BEGIN
	DELETE FROM borrow_cards WHERE card_id = p_card_id;
END //

-- Thực hiện tìm kiếm phiếu mượn theo tên sách book_tittle không phân biệt hoa thường
CREATE PROCEDURE search_by_title(
	IN p_book_title VARCHAR(150)
)
BEGIN
	SELECT*FROM borrow_cards
    WHERE LOWER(book_title) LIKE CONCAT('%', LOWER(p_book_title), '%');
END //

DELIMITER ;
