package com.yi.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yi.util.MediaUtils;
import com.yi.util.UploadFileUtils;

@Controller
public class UploadController {
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	private static String innerUploadPath = "resources/upload";

	@Resource(name = "uploadPath")
	private String uploadPath;

	@RequestMapping(value = "inner", method = RequestMethod.GET)
	public String innerUploadForm() {
		logger.info("inner - Get");

		return "innerUploadForm";
	}

	@RequestMapping(value = "inner", method = RequestMethod.POST)
	public String innerUploadResult(String writer, MultipartFile file, HttpServletRequest request, Model model)
			throws IOException {
		logger.info("inner - Post");
		logger.info("writer = " + writer);
		logger.info("file name = " + file.getOriginalFilename());
		logger.info("file size = " + file.getSize());

		String root_path = request.getSession().getServletContext().getRealPath("/");
		File dirPath = new File(root_path + "/" + innerUploadPath);

		if (dirPath.exists() == false) {
			dirPath.mkdirs();
		}

		UUID uuid = UUID.randomUUID();
		String savedName = uuid.toString() + "_" + file.getOriginalFilename();
		File target = new File(root_path + "/" + innerUploadPath + "/" + savedName);
		FileCopyUtils.copy(file.getBytes(), target);

		model.addAttribute("writer", writer);
		model.addAttribute("file", savedName);

		return "innerUploadResult";
	}

	@RequestMapping(value = "innerMulti", method = RequestMethod.GET)
	public String innerMultiUploadForm() {
		logger.info("innerMulti - Get");

		return "innerMultiUploadForm";
	}

	@RequestMapping(value = "innerMulti", method = RequestMethod.POST)
	public String innerMultiUploadResult(String writer, List<MultipartFile> files, HttpServletRequest request,
			Model model) throws IOException {
		logger.info("inner - Post");
		logger.info("writer = " + writer);

		for (int i = 0; i < files.size(); i++) {
			MultipartFile file = files.get(i);
			logger.info("file name = " + file.getOriginalFilename());
			logger.info("file size = " + file.getSize());
		}

		String root_path = request.getSession().getServletContext().getRealPath("/");
		File dirPath = new File(root_path + "/" + innerUploadPath);

		if (dirPath.exists() == false) {
			dirPath.mkdirs();
		}

		ArrayList<String> list = new ArrayList<>();

		for (MultipartFile file : files) {
			UUID uuid = UUID.randomUUID();
			String savedName = uuid.toString() + "_" + file.getOriginalFilename();
			File target = new File(root_path + "/" + innerUploadPath + "/" + savedName);
			FileCopyUtils.copy(file.getBytes(), target);
			list.add(savedName);
		}

		model.addAttribute("writer", writer);
		model.addAttribute("list", list);

		return "innerMultiUploadResult";
	}

	@RequestMapping(value = "out", method = RequestMethod.GET)
	public String outUploadForm() {
		logger.info("out - Get");

		return "outUploadForm";
	}

	@RequestMapping(value = "out", method = RequestMethod.POST)
	public String outUploadResult(String writer, MultipartFile file, Model model) throws IOException {
		logger.info("out - Post");
		logger.info("writer = " + writer);
		logger.info("file name = " + file.getOriginalFilename());
		logger.info("file size = " + file.getSize());

		File dirPath = new File(uploadPath);

		if (dirPath.exists() == false) {
			dirPath.mkdirs();
		}

		UUID uuid = UUID.randomUUID();
		String savedName = uuid.toString() + "_" + file.getOriginalFilename();
		File target = new File(uploadPath + "/" + savedName);
		FileCopyUtils.copy(file.getBytes(), target);

		model.addAttribute("writer", writer);
		model.addAttribute("file", savedName);

		return "outUploadResult";
	}

	@ResponseBody
	@RequestMapping("/displayFile")
	public ResponseEntity<byte[]> displayFile(String filename) {
		ResponseEntity<byte[]> entity = null;

		logger.info("displayFile : " + filename);

		try {
			String format = filename.substring(filename.lastIndexOf(".") + 1);
			MediaType mType = MediaUtils.getMediaType(format);
			HttpHeaders headers = new HttpHeaders();
			InputStream in = null;

			in = new FileInputStream(uploadPath + "/" + filename);
			headers.setContentType(mType);
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);

			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@RequestMapping(value = "drag", method = RequestMethod.GET)
	public String dragForm() {
		logger.info("drag - Get");

		return "dragForm";
	}

	@RequestMapping(value = "drag", method = RequestMethod.POST)
	public ResponseEntity<List<String>> dragPost(String writer, List<MultipartFile> files) {
		logger.info("drag - Post");

		ResponseEntity<List<String>> entity = null;

		try {
			logger.info("writer = " + writer);

			for (MultipartFile file : files) {
				logger.info("file name = " + file.getOriginalFilename());
				logger.info("file size = " + file.getSize());
			}

			File dirPath = new File(uploadPath);

			if (dirPath.exists() == false) {
				dirPath.mkdirs();
			}

			List<String> list = new ArrayList<>();

			for (MultipartFile file : files) {
				String savedName = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
				
				list.add(savedName);
			}

			entity = new ResponseEntity<List<String>>(list, HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

}
