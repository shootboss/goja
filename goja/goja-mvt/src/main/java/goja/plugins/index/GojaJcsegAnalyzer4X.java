/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.plugins.index;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.lionsoul.jcseg.analyzer.JcsegTokenizer;
import org.lionsoul.jcseg.core.ADictionary;
import org.lionsoul.jcseg.core.DictionaryFactory;
import org.lionsoul.jcseg.core.JcsegTaskConfig;

import java.io.Reader;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-08-24 20:59
 * @since JDK 1.6
 */
public class GojaJcsegAnalyzer4X extends Analyzer {

    private final int mode;

    private final JcsegTaskConfig config;
    private final ADictionary     dic;

    public GojaJcsegAnalyzer4X(int mode) {
        this.mode = mode;

        //initialize the task config and the dictionary
        config = new JcsegTaskConfig();
        dic = DictionaryFactory.createDefaultDictionary(config);
    }

    public GojaJcsegAnalyzer4X(int mode, JcsegTaskConfig config) {
        this.mode = mode;
        this.config = config;
        this.dic = DictionaryFactory.createDefaultDictionary(config);
    }

    public JcsegTaskConfig getTaskConfig() {
        return config;
    }

    public ADictionary getDict() {
        return dic;
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
        try {
            Tokenizer source = new JcsegTokenizer(reader, mode, config, dic);
            return new TokenStreamComponents(source, new GoJcsegFilter(source));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
