package com.example.ebookrepository;

import com.example.ebookrepository.model.Category;
import com.example.ebookrepository.model.Ebook;
import com.example.ebookrepository.model.Language;
import com.example.ebookrepository.model.User;
import com.example.ebookrepository.service.EbookService;
import com.example.ebookrepository.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EBookRepositoryProjectApplication implements CommandLineRunner {

    private final EbookService ebookService;

    @Autowired
    public EBookRepositoryProjectApplication(EbookService service) {
        this.ebookService = service;
    }

    public static void main(String[] args) {
        SpringApplication.run(EBookRepositoryProjectApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        User user = new User("Marko", "Jokic", "m",
                "$2a$10$uXcHV5fz.y7j6xsXfm7CWOKtrA5eKFANdq5dm7ltHonogzG7PsP0u",
                "administrator");

        Language language = new Language("srpski");
        Category category = new Category("cat1");
        user.setCategory(category);

        Ebook ebook = new Ebook("Tajtl", "fajl.pdf", language, category, user);

//        ebookService.addEditEbook(ebook);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
