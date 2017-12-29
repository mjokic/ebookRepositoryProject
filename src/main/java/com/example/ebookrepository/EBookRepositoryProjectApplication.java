package com.example.ebookrepository;

import com.example.ebookrepository.lucene.Indexer;
import com.example.ebookrepository.model.Category;
import com.example.ebookrepository.model.Ebook;
import com.example.ebookrepository.model.Language;
import com.example.ebookrepository.model.User;
import com.example.ebookrepository.service.CategoryService;
import com.example.ebookrepository.service.EbookService;
import com.example.ebookrepository.service.LanguageService;
import com.example.ebookrepository.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableScheduling
@SpringBootApplication
public class EBookRepositoryProjectApplication implements CommandLineRunner {

    private final EbookService ebookService;
    private final UserService userService;
    private final LanguageService languageService;
    private final CategoryService categoryService;

    @Autowired
    public EBookRepositoryProjectApplication(EbookService service,
                                             UserService userService,
                                             LanguageService languageService,
                                             CategoryService categoryService) {
        this.ebookService = service;
        this.userService = userService;
        this.languageService = languageService;
        this.categoryService = categoryService;
    }

    public static void main(String[] args) {
        SpringApplication.run(EBookRepositoryProjectApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        User user = new User("Marko", "Jokic", "m",
                "$2a$10$uXcHV5fz.y7j6xsXfm7CWOKtrA5eKFANdq5dm7ltHonogzG7PsP0u",
                "administrator");
//        userService.addEditUser(user);

        Language language1 = new Language("english");
        Language language2 = new Language("serbian");
        Language language3 = new Language("french");

//        languageService.addEditLanguage(language1);
//        languageService.addEditLanguage(language2);
//        languageService.addEditLanguage(language3);


        Category category1 = new Category("cat1");
        Category category2 = new Category("cat2");
        Category category3 = new Category("cat3");

//        categoryService.addEditCategory(category1);
//        categoryService.addEditCategory(category2);
//        categoryService.addEditCategory(category3);

//        Indexer.index();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
