
package com.elusivehawk.util;

import java.io.File;
import java.util.Arrays;
import com.google.common.collect.ImmutableList;

/**
 * 
 * Static API for basic specifications regarding the current computer.
 * 
 * @author Elusivehawk
 */
public final class CompInfo
{
	private CompInfo(){}
	
	public static final String
			JAVA_VERSION = System.getProperty("java.vm.specification.version"),
			TIME_ZONE = System.getProperty("user.timezone"),
			USERNAME = System.getProperty("user.name"),
			VENDOR = System.getProperty("java.vendor");
	public static final ImmutableList<String> PATH = ImmutableList.copyOf(Arrays.asList(System.getProperty("java.library.path").split(System.getProperty("path.separator"))));
	public static final EnumOS OS = EnumOS.getCurrentOS();
	public static final int CORES = Runtime.getRuntime().availableProcessors();
	public static final EnumVideoCard VIDEO_CARD = EnumVideoCard.getCurrentVC();
	public static final File
			USER_DIR = new File(System.getProperty("user.home")),
			TMP_DIR = new File(System.getProperty("java.io.tmpdir"));
	
}