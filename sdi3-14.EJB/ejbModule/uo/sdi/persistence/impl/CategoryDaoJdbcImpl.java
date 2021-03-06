package uo.sdi.persistence.impl;

import java.util.List;

import javax.persistence.EntityManager;

import uo.sdi.model.Category;
import uo.sdi.persistence.CategoryDao;
import uo.sdi.persistence.util.Jpa;

public class CategoryDaoJdbcImpl implements CategoryDao {

	@Override
	public Category save(Category dto) {
		Jpa.getManager().persist(dto);
		return dto;
	}

	@Override
	public Category update(Category dto) {
		Jpa.getManager().persist(dto);
		return dto;
	}

	@Override
	public Category delete(Long id) {
		EntityManager em = Jpa.getManager();
		Category cat = em.find(Category.class, id);
		if (cat != null)
			em.remove(cat);
		return cat;
	}

	@Override
	public Category findById(Long id) {
		return Jpa.getManager().find(Category.class, id);
	}

	@Override
	public List<Category> findAll() {
		return Jpa.getManager()
				.createNamedQuery("Category.findAll", Category.class)
				.getResultList();

	}

	@Override
	public List<Category> findByUserId(Long userId) {
		return Jpa
				.getManager()
				.createNamedQuery("Category.findByUserId",
						Category.class).setParameter(1, userId).getResultList();
	}

	@Override
	public int deleteAllFromUserId(Long userId) {
		return Jpa.getManager()
				.createNamedQuery("Category.deleteByUserId")
				.setParameter(1, userId).executeUpdate();
	}

	@Override
	public Category findByUserIdAndName(Long userId, String name) {
		List<Category> res = Jpa
				.getManager()
				.createNamedQuery(
						"Category.findByUserIdAndName",
						Category.class)
				.setParameter(1, userId)
				.setParameter(2, name).getResultList();

		return res.size() == 0 ? null : res.get(0);
	}

	@Override
	public void deleteAll() {
		Jpa.getManager().createNamedQuery("Category.deleteAll")
				.executeUpdate();
	}

}
