package Ifce.Bancodedados;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class HotelDAO {
	private Connection connection;
	public HotelDAO() {
		connection = new Conexao().getConnection();
	}
	
	public boolean ClientInDatabase(int id) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from client where rg_number ="+id);
			if(rs.next()) {
				if(rs.getString("gender") == "F" || rs.getString("gender") == "f" ){
					System.out.println("Usuária Encontrada!!");
					System.out.print("Usuária: "+ rs.getString("name")+ " com o rg: "+ rs.getInt("rg_number") + "?");
				}else {
					System.out.println("Usuário encontrado !!");
					System.out.print("Usuário: "+ rs.getString("name")+ "//rg: "+ rs.getInt("rg_number"));
				}
				return true;
			}else {
				System.out.println("Usuário não encontrado");
				return false;			
			}
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public void createReserva() {
		
	}
	
	public void mostrarQuartos() {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Bedroom where status = livre");
			while(rs.next()) {
				ResultSet q = stmt.executeQuery("select * from type_bedroom where id_type ="+ rs.getString("id_type"));
				System.out.println("Quarto numero "+ rs.getInt("id_type"));
				if(q.next()) {
					System.out.print("/tdescrição: " + q.getString("description_reservation"));
					System.out.print("/tpreço:" + q.getDouble("price"));
					System.out.println();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
	
	public void updateBedroom(int id_type) {
        String query = "update bedroom set status_bedroom = 'fechado' where id_type = " + id_type;
        try {
            Statement stmt = connection.createStatement();
            int reserva = stmt.executeUpdate(query);
            if (reserva == 1) {
                System.out.println("Reserva realizada");
            } else {
                System.out.println("Reserva não realizada");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
	public void CheckIn() {
		
	}
	public void CreateReservation(int rg_number, int number_bedroom,String date_Reservation,int qt_days,String date_in) {
		String query = "insert into Reservation(number_bedroom,rg_number,id_reservation,date_reservation"
				+ ",qt_days,date_in,status_reservation) values ("+number_bedroom +","+rg_number+"," +1+"," + date_Reservation +
				","+ qt_days+ "," + date_in+ ","+ "em andamento" +")";
		
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void CreateLodging(int rg_number, int number_bedroom,LocalDate date_in) {		
		String query = "insert into Lodging(rg_number,number_bedroom,date_in"
				+ ",status_lodging) values ("+rg_number+"," +number_bedroom+"," + date_in +
				","+ "'em andamento'" +")";
		
		try {
			Statement stmt = connection.createStatement();
			
			stmt.executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void insertClient(int rg_number,String name,char gender) {
		
		String query = "insert into client(rg_number,name,gender) values ("+rg_number +",'"+name+"','" +gender +"')";
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	
	public static void main(String[] args) {
		HotelDAO dao = new HotelDAO();
		//dao.insertClient(2514512,"miguel",'m');
		dao.CreateLodging(256315, 1,LocalDate.now());
		
		
		
	}
}

//"insert into bedroom(id_type,number_bedroom,status_bedroom) values "
//+ "(" + id + "," + number +",'"+ status+ "')";

/*
 * 		Scanner leitor = new Scanner(System.in);
				String resposta = leitor.nextLine();
				if(resposta.toUpperCase() == "SIM" || resposta == "s" || resposta == "S") {
					int rg;
					String name;
					char gender;
					System.out.println("Digite seu rg,nome e genero");
					System.out.println ("Digite seu Rg");  
					rg = leitor.nextInt();
					System.out.println ("Digite seu Nome");
					name = leitor.nextLine();
					System.out.println ("Digite seu genero (H/M)");
					gender = (char) leitor.nextShort();
					System.out.println ("Usuário cadastrado");
					
				}
				
 * */
