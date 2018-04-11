package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {


	public List<Studente> caricaIscritti(Corso corso) {
		final String sql = "SELECT matricola FROM iscrizione WHERE codins=?";
		String jdbcURL= "jdbc:mysql://localhost/iscritticorsi?user=root&password=Martyannaraffa7";
		
		List <Studente> studenti=new LinkedList<Studente>();
		try {
			Connection conn= DriverManager.getConnection(jdbcURL);
			PreparedStatement st = conn.prepareStatement(sql);



			
		    st.setString(1, corso.getCodice());
			
		    ResultSet rs = st.executeQuery();
		  while(rs.next()) {
			 
			Studente t= this.caricaStudenti(rs.getInt("matricola"));
			 if(!studenti.equals(t))
			studenti.add(t);
			  
		  }
		  
		  conn.close();
		  return studenti;
		}
		
	    catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
}
	
	
	
	
	
	public Studente caricaStudenti(int matricola) {

		final String sql = "SELECT * FROM studente where matricola=?";
		String jdbcURL= "jdbc:mysql://localhost/iscritticorsi?user=root&password=Martyannaraffa7";
		Studente studente = null;

		try {

			Connection conn= DriverManager.getConnection(jdbcURL);

			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, matricola);



			ResultSet rs = st.executeQuery();



			if (rs.next()) {

				studente = new Studente(matricola, rs.getString("nome"), rs.getString("cognome"), rs.getString("cds"));

			}



			conn.close();



		} catch (SQLException e) {

			e.printStackTrace();

			throw new RuntimeException("Errore Db");

		}

		return studente;

	}





	public boolean isStudenteIscrittoACorso(Studente studente, Corso corso) {
		
	
		
		final String sql = "SELECT * FROM iscrizione where codins=? and matricola=?";
		String jdbcURL= "jdbc:mysql://localhost/iscritticorsi?user=root&password=Martyannaraffa7";
		boolean returnValue = false;



		try {


			Connection conn= DriverManager.getConnection(jdbcURL);

			PreparedStatement st = conn.prepareStatement(sql);
			

			st.setString(1, corso.getCodice());

			st.setInt(2, studente.getMatricola());



			ResultSet rs = st.executeQuery();



			if (rs.next())

				returnValue = true;



			conn.close();



		} catch (SQLException e) {

			e.printStackTrace();

			throw new RuntimeException("Errore Db");

		}



		return returnValue;
	}






	
	
}
