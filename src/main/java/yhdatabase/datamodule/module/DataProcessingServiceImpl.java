package yhdatabase.datamodule.module;

import lombok.Setter;

import java.util.concurrent.BlockingQueue;

@Setter
public class DataProcessingServiceImpl implements DataProcessingService {
    private DataSource dataSource;
    private BlockingQueue<String> filterQueue;
    private BlockingQueue<String> outputQueue;

    @Override
    public void processData() {
        // PostgreSQL 데이터베이스 연결
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // PostgreSQL 데이터베이스에서 데이터 읽어들이기
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            String query = "SELECT * FROM mytable";
            resultSet = statement.executeQuery(query);

            // 읽어들인 데이터 필터링 처리 후 Queue에 등록
            while (resultSet.next()) {
                String rowData = resultSet.getString("column1") + "," + resultSet.getString("column2");
                if (rowData.contains("filtering keyword")) {
                    filterQueue.add(rowData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
