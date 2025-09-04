package com.selfhealing.utils;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryListener implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        // ✅ In TestNG 7.10+, getRetryAnalyzer is removed. Use setRetryAnalyzer directly.
        Class<?> retry = annotation.getRetryAnalyzerClass();

        if (retry == null) {
            annotation.setRetryAnalyzer(RetryAnalyzer.class);
        }
    }
}
