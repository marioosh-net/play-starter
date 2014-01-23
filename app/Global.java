import static play.mvc.Results.notFound;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;
import play.libs.F.Function0;
import play.libs.F.Promise;
import play.mvc.Http.RequestHeader;
import play.mvc.SimpleResult;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {
		Logger.info("Application has started");		
	}

	@Override
	public void onStop(Application app) {
		Logger.info("Application shutdown...");
	}

	@Override
	public Promise<SimpleResult> onError(RequestHeader header, Throwable t) {
		if (Play.isDev()) {
			return super.onError(header, t);
		}
		return pageNotFound();
	}

	@Override
	public Promise<SimpleResult> onHandlerNotFound(RequestHeader header) {
		if (Play.isDev()) {
			return super.onHandlerNotFound(header);
		}
		return pageNotFound();
	}
	
	@Override
	public Promise<SimpleResult> onBadRequest(RequestHeader header, String error) {
		if (Play.isDev()) {
			super.onBadRequest(header, error);
		}
		return pageNotFound();
	}
	
	/**
	 * page not found
	 * @return
	 */
	private static Promise<SimpleResult> pageNotFound() {
		return Promise.promise(new Function0<SimpleResult>() {
			@Override
			public SimpleResult apply() throws Throwable {
				return notFound("Page Not Found");
			}
		});		
	}
}
