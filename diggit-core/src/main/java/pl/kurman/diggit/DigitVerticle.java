package pl.kurman.diggit;

import com.jetdrone.vertx.yoke.Yoke;
import com.jetdrone.vertx.yoke.middleware.Favicon;
import com.jetdrone.vertx.yoke.middleware.Router;
import com.jetdrone.vertx.yoke.middleware.Static;
import com.jetdrone.vertx.yoke.middleware.YokeRequest;
import org.vertx.java.platform.Verticle;

/**
 *
 * @author Krzysztof Urman <krzysztof.urman at espeo.pl>
 */
public class DigitVerticle extends Verticle {

    @Override
    public void start() {

        Yoke yoke = new Yoke(vertx)
                .use(new Favicon())
                .use("/static", new Static("."))
                .use(new Router()
                        .get("/", (YokeRequest request) -> {
                          request.response().sendFile("index.html");
                        })
                        .all("/hello", (YokeRequest request) -> {
                            request.response().end("Hello World!");
        })).listen(8080);

        container.logger().info("HTTP server initialized");

    }
}
