package com.selfhealing.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int maxRetryCount = 2; // retry twice max

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            System.out.println("[RETRY] Retrying test: " + result.getName() + " | Attempt " + retryCount);
            return true;
        }
        return false;
    }
}
