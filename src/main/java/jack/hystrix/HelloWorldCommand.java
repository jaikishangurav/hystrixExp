package jack.hystrix;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import rx.Observable;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;


public class HelloWorldCommand extends HystrixCommand<String> {

	protected HelloWorldCommand() {
		super(HystrixCommandGroupKey.Factory.asKey("HelloWorld"));
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String run() throws Exception {
		// TODO Auto-generated method stub
		//throw new IllegalArgumentException();
		return "Hello World";
	}
		
	@Override
	protected String getFallback() {
		return "Good Bye";
	}
	
	public static void main(String args[]){
		
		HystrixRequestContext context = HystrixRequestContext.initializeContext();

		HystrixCommandMetrics creditCardMetrics = HystrixCommandMetrics.getInstance(HystrixCommandKey.Factory.asKey(HelloWorldCommand.class.getSimpleName()));
		  
		System.out.println(new HelloWorldCommand().execute());
		
		ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.circuitBreaker.forceOpen", true);
		
		System.out.println(new HelloWorldCommand().execute());
		
		System.out.println("Request => " + HystrixRequestLog.getCurrentRequest().getExecutedCommandsAsString());
		/*System.out.println("======Using Future=======");
		
		Future<String> helloWorldResult = new HelloWorldCommand().queue();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println(helloWorldResult.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("======Using Observable=======");
		
		Observable<String> obs = new HelloWorldCommand().observe();
		
		obs.subscribe(v -> {
			System.out.println(v);
		}
			);*/
		
	}
	
	

}

