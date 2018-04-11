package it.polito.tdp.lab04.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;



public class Model {
	
	
private List <Corso> corsi;
	
	public Model() {
		this.corsi=new ArrayList <Corso>();
	}
	
	
	public List<Corso> aggiungiCorsi() {
		CorsoDAO dao=new CorsoDAO();
		
		return dao.getTuttiICorsi();
	}


	public Studente completaStudente(int temp) {
		StudenteDAO ed= new StudenteDAO();
		
		
		return ed.caricaStudenti(temp);
	}


	public List<Studente> studentiIscrittiAlCorso(Corso corso) {
       StudenteDAO ed= new StudenteDAO();
		
		
		
		return ed.caricaIscritti(corso);

	}


	public List<Corso> cercaCorsi(int temp) {
		CorsoDAO dao=new CorsoDAO();
		return dao.trovaCorsi(temp);
	}


	public String verificaIscrizione(String codice, int parseInt) {
		
		CorsoDAO dao=new CorsoDAO();
		return dao.verifica(codice ,parseInt);
	}


	public boolean isStudenteIscrittoACorso(Studente studente, Corso corso) {
		 StudenteDAO studenteDao= new StudenteDAO();
		return studenteDao.isStudenteIscrittoACorso(studente, corso);
	}


	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		CorsoDAO corsoDao=new CorsoDAO();
		return corsoDao.inscriviStudenteACorso(studente, corso);
	}
	

}
