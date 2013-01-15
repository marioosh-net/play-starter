package models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity(name = "notes")
public class Note extends Model {

	@Id
	private Long id;
	private Date date;
	
	@Required
	private String title;
	
	@Column(length=1000)
	private String content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static Finder<Long, Note> find = new Finder<Long, Note>(Long.class, Note.class);

	@Override
	public String toString() {
		return "Note [id=" + id + ", date=" + date + ", title=" + title + ", content=" + content + "]";
	}

    public String validate() {
        if(title == null) {
            return "Title reqired";
        }
        return null;
    }
	
}
