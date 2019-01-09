package api.interfaces;

import question.model.Category;

import java.util.List;

public interface ICategoryService {

    /**
     * Gets all the available categories
     */
    List<Category> getAll();
}
