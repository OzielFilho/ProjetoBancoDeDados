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
import java.util.Scanner;

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
					System.out.print("Usuária: "+ rs.getString("name")+ " com o rg: "+ rs.getInt("rg_number") + "\n");
				}else {
					System.out.println("Usuário encontrado !!");
					System.out.print("Usuário: "+ rs.getString("name")+ " com o rg: "+ rs.getInt("rg_number")+ "\n");
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
	public boolean QuartoinDatabase(int id) {
		Statement stmt;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from bedroom where status_bedroom = 'disponivel'");
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public void mostrarQuartos() {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from bedroom where status_bedroom = 'disponivel'");
			while(rs.next()) {
				System.out.print("Quarto numero "+ rs.getInt("number_bedroom"));
				Statement stm = connection.createStatement();
				ResultSet q = stm.executeQuery("select * from type_bedroom where id_type ="+ rs.getString("id_type"));
				if(q.next()) {
					System.out.print("\t descrição: " + q.getString("description_reservation"));
					System.out.print("\t preço:" + q.getDouble("price"));
					System.out.println();
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
	
	public void updateBedroom(int number_bedroom) {
        try {
    		String query = "update bedroom set status_bedroom = 'fechado' where number_bedroom = " + number_bedroom;
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

	public void CreateReservation(int rg_number, int number_bedroom,int qt_days) {
		String query = "insert into Reservation(number_bedroom,rg_number,date_reservation"
				+ ",qt_days,date_in,status_reservation) values ("+number_bedroom +","+rg_number+","+ "current_timestamp" +
				","+ qt_days+ "," + "current_timestamp" + ","+ "'em andamento'" +")";
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void CreateLodging(int rg_number, int number_bedroom) {		
		String query = "insert into Lodging(rg_number,number_bedroom,date_in"
				+ ",status_lodging) values ("+rg_number+"," +number_bedroom+"," + "current_timestamp" +
				","+ "'em andamento'" +")";
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query);	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public boolean insertClient(int rg_number,String name,char gender) {
		int resultado = 0;
		String query = "insert into client(rg_number,name,gender) values ("+rg_number +",'"+name+"','" +gender +"')";
		
		try {
			Statement stmt = connection.createStatement();
			resultado = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(resultado == 1) {
			System.out.println("USUÁRIO CADASTRADO COM SUCESSO !");
			return true;
		}else {
			System.out.println("USUÁRIO NÃO CADASTRADO");
			return false;

		}
	}
	
	public void checkOut(int number_bedroom) {
        String query = "update lodging set status_lodging = 'fechado' where number_bedroom = " + number_bedroom;
        String query_2 = "update reservation set status_reservation = 'fechado' where number_bedroom = " + number_bedroom;
        String query_3 = "update bedroom set status_bedroom = 'disponivel' where number_bedroom = " + number_bedroom;

        try {
            Statement stmt = connection.createStatement();
            Statement stmt_2 = connection.createStatement();
            Statement stmt_3 = connection.createStatement();
            int lodging = stmt.executeUpdate(query);
            int reservation = stmt_2.executeUpdate(query_2);
            int bedroom = stmt_3.executeUpdate(query_3);

            if (lodging == 1 && reservation == 1 && bedroom  == 1) {
                System.out.println("Check out Ok");
            } else {
                System.out.println("Check out invalido");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
	
	public void CheckIn(int rg_number,int number_bedroom,int qt_days) {
		updateBedroom(number_bedroom);
		CreateReservation(rg_number,number_bedroom,qt_days);
		CreateLodging(rg_number,number_bedroom);
	}
	
	public static void main(String[] args) {
		HotelDAO dao = new HotelDAO();
		System.out.println("BEM VINDO AO HOTEL "
				+ "\n Digite seu rg: ");
		Scanner s = new Scanner(System.in);
		int rg = s.nextInt();
		boolean resposta = dao.ClientInDatabase(rg);
		System.out.println("digite a reserva que vc quer fechar");
		Scanner reserva = new Scanner(System.in);
		int id = s.nextInt();
		dao.checkOut(id);
		/*
		 * if(resposta == false) {	
			System.out.println("se deseja se cadastrar digite seu nome e gênero: ");
			System.out.println("nome: ");
			Scanner n = new Scanner(System.in);
			String nome = n.nextLine();
			System.out.println("genero(M/F): ");
			Scanner g = new Scanner(System.in);
			String genderLine = g.nextLine();
			char genero = genderLine.charAt(0);
			dao.insertClient(rg,nome,genero);
		}
		
			System.out.println("Quartos disponíveis:  ");
			dao.mostrarQuartos();
			System.out.println("Qual quarto você vai querer?(digite o numero do quarto): ");
			Scanner q = new Scanner(System.in);
			int quarto = q.nextInt();
			System.out.println("Quantos dias você vai querer passar?: ");
			Scanner d = new Scanner(System.in);
			int dias = d.nextInt();
			dao.CheckIn(rg,quarto,dias);
			
		 */
		
	}
}
/**
 * Statement stm = connection.createStatement();
    		ResultSet rs = stm.executeQuery("select * from bedroom where number_bedroom = "+ number_bedroom);
    		if(rs.next()) {
    			if(rs.getString("status_bedroom") == "fechado") {
    				System.out.println("quarto indisponível, selecione outro");
    				Scanner s = new Scanner(System.in);
    				int id = s.nextInt();
    				updateBedroom(id);
    			}
    		}
 */
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
