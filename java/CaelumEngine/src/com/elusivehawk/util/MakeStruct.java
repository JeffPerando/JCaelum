
package com.elusivehawk.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Reminder annotation, for when Java comes out with structs.
 * 
 * @author Elusivehawk
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
public @interface MakeStruct{}