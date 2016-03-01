package hr.fer.zemris.java.tecaj_13.dao.sql;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;



import hr.fer.zemris.java.tecaj_13.model.Glasanje;
import hr.fer.zemris.java.tecaj_13.model.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova
 * konkretna implementacija očekuje da joj veza stoji na raspolaganju
 * preko {@link SQLConnectionProvider} razreda, što znači da bi netko
 * prije no što izvođenje dođe do ove točke to trebao tamo postaviti.
 * U web-aplikacijama tipično rješenje je konfigurirati jedan filter 
 * koji će presresti pozive servleta i prije toga ovdje ubaciti jednu
 * vezu iz connection-poola, a po zavrsetku obrade je maknuti.
 *  
 * @author Petra Marče
 */
public class SQLDAO implements DAO {

	@Override
	public void glasaj(long idOpcije, long pollId) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try{
		pst=con.prepareStatement("UPDATE PollOptions SET votesCount=votesCount+1 WHERE id=? AND pollID=?");
		pst.setLong(1, idOpcije);
		pst.setLong(2, pollId);
		
		pst.executeUpdate();
		try{
		pst.close();
		} finally {
			try { pst.close(); } catch(Exception ignorable) {}
		}
		}catch(Exception ex) {
			throw new DAOException("Pogreška prilikom izvođenja UPDATE naredbe.", ex);
		}
	}

	@Override
	public List<Result> dohvatiRezultate(long pollId) throws DAOException {
		List<Result> rezultati=new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try{
			pst=con.prepareStatement("SELECT id,optionTitle,optionLink,votesCount,pollId from PollOptions WHERE pollID=?");
			pst.setLong(1, pollId);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						rezultati.add(new Result(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getLong(4),rs.getLong(5)));
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		}catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste rezultata.", ex);
		}
		return rezultati;
	}

	@Override
	public Glasanje dohvatiPodatkeOGlasanju(long pollId) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try{
			pst=con.prepareStatement("SELECT id,title,message from Polls WHERE id=?");
			pst.setLong(1, pollId);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if(rs!=null && rs.next()) {
					return new Glasanje(rs.getLong(1), rs.getString(2), rs.getString(3));
					}else{
						return null;
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		}catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste rezultata.", ex);
		}
	}

	@Override
	public List<Glasanje> dohvatiSvaGlasanja() throws DAOException {
		List<Glasanje> rezultati=new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try{
			pst=con.prepareStatement("SELECT id,title,message from Polls");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						rezultati.add(new Glasanje(rs.getLong(1), rs.getString(2), rs.getString(3)));
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		}catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste rezultata.", ex);
		}
		return rezultati;
	}


	@Override
	public void ubaciGlasanje(Glasanje glasanje) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try{
		
				pst=con.prepareStatement("INSERT INTO Polls(title,message) values (?,?)");
				pst.setString(1, glasanje.getTitle());
				pst.setString(2, glasanje.getMessage());
				try {
				pst.executeUpdate();
					
				
				} finally {
					try { pst.close(); } catch(Exception ignorable) {}
			}
			
			
		}catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste rezultata.", ex);
		}
	}


	@Override
	public void ubaciRezultate(List<Result> zapisi) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;

		try{
			for(Result g:zapisi){
				pst=con.prepareStatement("insert into polloptions(optiontitle,optionLink,pollID,votesCount) values  (?,?,?,?)");
				pst.setString(1, g.getOptionTitle());
				pst.setString(2, g.getOptionLink());
				pst.setLong(3, g.getPollId());
				pst.setLong(4, g.getGlasovi());
				try {
				pst.executeUpdate();
					
				
				} finally {
					try { pst.close(); } catch(Exception ignorable) {}
			}
			
			}
		}catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste rezultata.", ex);
		}
	}

	@Override
	public Glasanje dohvatiGlasanje(String name) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try{
			pst=con.prepareStatement("SELECT * from Polls WHERE title=?");
			pst.setString(1, name);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if(rs!=null && rs.next()) {
					return new Glasanje(rs.getLong(1), rs.getString(2), rs.getString(3));
					}else{
						return null;
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		}catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste rezultata.", ex);
		}
		
	}
	
	@Override
	public Glasanje dohvatiGlasanje(long id) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try{
			pst=con.prepareStatement("SELECT * from Polls WHERE id=?");
			pst.setLong(1, id);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if(rs!=null && rs.next()) {
					return new Glasanje(rs.getLong(1), rs.getString(2), rs.getString(3));
					}else{
						return null;
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		}catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste rezultata.", ex);
		}
		
	}


}