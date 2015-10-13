package jack.hystrix;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;

public class HelloWorldObservableCommand extends HystrixObservableCommand<String> {

	private String name;
	
	protected HelloWorldObservableCommand(String name) {
		super(HystrixCommandGroupKey.Factory.asKey("HelloWorld"));
		this.name = name;
	}

	@Override
	protected Observable<String> construct() {
		// TODO Auto-generated method stub
		return Observable.create(new Observable.OnSubscribe<String>() {

			@Override
			public void call(Subscriber<? super String> observer) {
				// TODO Auto-generated method stub
				if(!observer.isUnsubscribed()){
					observer.onNext("Hello ");
					observer.onNext(name+"!");
					observer.onCompleted();
				}
			
		}		
	});
	}
		
		public static void main(String args[]){
			Observable<String> ho = new HelloWorldObservableCommand("World").observe();
			
			ho.subscribe(new Action1<String>() {

				@Override
				public void call(String s) {
					// TODO Auto-generated method stub
					System.out.print(s);
				}
			});
			
			System.out.println();
			System.out.println("========================================");
			
			ho.subscribe((v) -> {
				System.out.print(v);
				},
				(exception) -> {
					exception.printStackTrace();
				},
				() -> {
					System.out.println();
					System.out.println("Completed");
				}
			);
		}

}
