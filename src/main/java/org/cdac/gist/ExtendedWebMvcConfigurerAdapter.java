package org.cdac.gist;

import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class ExtendedWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Autowired
    SolrServer solrServer;

    @Bean
    public SolrTemplate getSolrTemplate() {
        return new SolrTemplate(solrServer);
    }

    @Bean
    public WebMvcConfigurerAdapter mvcViewConfigurer() {

        return new WebMvcConfigurerAdapter() {

            @Override
            public void addArgumentResolvers(
                    List<HandlerMethodArgumentResolver> argumentResolvers) {
                argumentResolvers
                        .add(new PageableHandlerMethodArgumentResolver());
            }

        };
    }

}
