package datango;

import datango.config.AppConfigProvider;
import datango.testrail.TCStep;
import datango.testrail.TestCaseID;
import datango.testrail.TestCaseIds;
import datango.testrail.TestRailRequests;
import datango.ui.driver.DriverManager;
import datango.utils.AllureReportUtil;
import datango.utils.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Sergey Kuzhel
 */
@Slf4j
public class TestListener extends TestListenerAdapter {
    private File testLogFile;

    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        DriverManager.takeScreenshot(((BaseTest) testClass).driver);
        AllureReportUtil.attachFile(testLogFile);
        LoggerUtil.detachAppender(testLogFile);
        super.onTestFailure(result);
        if (Boolean.parseBoolean(AppConfigProvider.get().testRailReportIsEnabled())) {
            printTestResult(result);
        }
    }

    public void onTestSkipped(ITestResult result) {
        LoggerUtil.detachAppender(testLogFile);
        super.onTestSkipped(result);
        if (Boolean.parseBoolean(AppConfigProvider.get().testRailReportIsEnabled())) {
            printTestResult(result);
        }
    }

    public void onTestSuccess(ITestResult result) {
        LoggerUtil.detachAppender(testLogFile);
        super.onTestSkipped(result);
        if (Boolean.parseBoolean(AppConfigProvider.get().testRailReportIsEnabled())) {
            printTestResult(result);
        }
    }

    public void onTestStart(ITestResult result) {
        log.info("EXECUTING TEST: {}", result.getMethod().getMethodName());
        testLogFile = LoggerUtil.attachFileAppender(result.getName());
        super.onTestStart(result);
    }

    private String getTestIdFromAnnotation(ITestResult result) {
        String testData = null;
        Method testMethod = result.getMethod().getMethod();
        if (testMethod.isAnnotationPresent(TestCaseID.class)) {
            testData = testMethod.getAnnotation(TestCaseID.class).id();
        }

        return testData;
    }

    private int getStepIdFromAnnotation(ITestResult result) {
        int step = -1;
        Method testMethod = result.getMethod().getMethod();
        if (testMethod.isAnnotationPresent(TCStep.class)) {
            step = testMethod.getAnnotation(TCStep.class).step();
        }
        return step;
    }

    private List<String> getTestIdsFromAnnotation(ITestResult result) {
        Method testMethod = result.getMethod().getMethod();
        if (testMethod.isAnnotationPresent(TestCaseIds.class)) {
            TestCaseIds annotation = testMethod.getAnnotation(TestCaseIds.class);
            return Arrays.asList(annotation.value());
        }
        return null;
    }

    private void printTestResult(ITestResult testResult) {
        TestRailRequests req;
        if (getTestIdFromAnnotation(testResult) == null && getTestIdsFromAnnotation(testResult) != null) {
            for (String id: getTestIdsFromAnnotation(testResult)) {
                req = new TestRailRequests(id, getStepIdFromAnnotation(testResult));
                log.info("=======================================================================");
                log.info(id);
                log.info("=======================================================================");
                switch (testResult.getStatus()) {
                    case ITestResult.SUCCESS:
                        req.passRequest();
                        break;
                    case ITestResult.FAILURE:
                        String curError;
                        Throwable throwable = testResult.getThrowable();
                        curError = throwable.getMessage();
                        req.failRequest(curError);
                        break;
                    case ITestResult.SKIP:
                        req.retestRequest();
                        break;
                }
            }
        }

        req = new TestRailRequests(getTestIdFromAnnotation(testResult), getStepIdFromAnnotation(testResult));
        log.info("=======================================================================");
        log.info(getTestIdFromAnnotation(testResult));
        log.info("=======================================================================");
        switch (testResult.getStatus()) {
            case ITestResult.SUCCESS:
                req.passRequest();
                break;
            case ITestResult.FAILURE:
                String curError;
                Throwable throwable = testResult.getThrowable();
                curError = throwable.getMessage();
                req.failRequest(curError);
                break;
            case ITestResult.SKIP:
                req.retestRequest();
                break;
        }
    }
}
