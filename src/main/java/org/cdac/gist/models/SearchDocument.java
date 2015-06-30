package org.cdac.gist.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.util.StringUtils;

import java.util.Comparator;

import static org.cdac.gist.domain.SearchableDocumentDefinition.*;

@SolrDocument(solrCoreName = SEARCHDOCUMENT_CORE)
@JsonInclude(Include.NON_NULL)
public class SearchDocument implements Comparable<SearchDocument>, Comparator<SearchDocument>, Cloneable {

    @Id
    @Field(SEARCHDOCUMENT_ID)
    @Indexed
    @JsonInclude(Include.NON_NULL)
    String id;

    @Field(SEARCHDOCUMENT_ORIGINALTITLE)
    @Indexed
    @JsonInclude(Include.NON_NULL)
    String originalTitle;

    @Field(SEARCHDOCUMENT_DESCRIPTION)
    @Indexed
    @JsonInclude(Include.NON_NULL)
    String description;

    @Field(SEARCHDOCUMENT_SCORE)
    @Indexed(readonly = true)
    Float score;

    public SearchDocument() {
        super();
    }

    public SearchDocument(SearchDocument s) {
        this(s.id, s.originalTitle, s.description);
    }

    public SearchDocument(String id, String originalTitle, String description) {
        super();
        this.id = id;
        this.originalTitle = originalTitle;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public int nullSafeStringComparator(final String one, final String two) {
        if (one == null ^ two == null) {
            return (one == null) ? -1 : 1;
        }

        if (one == null && two == null) {
            return 0;
        }

        return one.compareToIgnoreCase(two);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    @Override
    public int compareTo(SearchDocument other) {
        int i = nullSafeStringComparator(this.originalTitle,
                other.originalTitle);
        if (i != 0)
            return i;

        i = nullSafeStringComparator(this.description, other.description);
        if (i != 0)
            return i;

        return nullSafeStringComparator(this.id, other.id);
    }

    @Override
    public int compare(SearchDocument o1, SearchDocument o2) {
        return o1.compareTo(o2);
    }


    @Override
    public int hashCode() {
        if (StringUtils.isEmpty(originalTitle)
                || StringUtils.isEmpty(description) || StringUtils.isEmpty(id))
            return 0;
        else {
            final int prime = 31;
            int result = 1;
            result = prime
                    * result
                    + ((originalTitle == null || originalTitle.isEmpty()) ? 0
                    : originalTitle.hashCode());
            result = prime
                    * result
                    + ((description == null || description.isEmpty()) ? 0
                    : description.hashCode());
            result = prime * result
                    + ((id == null || id.isEmpty()) ? 0 : id.hashCode());

            return result;
        }
    }

    public boolean IsNullOrEmpty(String key) {
        return key == null || key.isEmpty();
    }

    public boolean containsTitleAndDescription() {
        return !IsNullOrEmpty(originalTitle) && !IsNullOrEmpty(description);
    }

    public String setFieldValueByName(String name, String value) {
        switch (name) {
            case SEARCHDOCUMENT_ID:
                this.setId(value);
                return value;
            case SEARCHDOCUMENT_ORIGINALTITLE:
                this.setOriginalTitle(value);
                return value;
            case SEARCHDOCUMENT_DESCRIPTION:
                this.setDescription(value);
                return value;
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SearchDocument other = (SearchDocument) obj;

        if (!other.containsTitleAndDescription())
            return true;
        if (other.originalTitle.equalsIgnoreCase(this.originalTitle)
                && other.description.equalsIgnoreCase(this.description)) {
            if (id.replaceAll("((ftp)|(ftps)|(http)|(https)|(pdf))://|(www.)",
                    "")
                    .equals(other.id
                            .replaceAll(
                                    "((ftp)|(ftps)|(http)|(https)|(pdf))://|(www.)",
                                    "")))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "SearchDocument [id=" + id + ", orginalTitle=" + originalTitle
                + ", description=" + description + "]";
    }

    @Override
    public SearchDocument clone() {
        return new SearchDocument(this);
    }
}
