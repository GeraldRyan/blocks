package com.gerald.ryan.blocks.dbConnection;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class DBConnection {
	protected EntityManagerFactory emf = null;
	protected EntityManager em = null;
	private String pUName = "blocks";

	public void connect() {
		System.err.println("Persistence Provider: " + Persistence.PERSISTENCE_PROVIDER);
		System.err.println("Persistence Class: " + Persistence.class);
		this.emf = Persistence.createEntityManagerFactory(pUName);
		this.em = emf.createEntityManager();
	}

	public void disconnect() {
		if (this.em != null) {
			em.close();
		}
		if (this.emf != null) {
			emf.close();
		}
	}

	/**
	 * Heroku function in order to connect to jawsdb
	 * 
	 * @return
	 * @throws URISyntaxException
	 * @throws SQLException
	 */
	private static Connection getConnection() throws URISyntaxException, SQLException {
		URI jdbUri = new URI(System.getenv("JAWSDB_URL"));

		String username = jdbUri.getUserInfo().split(":")[0];
		String password = jdbUri.getUserInfo().split(":")[1];
		String port = String.valueOf(jdbUri.getPort());
		String jdbUrl = "jdbc:mysql://" + jdbUri.getHost() + ":" + port + jdbUri.getPath();

		return DriverManager.getConnection(jdbUrl, username, password);
	}
}
