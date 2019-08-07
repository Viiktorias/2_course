import java.time.LocalDateTime;

public class Time {
    int seconds;
    int minutes;
    int hours;

    public Time() {
        LocalDateTime time = LocalDateTime.now();
        seconds = time.getSecond();
        minutes = time.getMinute();
        hours = time.getHour() % 12;
    }

    public void plus() {
        if (seconds == 59) {
            seconds = 0;
            if (minutes == 59) {
                minutes = 0;
                if (hours == 11) {
                    hours = 0;
                } else {
                    hours++;
                }
            } else {
                minutes++;
            }
        } else {
            seconds++;
        }
    }
}
