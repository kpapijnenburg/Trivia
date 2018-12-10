package api.category;

import question.model.Category;

import java.util.List;

public interface ICategoryContext {
    List<Category> getAll();
}
