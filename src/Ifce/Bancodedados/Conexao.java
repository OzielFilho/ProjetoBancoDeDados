package Ifce.Bancodedados;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
	private final static String driver = "org.postgresql.Driver";
	private final static String URL = "jdbc:postgresql://localhost:5432/Hotel";
	private final static String USUARIO = "postgres";
	private final static String SENHA = "1234";
	private Connection connection;
	public Conexao() {
		try {
			Class.forName(driver);
			setConnection(DriverManager.getConnection(URL,USUARIO,SENHA));
			System.out.println("conexãos sucedida ai sim porra");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
