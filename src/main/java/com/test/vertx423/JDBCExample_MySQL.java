package com.test.vertx423;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

public class JDBCExample_MySQL extends AbstractVerticle {

	private JsonObject configObj;
	private JsonObject queries;

	// Convenience method so you can run it in your IDE
	public static void main(String[] args) {
		System.out.println("welcome to JDBC example");
		Runner.runExample(JDBCExample_MySQL.class);
	}

	@Override
	public void start() throws Exception {
		System.out.println("start...............");
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("configObj " + configObj);
		System.out.println("queries " + queries);
		
		final JDBCClient client = JDBCClient.createShared(vertx,
				new JsonObject()
					.put("url", "jdbc:hsqldb:mem:test?shutdown=true")
					.put("driver_class", "org.hsqldb.jdbcDriver")
					.put("max_pool_size", 30).put("user", "SA")
					.put("password", ""));

		client.getConnection(conn -> {
			if (conn.failed()) {
				System.err.println(conn.cause().getMessage());
				return;
			}

			final SQLConnection connection = conn.result();
			connection.execute("create table person(id int primary key, name varchar(255), email varchar(255))", res -> {
				if (res.failed()) {
					throw new RuntimeException(res.cause());
				}
				connection.execute("insert into person values(1, 'Rahamath S', 'rahamath18@yahoo.com')", insert -> {
					
					connection.query("select * from person", rs -> {
						for (JsonArray line : rs.result().getResults()) {
							System.out.println(line.encode());
						}

						connection.close(done -> {
							if (done.failed()) {
								throw new RuntimeException(done.cause());
							}
						});
					});
				});
			});
		});
	}
}