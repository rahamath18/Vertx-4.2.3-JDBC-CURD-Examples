package com.test.vertx423;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

public class LoadVariousConfigFiles extends AbstractVerticle {

	// Convenience method so you can run it in your IDE
	public static void main(String[] args) {
		System.out.println("welcome to Load Various Config Files Example");
		Runner.runExample(LoadVariousConfigFiles.class);
	}

	@Override
	public void start() throws Exception {

		ConfigRetrieverOptions options = new ConfigRetrieverOptions();
		
		options.addStore(new ConfigStoreOptions().setType("file").setFormat("properties")
				.setConfig(new JsonObject().put("path", "application.properties")));

		// hocon file support properties file format
		options.addStore(new ConfigStoreOptions().setType("file").setFormat("hocon")
				.setConfig(new JsonObject().put("path", "db.hocon")));
		
		// hocon file support json file format
		options.addStore(new ConfigStoreOptions().setType("file").setFormat("hocon")
				.setConfig(new JsonObject().put("path", "config.hocon")));
		
		// hocon file support json file format
		options.addStore(new ConfigStoreOptions().setType("file").setFormat("hocon")
				.setConfig(new JsonObject().put("path", "query.hocon")));
				
		ConfigRetriever retriever = ConfigRetriever.create(vertx, options);
		retriever.getConfig(ar -> {
			if (ar.succeeded()) {
				JsonObject jo = ar.result();
				vertx.getOrCreateContext().config().mergeIn(jo);
				System.out.println("Connection............" + vertx.getOrCreateContext().config().getJsonObject("connection"));
			} else {
				System.out.println("Error............");
			}
		});
		//print(options);

	}

	public Future<JsonObject> ReadAsynchronously(ConfigRetrieverOptions options) {
		Promise<JsonObject> promise = Promise.promise();
		ConfigRetriever retriever = ConfigRetriever.create(vertx, options);
		retriever.getConfig(ar -> {
			if (ar.succeeded()) {
				JsonObject jo = ar.result();
				promise.complete(jo);
			} else {
				promise.fail(ar.cause());
			}
		});

		return promise.future();
	}

	private void print(ConfigRetrieverOptions options) {
		System.out.println("ConfigRetrieverOptions " + options.toJson());
//		System.out.println("ConfigRetrieverOptions " + options.toJson().encodePrettily());
		ReadAsynchronously(options).onComplete((AsyncResult<JsonObject> handler) -> {
			if (handler.succeeded()) {
				System.out.println("File Content\n " + handler.result().encodePrettily());
			} else if (handler.failed()) {
				System.out.println("Failed calling ReadAsynchronously()");
			}
		});
	}
}
