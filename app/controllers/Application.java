package controllers;

import interceptors.Headers;
import interceptors.Logged;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import other.Role;
import models.Note;
import play.Logger;
import play.api.templates.Html;
import play.data.Form;
import play.data.validation.ValidationError;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import views.html.home;
import views.html.notes;
import views.html.note;
import com.avaje.ebean.Ebean;
import com.google.gdata.util.ServiceException;

public class Application extends Controller {
	
	static final String ADMIN_PASSWORD = play.Play.application().configuration().getString("admin.password");
	static final String USER_PASSWORD = play.Play.application().configuration().getString("user.password");

	public static Html menu() {
		List<Note> l = Note.find.all();
		Logger.info(l+"");
		
		return notes.render(Note.find.all());
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
			session("role", Role.ADMIN.name());
			return redirect("/");
		} else if(hash.equals(USER_PASSWORD)) {
			session("user", "user");
			session("role", Role.USER.name());
			return redirect("/");
		}
		return ok(home.render("login error", menu()));
	}	

	@Logged(Role.ADMIN)
	public static Result add() {
		Form<Note> noteForm = form(Note.class);

		if(noteForm.bindFromRequest().hasErrors()) {
			return badRequest("Title required!");
		}
		Note note = noteForm.bindFromRequest().get();
	    note.setDate(new Date(System.currentTimeMillis()));
		Logger.info(note+"");	    
	    Ebean.save(note);
		
		/**
		 * manually
		 *
		final Map<String, String[]> values = request().body().asFormUrlEncoded();
	    final String title = values.get("title")[0];
	    final String content = values.get("content")[0];
	    Note n = new Note();
	    n.setTitle(title);
	    n.setContent(content);
	    n.setDate(new Date(System.currentTimeMillis()));
	    Ebean.save(n);
	    */
	    
		return redirect("/");
	}
	
	@Logged(Role.ADMIN)
	public static Result deleteAll() {
		Ebean.createSqlUpdate("delete from Note").execute();
		return redirect("/");
	}

	@Logged(Role.ADMIN)
	public static Result delete(Long id) {
		Note.find.byId(id).delete();
		return redirect("/");
	}
	
	@With(Headers.class)
	public static Result openNote(Long id) {
		Note n = Note.find.byId(id);
		return ok(note.render(n));
	}
	
}
