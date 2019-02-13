package com.yi.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {
	public static String uploadFile(String uploadPath, // 업로드 경로
			String originalName, // 파일 이름
			byte[] fileData) throws IOException { // 파일 데이타
		UUID uuid = UUID.randomUUID();
		String savedName = uuid.toString() + "_" + originalName;
		String savedPath = calcPath(uploadPath);
		File target = new File(uploadPath + savedPath + "/" + savedName); // new File(uploadPath + savedPath, savedName)

		FileCopyUtils.copy(fileData, target);

		return savedPath + "/" + savedName;
	}

	private static String calcPath(String uploadPath) {
		Calendar cal = Calendar.getInstance();
		String yearPath = "/" + cal.get(Calendar.YEAR);
		String monthPath = String.format("%s/%02d", yearPath, cal.get(Calendar.MONTH) + 1);
		String datePath = String.format("%s/%02d", monthPath, cal.get(Calendar.DATE));

		makeDir(uploadPath, yearPath, monthPath, datePath);

		return datePath;
	}

	private static void makeDir(String uploadPath, String... paths) {
		for (String path : paths) {
			File dirPath = new File(uploadPath + path);

			if (dirPath.exists() == false) {
				dirPath.mkdirs();
			}
		}
	}

}
