package com.chanjetpay.garlic.common;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleUtils {

	private final static String SITE_PROP_FILE_NAME = "website";

	public final static Object initLock = new Object();
	private static ResourceBundle bundle = null;

	static {
		synchronized (initLock) {
			if (bundle == null)
				bundle = ResourceBundle.getBundle(SITE_PROP_FILE_NAME, Locale.CHINA);
		}
	}

	public static String getDomain() {
		return bundle.getString("site.domain");
	}
}
