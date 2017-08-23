package ui;

public class Status {
    private boolean active;
    private boolean updated;
    private boolean complete;
    private long done;
    private long total;
    private int found;
    private RemainingTime remainingTime;
    private Text text;

    public Status(Text text) {
        this.text = text;
        this.reset();
    }

    public synchronized void reset() {
        this.active = false;
        this.updated = false;
        this.complete = false;
        this.done = 0;
        this.total = 0;
        this.found = 0;
        this.remainingTime = new RemainingTime(this.total);
    }

    public synchronized void setup(long done, long total, int found) {
        this.active = true;
        this.updated = true;
        this.complete = false;
        this.done = done;
        this.total = total;
        this.found = found;
        this.remainingTime = new RemainingTime(this.total);
    }

    public synchronized void update(long done, int found) {
        this.updated = true;
        this.done = done;
        this.found = found;
    }

    public synchronized void complete() {
        this.complete = true;
    }

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized boolean isUpdated() {
        return updated;
    }

    public synchronized boolean isComplete() {
        return complete;
    }

    public synchronized long getDone() {
        updated = false;
        return done;
    }

    public synchronized long getTotal() {
        updated = false;
        return total;
    }

    public synchronized int getFound() {
        updated = false;
        return found;
    }

    public synchronized String getRemainingTime() {
        updated = false;
        return remainingTime.getRemainingTime(done, text);
    }
}
