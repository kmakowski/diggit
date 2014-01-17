package pl.kurman.diggit;

import com.jetdrone.vertx.yoke.Yoke;
import com.jetdrone.vertx.yoke.middleware.Favicon;
import com.jetdrone.vertx.yoke.middleware.Router;
import com.jetdrone.vertx.yoke.middleware.Static;
import com.jetdrone.vertx.yoke.middleware.YokeRequest;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.platform.Verticle;

/**
 *
 * @author Krzysztof Urman <krzysztof.urman at espeo.pl>
 */
public class DiggitVerticle extends Verticle {

    @Override
    public void start() {

        container.deployVerticle(DiggitRepoVerticle.class.getName());

        Yoke yoke = new Yoke(vertx)
            .use(new Favicon())
            .use("/static", new Static("."))
            .use(new Router()
                .get("/", (YokeRequest request) -> {
                    request.response().sendFile("index.html");
                })
                
                .get("/rest/links", (YokeRequest request) -> {
                    vertx.eventBus().send("diggit.links", null, (Message event) -> {

                        request.response().setContentType("application/json")
                        .end(event.body().toString());
                    });

                })).listen(8080);

        container.logger().info("HTTP server initialized");

    }
}
