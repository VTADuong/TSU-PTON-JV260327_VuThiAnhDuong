package com.example.phonestore.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseFixer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DatabaseFixer.class);
    private final JdbcTemplate jdbc;

    public DatabaseFixer(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void run(String... args) {
        try {
            // Sửa cột 'total' thừa (nếu có từ trước) sang NULL DEFAULT NULL để MySQL không chặn lệnh INSERT
            jdbc.execute("ALTER TABLE invoice MODIFY COLUMN total DECIMAL(12,2) NULL DEFAULT NULL");
            log.info("DatabaseFixer: Đã sửa cột 'total' trong bảng invoice thành NULL DEFAULT NULL");
        } catch (Exception e) {
            log.debug("DatabaseFixer: Không cần sửa cột 'total': {}", e.getMessage());
        }
    }
}

