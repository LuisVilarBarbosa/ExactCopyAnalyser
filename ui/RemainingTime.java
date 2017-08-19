package ui;

public class RemainingTime {
    private long begin;
    private long total;

    public RemainingTime(long totalToDo) {
        this.begin = System.currentTimeMillis();
        this.total = totalToDo;
    }

    public String getRemainingTime(long alreadyDone, Text text) {
        if (alreadyDone < 1)
            return text.getCalculatingMsg();
        if (alreadyDone > total)
            return text.getInvalidArgMsg();

        long end = System.currentTimeMillis();
        long multiplier = total - alreadyDone;
        long diff = end - begin;
        double diffByUnit = (double) diff / alreadyDone;
        long remaining = Math.round(diffByUnit * multiplier);
        long remainingSeconds = remaining / 1000;
        long seconds = remainingSeconds % 60;
        long minutes = (remainingSeconds / 60) % 60;
        long hours = remainingSeconds / 3600;
        StringBuilder sb = new StringBuilder();
        sb.append(hours).append("h").append(String.format("%02d", minutes)).append("m").append(String.format("%02d", seconds)).append("s");
        return sb.toString();
    }
}
