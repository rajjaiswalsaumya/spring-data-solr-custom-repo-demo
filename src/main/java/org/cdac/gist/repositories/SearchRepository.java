package org.cdac.gist.repositories;

import org.cdac.gist.models.SearchDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.data.solr.repository.SolrRepository;
import org.springframework.data.solr.core.query.Query.Operator;

import java.util.Collection;
import java.util.List;

import static org.cdac.gist.domain.SearchableDocumentDefinition.*;


/**
 * Created by grohit on 30-06-2015.
 */
public interface SearchRepository extends SolrCrudRepository<SearchDocument, String> {


    @Highlight(prefix = "<b>", postfix = "</b>")
    @Query(fields = {SEARCHDOCUMENT_ID, SEARCHDOCUMENT_ORIGINALTITLE,
            SEARCHDOCUMENT_DESCRIPTION}, defaultOperator = Operator.AND)
    List<SearchDocument> findByOriginalTitleIn(String names);


}
