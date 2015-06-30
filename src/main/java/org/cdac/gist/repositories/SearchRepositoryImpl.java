package org.cdac.gist.repositories;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;
import org.cdac.gist.models.SearchDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.SolrResultPage;

import java.util.Collections;
import java.util.List;

/**
 * Created by grohit on 30-06-2015.
 */
public class SearchRepositoryImpl implements CustomSearchRepository {

    @Autowired
    SolrTemplate solrTemplate;


    public Page<SearchDocument> searchDocuments(SearchDocument criteria, Pageable page) {
        String[] words = criteria.getOriginalTitle().split(" ");
        Criteria conditions = createSearchConditions(words);
        SimpleQuery query = new SimpleQuery(conditions);
        query.setPageRequest(page);

        QueryPar


        SolrQuery solrQuery = queryParsers.getForClass(query.getClass()).constructSolrQuery(query);
        solrQuery.add(AUTHENTICATED_USER_NAME, criteria.getLoggedUsername());
        try {
            String queryString = this.queryParsers.getForClass(query.getClass()).getQueryString(query);
            solrQuery.set(CommonParams.Q, queryString);
            QueryResponse response = solrTemplate.getSolrServer().query(solrQuery);

            List<SearchDocument> beans = convertQueryResponseToBeans(response, SearchDocument.class);
            SolrDocumentList results = response.getResults();

            return new SolrResultPage<>(beans, query.getPageRequest(), results.getNumFound(), results.getMaxScore());
        } catch (SolrServerException e) {
            log.error(e.getMessage(), e);
            return new SolrResultPage<>(Collections.<SearchDocument>emptyList());
        }
    }

    private <T> List<T> convertQueryResponseToBeans(QueryResponse response, Class<T> targetClass) {
        return response != null ? convertSolrDocumentListToBeans(response.getResults(), targetClass) : Collections
                .<T>emptyList();
    }

    public <T> List<T> convertSolrDocumentListToBeans(SolrDocumentList documents, Class<T> targetClass) {
        if (documents == null) {
            return Collections.emptyList();
        }
        return solrTemplate.getConverter().read(documents, targetClass);
    }

    private Criteria createSearchConditions(String[] words) {
        return new Criteria("title").contains(words)
                .or(new Criteria("description").contains(words)  );
    }


}
