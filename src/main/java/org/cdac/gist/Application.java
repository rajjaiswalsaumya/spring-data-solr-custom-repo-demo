package org.cdac.gist;

import org.cdac.gist.models.SearchDocument;
import org.cdac.gist.repositories.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

import java.util.List;

@SpringBootApplication
@EnableSolrRepositories(multicoreSupport = true)
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    SearchRepository searchRepository;

    @Override
    public void run(String... strings) throws Exception {
//        SearchDocument s = new SearchDocument("1", "mdk", "mahesh kulkarni");
//        searchRepository.save(s);


        List<SearchDocument> docs = searchRepository.findByOriginalTitleIn("mdk");

        System.out.println(searchRepository.findOne("1"));

        System.out.println(docs);
    }
}
