package com.example.ebookrepository;

import com.example.ebookrepository.model.Category;
import com.example.ebookrepository.model.Ebook;
import com.example.ebookrepository.model.Language;
import com.example.ebookrepository.model.User;
import com.example.ebookrepository.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EBookRepositoryProjectApplication implements CommandLineRunner {

    private final EbookService ebookService;

    @Autowired
    public EBookRepositoryProjectApplication(EbookService ebookService) {
        this.ebookService = ebookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(EBookRepositoryProjectApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        User user = new User("Marko", "Jokic",
                "mjokic", "p4ss123", "admin");
        Language language = new Language("srpski");
        Category category = new Category("cat1");
        user.setCategory(category);

        Ebook ebook = new Ebook("Tajtl", "fajl.pdf", language, category, user);

//        ebookService.addEditEbook(ebook);
    }
}
