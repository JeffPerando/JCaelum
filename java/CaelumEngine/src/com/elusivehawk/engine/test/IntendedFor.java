
package com.elusivehawk.engine.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.elusivehawk.engine.EnumEngineFeature;
import com.elusivehawk.util.Internal;

/**
 * 
 * DO NOT MOVE THIS! I mean it, it's suppose to be HERE!
 * <p>
 * Anyway: Internal annotation to denote which engine feature the class in question is intended for.
 * 
 * @author Elusivehawk
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
@Internal
public @interface IntendedFor
{
	public EnumEngineFeature[] value();
	
}
