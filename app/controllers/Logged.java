package controllers;

import java.util.Map;
import play.Logger;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;
import scala.actors.threadpool.Arrays;

public class Logged extends Action.Simple {

	@Override
	public Result call(Context paramContext) throws Throwable {

		Logger.debug("Intercepted");
		Map<String, String[]> m = paramContext.request().headers();
		for(String k: m.keySet()) {
			Logger.debug(k + " = " + Arrays.asList(m.get(k))+"");
		}
		
		if(paramContext.session().get("user") != null) {
			return delegate.call(paramContext);
		} else {
			return forbidden("You need to be logged here!");
		}
	}

}
