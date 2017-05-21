package julianleng.eyeris;
import java.util.ArrayList;
/**
 * Created by julianleng on 3/21/17.
 */

public class ScrollablePosts {


    private String post_date;
    private String post_content;
    private String post_title;
    private int post_votes;
    private ArrayList<Comment> post_comments;
    public ScrollablePosts(){

    }

    public ScrollablePosts(String post_date, String post_content, String post_title){
        this.post_content=post_content;
        this.post_date=post_date;
        this.post_title=post_title;
        this.post_votes=0;
    }
    public ScrollablePosts(String post_date, String post_content, String post_title, ArrayList<Comment> post_comments){
        this.post_content=post_content;
        this.post_date=post_date;
        this.post_title=post_title;
        this.post_comments=post_comments;
    }
    public ArrayList<Comment> getPost_comments() {
        return post_comments;
    }

    public void setPost_comments(ArrayList<Comment> post_comments) {
        this.post_comments = post_comments;
    }
    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }
    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public int getPost_votes() {
        return post_votes;
    }

    public void setPost_votes(int post_votes) {
        this.post_votes = post_votes;
    }
}
