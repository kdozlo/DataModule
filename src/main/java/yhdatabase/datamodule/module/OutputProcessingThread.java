package yhdatabase.datamodule.module;

public class OutputProcessingThread implements Runnable{
    @Override
    public void run() {
        while (true) {
            // FilterQueue에서 데이터 빼내기
            String data = filterQueue.poll();
            if (data != null) {
                // 데이터 처리
                String outputData = processData(data);

                // OutputQueue에 데이터 추가
                outputQueue.add(outputData);
            }

            // OutputQueue에서 데이터 빼내기
            String outputData = outputQueue.poll();
            if (outputData != null) {
                // Insert, Update, Delete, CSV 변환 처리
                processOutputData(outputData);
            }

            // 일정 시간 간격으로 데이터 처리하기
            Thread.sleep(OUTPUT_INTERVAL);
        }
    }

    private String processData(String data) {
        // TODO: 데이터 처리 로직 구현
        return data;
    }

    private void processOutputData(String outputData) {
        // TODO: Output 데이터 처리 로직 구현
    }
}
