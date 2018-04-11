package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;


public class CorsoDAO {

	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		
		final String sql = "SELECT * FROM corso";
		String jdbcURL= "jdbc:mysql://localhost/iscritticorsi?user=root&password=Martyannaraffa7";
		
		

		List<Corso> corsi = new LinkedList<Corso>();

		try {
		
			Connection conn=DriverManager.getConnection(jdbcURL);
			
			
			PreparedStatement st = conn.prepareStatement(sql);



			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");
				
			//	System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				Corso  temp=new Corso(codins, numeroCrediti, nome, periodoDidattico);
			if(!corsi.contains(temp))
					corsi.add(temp);
			}
			//Corso v=null;
			//corsi.add(v);
			conn.close();
			

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		return corsi;
	}

	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(String codCorso) {
		final String sql = "SELECT * FROM corso WHERE codins=?";
		String jdbcURL= "jdbc:mysql://localhost/iscritticorsi?user=root&password=Martyannaraffa7";
		
		List <Corso> studenti=new LinkedList<Corso>();
		try {
			Connection conn= DriverManager.getConnection(jdbcURL);
			PreparedStatement st = conn.prepareStatement(sql);
             st.setString(1, codCorso);
			 ResultSet rs = st.executeQuery();
			
		  while(rs.next()) {
			 Corso temp=new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"),
				rs.getInt("pd"));
			 return temp;
	
		  }
		  
		  conn.close();
		  return null;
		}
		
	    catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	   
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public void getStudentiIscrittiAlCorso(Corso corso) {
		// TODO
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		
		String sql = "INSERT IGNORE INTO `iscritticorsi`.`iscrizione` (`matricola`, `codins`) VALUES(?,?)";
		String jdbcURL= "jdbc:mysql://localhost/iscritticorsi?user=root&password=Martyannaraffa7";
		boolean returnValue = false;

		

		try {

			Connection conn= DriverManager.getConnection(jdbcURL);

			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, studente.getMatricola());

			st.setString(2, corso.getCodice());

			

			int res = st.executeUpdate();	



			if (res == 1)

				returnValue = true;



			conn.close();



		} catch (SQLException e) {

			e.printStackTrace();

			throw new RuntimeException("Errore Db");

		}

		

		return returnValue;
	}

	public List<Corso> trovaCorsi(int codiceMatricola) {
		final String sql = "SELECT codins FROM iscrizione WHERE matricola=?";
		String jdbcURL= "jdbc:mysql://localhost/iscritticorsi?user=root&password=Martyannaraffa7";
		
		List <Corso> corsi=new LinkedList<Corso>();
		try {
			Connection conn= DriverManager.getConnection(jdbcURL);
			PreparedStatement st = conn.prepareStatement(sql);
             st.setInt(1, codiceMatricola);
			 ResultSet rs = st.executeQuery();
			
		  while(rs.next()) {
			  Corso c=this.getCorso(rs.getString("codins"));
			  if(!corsi.equals(c) && c!=null)
			 corsi.add(c);
	
		  }
		  
		  conn.close();
		  return corsi;
		}
		
	    catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
		
	}

	public String verifica(String codice, int parseInt) {
		final String sql = "SELECT * FROM iscrizione WHERE codins=? AND matricola=?";
		String jdbcURL= "jdbc:mysql://localhost/iscritticorsi?user=root&password=Martyannaraffa7";
		
		List <Corso> corsi=new LinkedList<Corso>();
		try {
			Connection conn= DriverManager.getConnection(jdbcURL);
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codice);
        st.setInt(2, parseInt);
			 ResultSet rs = st.executeQuery();
			
		  while(rs.next()) {
			 return "Studente gia Iscritto\n";
		  }
		  
		  conn.close();
		  return null;
		
	}
		 catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	
}
}
