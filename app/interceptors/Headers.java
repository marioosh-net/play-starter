package interceptors;

import java.util.Arrays;
import java.util.Map;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.SimpleResult;

public class Headers extends Action.Simple {

	@Override
	public Promise<SimpleResult> call(Context paramContext) throws Throwable {
		Logger.debug("Intercepted");
		Map<String, String[]> m = paramContext.request().headers();
		for(String k: m.keySet()) {
			Logger.debug(k + " = " + Arrays.asList(m.get(k))+"");
		}
		return delegate.call(paramContext);
	}

}
