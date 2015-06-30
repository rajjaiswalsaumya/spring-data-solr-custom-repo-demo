package org.cdac.gist.repositories;

import org.apache.solr.client.solrj.SolrResponse;
import org.cdac.gist.models.SearchDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by grohit on 30-06-2015.
 */
public interface CustomSearchRepository {

    Page<SearchDocument> searchDocuments(SearchDocument criteria, Pageable page);

}
