package api.category;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import question.model.Category;

import java.util.List;

@RestController
public class CategoryController {
    private CategoryRepository repository = new CategoryRepository(new CategoryContext());

    @RequestMapping(value = "/category" , method = RequestMethod.GET)
    public List<Category> login() {
        return repository.getAll();
    }
}
