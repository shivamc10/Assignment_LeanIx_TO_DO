package com.to_do_bck;
import com.to_do_bck.core.Test_Task;
import com.to_do_bck.resources.Test_Resource;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunnerClient {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(Test_Resource.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(Test_Task.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}
