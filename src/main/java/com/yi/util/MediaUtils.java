package com.yi.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

public class MediaUtils {
	private static Map<String, MediaType> mediaMap;

	static {
		mediaMap = new HashMap<>();
		mediaMap.put("jpg", MediaType.IMAGE_JPEG);
		mediaMap.put("png", MediaType.IMAGE_PNG);
		mediaMap.put("gif", MediaType.IMAGE_GIF);
	}

	public static MediaType getMediaType(String type) {
		return mediaMap.get(type.toLowerCase());
	}

}
