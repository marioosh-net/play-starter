package controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import model.MenuItem;
import play.api.templates.Html;
import play.mvc.Controller;
import play.mvc.Result;
import scala.actors.threadpool.Arrays;
import views.html.home;
import views.html.menu;
import com.google.gdata.util.ServiceException;

public class Application extends Controller {
	
	static final String ADMIN_PASSWORD = play.Play.application().configuration().getString("admin.password");

	public static Html menu() {
		return menu.render(Arrays.asList(new MenuItem[]{
				new MenuItem("Home"), 
				new MenuItem("Information"),
				new MenuItem("Contact"),
				new MenuItem("Test")
				}));
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
	
}
