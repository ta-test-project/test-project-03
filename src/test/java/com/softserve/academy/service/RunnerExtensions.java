package com.softserve.academy.service;

import com.softserve.academy.base.test.TestRunner;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class RunnerExtensions implements AfterTestExecutionCallback {
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        boolean failed = context.getExecutionException().isPresent();
        context.getTestInstance().ifPresent(instance ->
        {
            if (instance instanceof TestRunner runner) {
                runner.isTestSuccessful = !failed;
            }
        });
    }
}
