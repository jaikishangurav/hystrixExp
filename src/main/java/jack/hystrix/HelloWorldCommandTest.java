package jack.hystrix;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

public class HelloWorldCommandTest {
	
	@Test
	public void should_execute_fallback_method_when_circuit_is_open() {

	   

	  //Initialize HystrixRequestContext to be able to get some metrics

	  HystrixRequestContext context = HystrixRequestContext.initializeContext();

	  HystrixCommandMetrics creditCardMetrics = HystrixCommandMetrics.getInstance(HystrixCommandKey.Factory.asKey(HelloWorldCommand.class.getSimpleName()));

	   

	  //We use Archaius to set the circuit as closed.

	  ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.circuitBreaker.forceOpen", false);

	   

	  String successMessage = new HelloWorldCommand().execute();
	  
	  assertEquals(successMessage, "Hello World");

	   

	  //We use Archaius to open the circuit

	  ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.circuitBreaker.forceOpen", true);

	   

	  String failMessage = new HelloWorldCommand().execute();

	  assertEquals(failMessage, "Good Bye");

	   

	  //Prints Request => HelloWorldRestCommand[SUCCESS][19ms], HelloWorldRestCommand[SHORT_CIRCUITED, FALLBACK_SUCCESS][0ms] 

	  System.out.println("Request => " + HystrixRequestLog.getCurrentRequest().getExecutedCommandsAsString());

	   

	  assertEquals(creditCardMetrics.getHealthCounts().getTotalRequests(), 2);

	  assertEquals(creditCardMetrics.getHealthCounts().getErrorCount(), 1);

	 

	}
	
	
}
