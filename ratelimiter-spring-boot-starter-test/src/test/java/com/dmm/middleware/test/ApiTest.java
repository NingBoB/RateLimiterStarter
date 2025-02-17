package com.dmm.middleware.test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Mean
 * @date 2025/2/17 16:03
 * @description ApiTest
 */
public class ApiTest {

	private static final String URL = "http://localhost:8081/api/queryUserInfo?userId=aaa";
	private static final int THREAD_COUNT = 10; // 线程数量

	public static void main(String[] args) {
		HttpClient client = HttpClient.newHttpClient();
		ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

		for (int i = 0; i < THREAD_COUNT; i++) {
			final int requestNumber = i + 1;
			executorService.submit(() -> {
				HttpRequest request = HttpRequest.newBuilder()
						.uri(URI.create(URL))
						.build();

				try {
					long start = System.currentTimeMillis();
					// 时间格式化
					// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					// System.out.println("Request #" + requestNumber + " Start Time: " + sdf.format(start));
					System.out.println("Request #" + requestNumber + " Start Time: " + start);
					HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
					// System.out.println("Request #" + requestNumber + " Status Code: " + response.statusCode());
					System.out.println("Request #" + requestNumber + "Response Body: " + response.body());
					long end = System.currentTimeMillis();
					System.out.println("Request #" + requestNumber + " End Time: " + end);
					System.out.println("Request #" + requestNumber + " Time: " + (end - start) + "ms");
				} catch (Exception e) {
					System.err.println("Error occurred during request #" + requestNumber);
					e.printStackTrace();
				}
			});
		}

		// 关闭线程池
		executorService.shutdown();
		try {
			// 等待所有任务完成或超时
			if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
				executorService.shutdownNow();
			}
		} catch (InterruptedException e) {
			executorService.shutdownNow();
		}
	}
}
