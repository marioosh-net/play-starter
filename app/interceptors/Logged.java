package interceptors;

import java.util.Map;
import play.Logger;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;
import scala.actors.threadpool.Arrays;

public class Logged extends Action.Simple {

	@Override
	public Result call(Context paramContext) throws Throwable {
		if(paramContext.session().get("user") != null) {
			return delegate.call(paramContext);
		} else {
			return forbidden("You need to be logged here!");
		}
	}

}
