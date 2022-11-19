package uet.ppvan.mangareader.config;

import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 * Config for search engine.
 */
@Configuration
public class MyLuceneAnalysisConfigurer implements LuceneAnalysisConfigurer {
    @Override
    public void configure(LuceneAnalysisConfigurationContext context) {
        context.analyzer("custom").custom()
            .tokenizer("standard")
            .tokenFilter("lowercase")
            .tokenFilter("asciiFolding");
    }
}
