package cz.sucharda.homework.Model;

public class Homework {

    private long id;
    private String title;
    private String date;
    private boolean finished;
    private String subject;

    public Homework(long id, String title, String date, boolean finished, String subject) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.finished = finished;
        this.subject = subject;
    }

    public Homework() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
