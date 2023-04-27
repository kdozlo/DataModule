package yhdatabase.datamodule.module;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PostgreSQLDataProcessingModule {
    private final JdbcTemplate jdbcTemplate;
    private final BlockingQueue<String[]> filterQueue;
    private final BlockingQueue<String[]> outputQueue;

    public PostgreSQLDataProcessingModule() {
        // 데이터베이스 연결 정보 설정
        String url = "jdbc:postgresql://localhost:5432/mydatabase";
        String username = "myuser";
        String password = "mypassword";
        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username, password);

        // JdbcTemplate 초기화
        jdbcTemplate = new JdbcTemplate(dataSource);

        // Queue 초기화
        filterQueue = new LinkedBlockingQueue<>();
        outputQueue = new LinkedBlockingQueue<>();
    }

    public void processData() {
        // PostgreSQL 데이터 읽어들이는 Thread
        Thread readThread = new Thread(() -> {
            // 데이터 조회 쿼리
            String query = "SELECT column1, column2, column3 FROM mytable";

            while (true) {
                // 데이터 조회
                List<String[]> result = jdbcTemplate.query(
                        query,
                        (rs, rowNum) -> new String[]{
                                rs.getString("column1"),
                                rs.getString("column2"),
                                rs.getString("column3")
                        }
                );

                // 조회된 데이터를 FilterQueue에 등록
                for (String[] data : result) {
                    try {
                        filterQueue.put(data);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        readThread.start();

        // Filter 처리 Thread
        Thread filterThread = new Thread(() -> {
            while (true) {
                String[] data;
                try {
                    // FilterQueue에서 데이터 가져오기
                    data = filterQueue.take();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                // 데이터 필터링 처리
                if (data[0].equals("filter condition")) {
                    // 필터링 조건에 맞지 않는 데이터는 출력 Queue에 추가하지 않음
                    continue;
                } else {
                    // 필터링 조건에 맞는 데이터는 출력 Queue에 추가
                    try {
                        outputQueue.put(data);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        filterThread.start();

        // Output 처리 Thread
        Thread outputThread = new Thread(() -> {
            while (true) {
                String[] data;
                try {
                    // Output Queue에서 데이터 가져오기
                    data = outputQueue.take();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                // Insert, Update, Delete 처리
                jdbcTemplate.update(
                        "INSERT INTO mytable (column1, column2, column3) VALUES (?, ?, ?)",
                        data[0], data[1], data[2]
                );
            }
        });
        outputThread.start();

        // 환경정보 변경 감지 Thread
        Thread environmentThread = new Thread(() -> {
            while (true) {
                // 쿼리변경 확인
                // 필터조건 변경 확인
                // Output 정보 변경 확인
                // 환경정보 메모리 적제
            }
        });
        environmentThread.start();
    }
}


