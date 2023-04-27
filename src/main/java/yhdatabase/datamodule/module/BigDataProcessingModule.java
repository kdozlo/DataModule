package yhdatabase.datamodule.module;

import lombok.Setter;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

@Setter
public class BigDataProcessingModule {
    private DataProcessingService dataProcessingService;
    private BlockingQueue<String> filterQueue;
    private BlockingQueue<String> outputQueue;
    private Map<String, String> environmentInfo;

    public void start() {
        // 환경정보 변경 감지 Thread 실행
        Thread configThread = new Thread(new ConfigChangeDetectionThread());
        configThread.start();

        // PostgreSQL 데이터 읽어들이는 Thread 실행
        Thread readThread = new Thread(new ReadDataThread());
        readThread.start();

        // Output 처리 Thread 실행
        Thread outputThread = new Thread(new OutputDataThread());
        outputThread.start();
    }

    // PostgreSQL 데이터 읽어들이는 Thread
    private class ReadDataThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                dataProcessingService.processData();
            }
        }
    }

    // Output 처리 Thread
    private class OutputDataThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    String rowData = outputQueue.take();
                    // TODO: Output 처리 (Insert, Update, Delete, CSV 변환)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 환경정보 변경 감지 Thread
    private class ConfigChangeDetectionThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                // 쿼리변경 확인
                String query = environmentInfo.get("query");
                if (query != null) {
                    // TODO: 쿼리 변경 처리
                }

                // 필터조건 변경 확인
                String filter = environmentInfo.get("filter");
                if (filter != null) {
                    // TODO: 필터 조건 변경 처리
                }

                // Output 정보 변경 확인
                String output = environmentInfo.get("output");
                if (output != null) {
                    // TODO: Output 정보 변경 처리
                }

                // 환경정보 메모리 적제
                // TODO: 환경정보 메모리 적제
            }
        }
    }
}
