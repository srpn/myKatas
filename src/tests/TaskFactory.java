package tests;

public class TaskFactory {

	public Runnable createSuccessfulTask() {
		return new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}

	public Runnable createFailingTask(int i) {
		return new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(i);
					throw new RuntimeException("Hello From Failing Task");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}

	public Runnable createTimeoutTask() {
		return new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("I am being Interrupted");
				}
			}
		};
	}

}
