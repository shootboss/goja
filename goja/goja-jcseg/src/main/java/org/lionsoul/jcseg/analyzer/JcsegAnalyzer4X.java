/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package org.lionsoul.jcseg.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.lionsoul.jcseg.core.ADictionary;
import org.lionsoul.jcseg.core.DictionaryFactory;
import org.lionsoul.jcseg.core.JcsegTaskConfig;

import java.io.Reader;


/**
 * jcseg analyzer for lucene.
 *
 * @author chenxin<chenxin619315@gmail.com>
 */
public class JcsegAnalyzer4X extends Analyzer {

    private final int mode;
    private JcsegTaskConfig config = null;
    private ADictionary     dic    = null;

    public JcsegAnalyzer4X(int mode) {
        this.mode = mode;

        //initialize the task config and the dictionary
        config = new JcsegTaskConfig();
        dic = DictionaryFactory.createDefaultDictionary(config);
    }

    public void setConfig(JcsegTaskConfig config) {
        this.config = config;
    }

    public void setDict(ADictionary dic) {
        this.dic = dic;
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
            return new TokenStreamComponents(source, new JcsegFilter(source));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
