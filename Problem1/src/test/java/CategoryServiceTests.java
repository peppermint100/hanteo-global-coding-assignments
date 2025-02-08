import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.hanteo.domain.Category;
import org.hanteo.repository.InMemoryCategoryRelationRepository;
import org.hanteo.repository.InMemoryCategoryRepository;
import org.hanteo.service.CategoryService;
import org.hanteo.service.InMemoryCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceTests {
    private static final Logger log = Logger.getLogger("CategoryServiceTest");

    private CategoryService categoryService;
    private Category rootCategory;

    @BeforeEach
    void setUp() {
        // 카테고리 서비스에 필요한 의존성을 주입한다.
        categoryService = buildCategoryService();
        // 과제 예시 사진의 카테고리를 만든다.
        rootCategory = buildExampleCategory();
        // 과제 예시 사진의 카테고리를 인메모리 repository에 저장한다.
        categoryService.addNewCategory(rootCategory);
    }

    @Test
    @DisplayName("카테고리 식별자로 검색할 수 있으며 하위 카테고리들을 담고 있다.")
    void getMenCategoryById() {
        Category menCategory = categoryService.getByCategoryId(1L);

        log.info("남자 카테고리");
        log.info(menCategory.toJson());

        assertEquals(menCategory.getName(), "남자");
        assertTrue(menCategory.getChildren().size() > 0);
    }

    @Test
    @DisplayName("공지사항 게시판들은 이름이 같지만 각각 다른 게시판이다.")
    void getNoticeCategories() {
        List<Category> noticeCategories = categoryService.getByCategoryName("공지");

        log.info("공지사항 게시판 목록");
        noticeCategories.forEach(category ->
                log.info(category.toJson())
        );

        Set<Long> noticeIds = noticeCategories
                .stream()
                .map(category -> category.getId() )
                .collect(Collectors.toSet());

        for (Category noticeCategory : noticeCategories) {
            assertTrue(noticeCategory.getName().contains("공지"));
        }
        assertTrue(noticeIds.size() > 1);
    }

    @Test
    @DisplayName("익명 게시판은 모두 같은 게시판이 각각 다른 카테고리에 소속되어 있다.")
    void getAnonymousBoard() {
        List<Category> anonymousBoards = categoryService.getByCategoryName("익명게시판");

        log.info("익명게시판");
        anonymousBoards.forEach(category ->
                log.info(category.toJson())
        );

        assertEquals(anonymousBoards.size(), 1);
    }

    private CategoryService buildCategoryService() {
        InMemoryCategoryRelationRepository relationRepository = new InMemoryCategoryRelationRepository();
        InMemoryCategoryRepository categoryRepository = new InMemoryCategoryRepository();
        return new InMemoryCategoryService(categoryRepository, relationRepository);
    }

    private Category buildExampleCategory() {
        Category root = Category.of(0L, "ROOT");

        Category men = Category.of(1L, "남자");
        Category women = Category.of(2L, "여자");
        root.addChild(men);
        root.addChild(women);

        Category exo = Category.of(3L, "엑소");
        Category bts = Category.of(4L, "방탄소년단");
        Category blackPink = Category.of(5L, "블랙핑크");
        men.addChild(exo);
        men.addChild(bts);
        women.addChild(blackPink);

        Category exoNotice = Category.of(6L, "공지사항");
        Category btsNotice = Category.of(7L, "공지사항");
        Category blackPinkNotice = Category.of(8L, "공지사항");
        Category anonymousBoard = Category.of(9L, "익명게시판");

        Category chen = Category.of(10L, "첸");
        Category baekhyun = Category.of(11L, "백현");
        Category siumin = Category.of(12L, "시우민");
        Category v = Category.of(13L, "뷔");
        Category rose = Category.of(14L, "로제");

        exo.addChild(exoNotice);
        exo.addChild(chen);
        exo.addChild(baekhyun);
        exo.addChild(siumin);
        exo.addChild(anonymousBoard);

        bts.addChild(btsNotice);
        bts.addChild(anonymousBoard);
        bts.addChild(v);

        blackPink.addChild(blackPinkNotice);
        blackPink.addChild(anonymousBoard);
        blackPink.addChild(rose);

        return root;
    }
}