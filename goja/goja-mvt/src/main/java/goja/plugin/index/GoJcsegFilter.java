/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package goja.plugin.index;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

import java.io.IOException;

/**
 * <p>
 * .
 * </p>
 *
 * @author sagyf yang
 * @version 1.0 2014-08-24 21:01
 * @since JDK 1.6
 */
public class GoJcsegFilter extends TokenFilter {

    //private CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    protected GoJcsegFilter(TokenStream input) {
        super(input);
    }

    @Override
    public boolean incrementToken() throws IOException {
        while (input.incrementToken()) {
            //char text[] = termAtt.buffer();
            //int termLength = termAtt.length();

            return true;
        }
        return false;
    }


}
