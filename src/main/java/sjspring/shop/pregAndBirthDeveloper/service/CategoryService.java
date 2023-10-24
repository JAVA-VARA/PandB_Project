package sjspring.shop.pregAndBirthDeveloper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sjspring.shop.pregAndBirthDeveloper.domain.BoardCategory;
import sjspring.shop.pregAndBirthDeveloper.repository.BoardCategoryRepository;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final BoardCategoryRepository boardCategoryRepository;

    public BoardCategory save(String categoryName) {

        if(boardCategoryRepository.findByCategoryName(categoryName) == null){

            BoardCategory newCategory = BoardCategory.builder()
                    .categoryName(categoryName)
                    .build();

            boardCategoryRepository.save(newCategory);
        }
        return boardCategoryRepository.findByCategoryName(categoryName);

    }
}

