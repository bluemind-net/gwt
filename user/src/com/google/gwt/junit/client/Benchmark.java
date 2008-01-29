/*
 * Copyright 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.junit.client;

/**
 * A type of {@link com.google.gwt.junit.client.GWTTestCase} which specifically
 * records performance results. {@code Benchmarks} have additional functionality
 * above and beyond GWT's JUnit support for standard <code>TestCases</code>.
 *
 * <h2>Reporting</h2>
 * <p>
 * In a single <code>JUnit</code> run, the results of all executed
 * benchmarks are collected and stored in an XML report viewable with the
 * <code>benchmarkViewer</code>.
 * </p><br>
 *
 * <h2>Permutations</h2>
 * <p>GWT supports test methods that have parameters. GWT will execute each
 * benchmark method multiple times in order to exhaustively test all the 
 * possible combinations of parameter values. All of your test method parameters 
 * must be annotated with a {@code Range} annotation such as 
 * {@link com.google.gwt.junit.client.annotations.RangeField RangeField}
 * or {@link com.google.gwt.junit.client.annotations.RangeEnum RangeEnum}.
 *
 * For example,
 *
 * <code><pre> 
 * public void testArrayListRemoves(
 *   &#64;RangeEnum(Position.class) Position where, 
 *   &#64;RangeField("insertRemoveRange") Integer size) { ... 
 * }
 * </pre></code>
 * </p>
 * 
 * <h2>Timing</h2>
 * <ul>
 * <li>GWT automatically removes jitter from your benchmark methods by running
 * them for a minimum period of time (150ms).</li>
 *
 * <li>GWT supports {@link com.google.gwt.junit.client.annotations.IterationTimeLimit
 * time limits} on the maximum duration of each permutation of a benchmark 
 * method. With this feature, you can supply very high upper bounds on your 
 * ranges (such as Integer.MAX_VALUE), which future-proofs your benchmarks
 * against faster hardware.
 * </li>
 *
 * <li>GWT supports {@link com.google.gwt.junit.client.annotations.Setup Setup} 
 * and {@link com.google.gwt.junit.client.annotations.Teardown Teardown} methods
 * which separate test overhead from the actual work being benchmarked. The 
 * timings of these lifecycle methods are excluded from test results.
 * </li>
 * </ul>
 *
 * <h2>Notes</h2>
 * <p>Please note that {@code Benchmarks} do not currently support 
 * asynchronous testing mode. Calling
 * {@link com.google.gwt.junit.client.GWTTestCase#delayTestFinish(int)}
 * or {@link com.google.gwt.junit.client.GWTTestCase#finishTest()} will result
 * in an UnsupportedOperationException.</p>
 *
 * <h2>Examples of benchmarking in action</h2>
 *
 * <h3>A simple benchmark example</h3>
 * {@link com.google.gwt.examples.benchmarks.AllocBenchmark} is a simple example
 * of a basic benchmark that doesn't take advantage of most of benchmarking's
 * advanced features.
 *
 * {@example com.google.gwt.examples.benchmarks.AllocBenchmark}
 *
 * <h3>An advanced benchmark example</h3>
 * {@link com.google.gwt.examples.benchmarks.ArrayListBenchmark} is a 
 * more sophisticated example of benchmarking. It demonstrates the use of 
 * {@code Setup} and {@code Teardown} test methods, parameterized test methods,
 *  and time limits.
 *
 * {@example com.google.gwt.examples.benchmarks.ArrayListBenchmark}
 */
public abstract class Benchmark extends GWTTestCase {

  /**
   * The name of the system property that specifies the location
   * where benchmark reports are both written to and read from.
   * Its value is <code>com.google.gwt.junit.reportPath</code>.
   *
   * If this system property is not set, the path defaults to the user's
   * current working directory.
   */
  public static final String REPORT_PATH = "com.google.gwt.junit.reportPath";
}
