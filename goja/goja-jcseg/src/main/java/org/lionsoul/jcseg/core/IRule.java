/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013-2014 sagyf Yang. The Four Group.
 */

package org.lionsoul.jcseg.core;

/**
 * filter rule interface.
 * 		the most important concept for mmseg chinese
 * 	segment algorithm.
 * 
 * @author	chenxin<chenxin619315@gmail.com>
 */
public interface IRule {
	/**
	 * do the filter work
	 * 
	 * @param chunks
	 * @return IChunk[]
	 */
	public IChunk[] call(IChunk[] chunks);
}
