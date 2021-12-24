package mariotto_marin;

public class Thread_Countdown implements Runnable {
	public int tempo = 20;
    public boolean temposcaduto=false;
    public Thread_Countdown() {
        this.temposcaduto=temposcaduto;
        this.tempo=tempo;
    }
    public void setTemposcaduto(boolean isscaduto) {
        this.temposcaduto=isscaduto;
    }
    public boolean getTemposcaduto() {
        return temposcaduto;
    }
    public int getTempo() {
		return tempo;
	}
    @Override
    public void run() {
        try {
        	for(int x=0; x<20; x++) {
        		Thread.sleep(1000);
        		tempo=20-x;
        	}
            setTemposcaduto(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}