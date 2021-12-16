package mariotto_marin;

public class Thread_Countdown implements Runnable {
	
	public boolean temposcaduto=false;
	public Thread_Countdown() {
		this.temposcaduto=temposcaduto;
	}
	public void setTemposcaduto(boolean isscaduto) {
		this.temposcaduto=isscaduto;
	}
	public boolean getTemposcaduto() {
        return temposcaduto;
    }
	@Override
	public void run() {
		try {
			Thread.sleep(20000);
			setTemposcaduto(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
