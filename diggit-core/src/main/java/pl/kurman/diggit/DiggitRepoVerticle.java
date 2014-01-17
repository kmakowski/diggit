package pl.kurman.diggit;

import com.jetdrone.vertx.yoke.extras.store.MongoDbStore;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

/**
 *
 * @author Krzysztof Urman <krzysztof.urman at espeo.pl>
 */
public class DiggitRepoVerticle extends Verticle {

    @Override
    public void start() {

        JsonObject persistorCfg = new JsonObject();
        persistorCfg.putString("host", "localhost");
        persistorCfg.putNumber("port", 27017);
        persistorCfg.putString("address", "mongo.diggit");
        persistorCfg.putString("db_name", "diggit");

        final EventBus eb = vertx.eventBus();

        container.deployModule("io.vertx~mod-mongo-persistor~2.0.0-beta1", persistorCfg);
        final MongoDbStore db = new MongoDbStore(eb, "mongo.diggit");

        eb.registerHandler("diggit.links", (Message event) -> {
            db.query("diggit", new JsonObject(), 0, 10, new JsonObject(), (AsyncResult<JsonArray> mongoResponse) -> {
                mongoResponse.result();
                event.reply(mongoResponse.result());
            });
        });

    }

}
