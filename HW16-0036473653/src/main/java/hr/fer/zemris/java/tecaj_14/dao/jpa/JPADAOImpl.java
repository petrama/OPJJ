package hr.fer.zemris.java.tecaj_14.dao.jpa;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.tecaj_14.dao.DAO;
import hr.fer.zemris.java.tecaj_14.dao.DAOException;
import hr.fer.zemris.java.tecaj_14.model.BlogComment;
import hr.fer.zemris.java.tecaj_14.model.BlogEntry;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;

public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}
	
	public List<BlogUser> getUserByNick(String nick) throws DAOException{
		EntityManager em=JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<BlogUser> postojeci=em.createNamedQuery("BlogUser.dohvatNick").setParameter("ni", nick).getResultList();
		return postojeci;
	}

	@Override
	public void insertNewBlogUser(String firstName, String lastName,
			String email, String nick, String passwordHash) {
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogUser novi=new BlogUser();
		novi.setFirstName(firstName);
		novi.setLastName(lastName);
		novi.setNick(nick);
		novi.setPasswordHash(passwordHash);
		novi.setEmail(email);
		em.persist(novi);

		
	}

	@Override
	public List<BlogUser> getAllUsers() throws DAOException {
		EntityManager em=JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<BlogUser> sviPostojeci=em.createNamedQuery("BlogUser.dohvatiSve").getResultList();
		return sviPostojeci;
	}

//	@Override
//	public BlogEntry getBlogEntry(BlogUser creator, Long id) {
//		EntityManager em=JPAEMProvider.getEntityManager();
//		@SuppressWarnings("unchecked")
//		List<BlogEntry> sviPostojeci=em.createNamedQuery("BlogEntry.upit2").setParameter("id", id).setParameter("cr", creator).getResultList();
//		if(sviPostojeci.isEmpty()) return null;
//		return sviPostojeci.get(0);
//	}

	@Override
	public void addNewEntry(String title, String text, String creatorNick) {
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogEntry novi=new BlogEntry();
		novi.setText(text);
		novi.setTitle(title);
		novi.setCreator(getUserByNick(creatorNick).get(0));
		novi.setCreatedAt(new Date());
		em.persist(novi);
		
	}
	
	public void updateEntry(String creator,Long id,String title,String text){
		EntityManager em=JPAEMProvider.getEntityManager();
		BlogEntry be=getBlogEntry(id);
//		BlogEntry be=getBlogEntry(getUserByNick(creator).get(0),id);
		be.setText(text);
		be.setTitle(title);
		be.setLastModifiedAt(new Date());
		em.persist(be);
	}

	@Override
	public void addNewComment(String entry, String text, String email) {
		EntityManager em=JPAEMProvider.getEntityManager();
		BlogComment com=new BlogComment();
		com.setMessage(text);
		com.setUsersEMail(email);
		com.setBlogEntry(getBlogEntry(Long.parseLong(entry)));
		com.setPostedOn(new Date());
		em.persist(com);
	}

}
