package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CategoryMapperTest {
    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void categoryToCategoryDTO() {
        Category category = new Category();
        category.setName("Joe");
        category.setId(1L);

        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        assertThat(categoryDTO.getId(), is(1L));
        assertThat(categoryDTO.getName(), is("Joe"));
    }
}