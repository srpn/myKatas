package tests;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

import com.train.Worker3;

public class WorkerTest {

	private static final long DEFAULT_TIMEOUT = Duration.ofSeconds(10).toMillis();
	
	private TaskFactory factory;
	private Worker3 worker;
	
	@Before
	public void setUp() { 
		factory = new TaskFactory();
		worker = new Worker3();
	}
	
	@Test
    public void testFailedNew() throws InterruptedException {

        List<Runnable> tasks = new ArrayList<>();
        Set<Runnable> expectedFailed = new HashSet<>();

        expectedFailed.add(factory.createFailingTask(60000));
        expectedFailed.add(factory.createFailingTask(60000));
        expectedFailed.add(factory.createFailingTask(60000));
        expectedFailed.add(factory.createFailingTask(60000));
        expectedFailed.add(factory.createFailingTask(60000));
        expectedFailed.add(factory.createFailingTask(60000));
        expectedFailed.add(factory.createFailingTask(60000));
        expectedFailed.add(factory.createFailingTask(60000));

        tasks.addAll(expectedFailed);

        Worker3.ExecutedTasks result = worker.execute(tasks, DEFAULT_TIMEOUT);

        Assert.assertTrue(result.successful.isEmpty());
        Assert.assertEquals(expectedFailed.size(), result.failed.size());
        Assert.assertEquals(expectedFailed, result.failed);
        Assert.assertTrue(result.timedOut.isEmpty());
    }
	
	@Test
	public void testSuccessful() throws InterruptedException {
		
		List<Runnable> tasks = new ArrayList<>();
		List<Runnable> expectedSuccess = new ArrayList<>();
		
		expectedSuccess.add(factory.createSuccessfulTask());
		expectedSuccess.add(factory.createSuccessfulTask());
		
		tasks.add(expectedSuccess.get(1));
		tasks.add(expectedSuccess.get(0));
		
		Worker3.ExecutedTasks result = worker.execute(tasks, DEFAULT_TIMEOUT);
		
//		Assert.assertEquals(expectedSuccess, result.successful);
		Assert.assertThat(result.successful, containsInAnyOrder(expectedSuccess.toArray()));

		Assert.assertTrue(result.failed.isEmpty());
		Assert.assertTrue(result.timedOut.isEmpty());
	}
	
	@Test
	public void testFailed() throws InterruptedException {
		
		List<Runnable> tasks = new ArrayList<>();
		Set<Runnable> expectedFailed = new HashSet<>();
		
		expectedFailed.add(factory.createFailingTask(500));
		expectedFailed.add(factory.createFailingTask(200));
		
		tasks.addAll(expectedFailed);
		
		Worker3.ExecutedTasks result = worker.execute(tasks, DEFAULT_TIMEOUT);
		
		Assert.assertTrue(result.successful.isEmpty());
		Assert.assertEquals(expectedFailed, result.failed);
		Assert.assertTrue(result.timedOut.isEmpty());
	}
	
	//@Test
	public void testTimedOut() throws InterruptedException {
		
		List<Runnable> tasks = new ArrayList<>();
		Set<Runnable> expectedTimedOut = new HashSet<>();
		
		expectedTimedOut.add(factory.createTimeoutTask());
		expectedTimedOut.add(factory.createTimeoutTask());
		
		tasks.addAll(expectedTimedOut);
		
		Worker3.ExecutedTasks result = worker.execute(tasks, DEFAULT_TIMEOUT);
		
		Assert.assertTrue(result.successful.isEmpty());
		Assert.assertTrue(result.failed.isEmpty());
		Assert.assertEquals(expectedTimedOut, result.timedOut);
	}
	
	@Test
	public void testBasic() throws InterruptedException {
		
		List<Runnable> tasks = new ArrayList<>();
		
		List<Runnable> expectedSuccess = new ArrayList<>();		
		expectedSuccess.add(factory.createSuccessfulTask());
		expectedSuccess.add(factory.createSuccessfulTask());
		
		tasks.add(expectedSuccess.get(1));
		tasks.add(expectedSuccess.get(0));
		
		Set<Runnable> expectedFailed = new HashSet<>();		
		expectedFailed.add(factory.createFailingTask(200));
		expectedFailed.add(factory.createFailingTask(100));		
		tasks.addAll(expectedFailed);
		
		Set<Runnable> expectedTimedOut = new HashSet<>();		
		expectedTimedOut.add(factory.createTimeoutTask());
		expectedTimedOut.add(factory.createTimeoutTask());		
		tasks.addAll(expectedTimedOut);
		
		Worker3.ExecutedTasks result = worker.execute(tasks, DEFAULT_TIMEOUT);
		
		expectedSuccess.forEach(action -> Assert.assertTrue(result.successful.contains(action)));
//		Assert.assertEquals(expectedSuccess, containsInAnyOrder(result.successful));
		expectedFailed.forEach(action -> Assert.assertTrue(result.failed.contains(action)));
//		Assert.assertEquals(expectedFailed, result.failed);
		Assert.assertEquals(expectedTimedOut, result.timedOut);
	}
	
	@Test
	public void testNoExcessiveWait() throws InterruptedException {
		
		List<Runnable> tasks = new ArrayList<>();
		tasks.add(factory.createSuccessfulTask());
		
		int timeLimit = 700;
		long startTime = System.currentTimeMillis(); 
		Worker3.ExecutedTasks result = worker.execute(tasks, DEFAULT_TIMEOUT);
		long elapsed = System.currentTimeMillis() - startTime;
		
		Assert.assertEquals(tasks, result.successful);
		Assert.assertTrue(result.failed.isEmpty());
		Assert.assertTrue(result.timedOut.isEmpty());
		String message = String.format("Execution time significantly exceeded time necessary for "
									 + "execution of all tasks. "
									 + "Execution took %dms, while %dms is more than enough.", 
									 elapsed, timeLimit);
		
		Assert.assertTrue(message, elapsed <= timeLimit);
	}	
}