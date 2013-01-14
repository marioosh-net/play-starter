package controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import model.MenuItem;
import models.Note;
import play.Logger;
import play.api.templates.Html;
import play.db.DB;
import play.mvc.Controller;
import play.mvc.Result;
import scala.actors.threadpool.Arrays;
import views.html.home;
import views.html.menu;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.google.gdata.util.ServiceException;

public class Application extends Controller {
	
	static final String ADMIN_PASSWORD = play.Play.application().configuration().getString("admin.password");

	public static Html menu() {
		List<Note> l = Note.find.all();
		Logger.info(l+"");
		
		return menu.render(Note.find.all());
	}
	
	public static Result home(String message) {
		if(request().queryString().get("lang") != null) {
			response().setCookie("lang", request().queryString().get("lang")[0]);
		}				
		return ok(home.render(message, menu()));
	}
	
	public static Result logout() throws MalformedURLException {
		session().clear();
		return ok(home.render("logged out", menu()));
	}

	public static Result login() throws IOException, ServiceException {
		final Map<String, String[]> values = request().body().asFormUrlEncoded();
	    final String hash = values.get("pass")[0];
		if(hash.equals(ADMIN_PASSWORD)) {
			session("user", "admin");
			return redirect("/");
		}
		return ok(home.render("login error", menu()));
	}	
	
	public static Result add() {
		final Map<String, String[]> values = request().body().asFormUrlEncoded();
	    final String title = values.get("title")[0];
	    final String content = values.get("content")[0];
	    Note n = new Note();
	    n.setTitle(title);
	    n.setContent(content);
	    n.setDate(new Date(System.currentTimeMillis()));
	    Ebean.save(n);
	    
		return redirect("/");
	}
	
	public static Result deleteAll() {
		Ebean.createSqlUpdate("delete from Note").execute();
		return redirect("/");
	}
	
}
