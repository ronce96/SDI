package uo.sdi.business.impl.task.command;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.util.CategoryCheck;
import uo.sdi.infrastructure.Factories;
import uo.sdi.model.Category;
import uo.sdi.persistence.CategoryDao;

public class UpdateCategoryCommand{

	private Category category;

	public UpdateCategoryCommand(Category category) {
		this.category = category;
	}

	public Void execute() throws BusinessException {
		CategoryDao cDao = Factories.persistence.getCategoryDao();
		Category previous = cDao.findById(category.getId());

		checkCategoryExists(previous);
		CategoryCheck.nameIsNotNull(category);
		CategoryCheck.nameIsNotEmpty(category);
		if (nameIsChanged(previous, category)) {
			CategoryCheck.isUniqueName(category);
		}
		checkUserIsNotChanged(previous, category);

		cDao.update(category);
		return null;
	}

	private void checkUserIsNotChanged(Category previous, Category current)
			throws BusinessException {
//		BusinessCheck.isTrue(previous.getUserId().equals(current.getUserId()),
//				"A category cannot be changed to other user");
	}

	private boolean nameIsChanged(Category previous, Category current) {
		return !previous.getName().equals(current.getName());
	}

	private void checkCategoryExists(Category c) throws BusinessException {
		BusinessCheck.isNotNull(c, "The category does not exist");
	}

}
