package interceptors;

import other.Role;
import play.libs.F.Function0;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.SimpleResult;

public class LoggedAction extends Action<Logged> {

	@Override
	public Promise<SimpleResult> call(Context paramContext) throws Throwable {
		Role role = configuration.value();
		if (paramContext.session().get("user") != null) {
			if (paramContext.session().get("role") != null && paramContext.session().get("role").equals(role.name())) {
				return delegate.call(paramContext);
			} else {
				return forbidden1(role);
			}
		} else {
			return forbidden1(null);
		}
	}
	
	/**
	 * forbidden
	 * @param role
	 * @return
	 */
	private static Promise<SimpleResult> forbidden1(final Role role) {
		return Promise.promise(new Function0<SimpleResult>() {
			@Override
			public SimpleResult apply() throws Throwable {
				if(role != null) {
					return forbidden("You need to be logged with role " + role.name() + " here!");
				} else {
					return forbidden("You need to be logged here!");
				}
			}
		});		
	}

}
