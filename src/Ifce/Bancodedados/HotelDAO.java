package Ifce.Bancodedados;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
public class HotelDAO {
	private Connection connection;
	public HotelDAO() {
		connection = new Conexao().getConnection();
	}
	
	public void insertGravadora(int id,String number,char status) {
		String query = "insert into client(rg_number,name,gender) values ("+id +",'"+number+"','" +status +"')";
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	public static void main(String[] args) {
		HotelDAO dao = new HotelDAO();
		dao.insertGravadora(2514512,"miguel",'m');
		
	}
}

//"insert into bedroom(id_type,number_bedroom,status_bedroom) values "
//+ "(" + id + "," + number +",'"+ status+ "')";
