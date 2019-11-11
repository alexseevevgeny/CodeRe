package retry.analyzer;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RetryFailedTestCases implements IRetryAnalyzer {

    private static final int RETRIES = 2;

    private static Map<String, Integer> retriesMap = Collections.synchronizedMap(new HashMap<>());

    public boolean retry(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS) {
            return false;
        }
        String key = result.getTestContext().getName() + "/" + result.getMethod().getMethodName();
        retriesMap.putIfAbsent(key, 0);
        int curRetries = retriesMap.get(key);
        if (curRetries < RETRIES) {
            retriesMap.put(key, curRetries + 1);
            return true;
        }
        return false;

    }

}
