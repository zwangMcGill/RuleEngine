//import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
//
//import java.io.File;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLClassLoader;
//import java.util.Arrays;
//import java.util.List;
//import org.junit.platform.launcher.Launcher;
//import org.junit.platform.launcher.LauncherDiscoveryRequest;
//import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
//import org.junit.platform.launcher.core.LauncherFactory;
//import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
//import org.junit.platform.launcher.listeners.TestExecutionSummary;
//import org.junit.runner.JUnitCore;
//import org.junit.runner.Result;
//import org.junit.runner.notification.Failure;
//
//public class RunTest {
//  public long compile(Class<?> Class) {
//    // Junit 4
//    //    Request request = Request.method(MVELTest.class, "testStudent");
//    //    Result result = new JUnitCore().run(request);
//    //      Result result = JUnitCore.runClasses(MVELTest.class);
//    //    System.out.println(result.wasSuccessful());
//
//    // Junit 5
//    final LauncherDiscoveryRequest request =
//        LauncherDiscoveryRequestBuilder.request().selectors(selectClass(Class)).build();
//
//    final Launcher launcher = LauncherFactory.create();
//    final SummaryGeneratingListener listener = new SummaryGeneratingListener();
//
//    launcher.registerTestExecutionListeners(listener);
//    launcher.execute(request);
//
//    TestExecutionSummary summary = listener.getSummary();
//    long testFoundCount = summary.getTestsFoundCount();
//    List<TestExecutionSummary.Failure> failures = summary.getFailures();
//    return summary.getTestsSucceededCount();
//  }
//
//  public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//    // Provide the file path to the .class file
//    String classFilePath = "C:\\Users\\12220\\OneDrive\\桌面\\Teacher.class";
//
//    // Load the class dynamically
//    File classFile = new File(classFilePath);
//    URL classFileUrl = classFile.toURI().toURL();
//    URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{classFileUrl});
//    System.out.println(Arrays.toString(classLoader.getURLs()));
//    Class<?> testClass = classLoader.loadClass("Teacher");
//    System.out.println(Arrays.toString(testClass.getFields()));
//
//
//  }
//}
