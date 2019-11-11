package datango;

import datango.testrail.TCStep;
import datango.testrail.TestCaseID;
import datango.testrail.TestCaseIds;
import datango.testrail.TestCaseManager;
import lombok.extern.slf4j.Slf4j;
import org.testng.IAnnotationTransformer;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class TestSkipper implements IAnnotationTransformer, IInvokedMethodListener {

    private String getCaseId(Method method) {
        if (method != null) {
            if (method.isAnnotationPresent(TestCaseID.class)) {
                return method.getAnnotation(TestCaseID.class).id();
            }
        }
        return null;
    }

    private List<String> getCaseIds(Method method) {
        if (method != null) {
            if (method.isAnnotationPresent(TestCaseIds.class)) {
                TestCaseIds annotation = method.getAnnotation(TestCaseIds.class);
                return Arrays.asList(annotation.value());
            }
        }
        return null;
    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        if (TestCaseManager.caseToRun != null && getCaseId(testMethod) != null) {
            annotation.setEnabled(TestCaseManager.caseToRun.stream()
                .anyMatch(str -> str.equals(getCaseId(testMethod))));
        } else if (TestCaseManager.caseToRun != null && getCaseIds(testMethod) != null){
                annotation.setEnabled(TestCaseManager.caseToRun.stream()
                    .anyMatch(str -> getCaseIds(testMethod).contains(str)));
             }
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

    }
}
