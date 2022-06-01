package com.jeemodel.solution.file.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jeemodel.bean.http.PongData;
import com.jeemodel.bean.http.PongUtils;
import com.jeemodel.core.utils.ServletUtils;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.solution.file.config.FileConfigHelper;
import com.jeemodel.solution.file.constant.FileConstants;
import com.jeemodel.solution.file.dto.UploadFile;
import com.jeemodel.solution.file.utils.FileUploadUtils;
import com.jeemodel.solution.file.utils.FileUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 通用请求处理
 * 
 * @author Rootfive
 */
@Slf4j
@RestController
public class CommonController {

	/**
	 * 通用下载请求
	 * 
	 * @param fileName 文件名称
	 * @param delete   是否删除
	 */
	@GetMapping("common/download")
	public void fileDownload(String fileName, Boolean delete, HttpServletResponse response,
			HttpServletRequest request) {
		try {
			if (!FileUtils.checkAllowDownload(fileName)) {
				throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
			}
			String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
			String filePath = FileConfigHelper.getDownloadPath() + fileName;

			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			FileUtils.setAttachmentResponseHeader(response, realFileName);
			FileUtils.writeBytes(filePath, response.getOutputStream());
			if (delete) {
				FileUtils.deleteFile(filePath);
			}
		} catch (Exception e) {
			log.error("下载文件失败", e);
		}
	}

	/**
	 * 通用上传请求(暂时没有用到) 
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	@PostMapping("/common/upload")
	public PongData<UploadFile> uploadFile(MultipartFile file) throws Exception {
		try {
			// 上传文件路径
			String filePath = FileConfigHelper.getUploadPath();
			// 上传并返回新文件相对地址
			String fileName = FileUploadUtils.upload(filePath, file);
			String url = ServletUtils.getDomainUrl() + fileName;
			
			UploadFile uploadFile = new UploadFile(fileName, url);
			return PongUtils.okData(uploadFile);
		} catch (Exception e) {
			return PongUtils.unknown(e.getMessage());
		}
	}

	/**
	 * 本地资源通用下载
	 */
	@GetMapping("/common/download/resource")
	public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			if (!FileUtils.checkAllowDownload(resource)) {
				throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
			}
			// 本地资源路径
			String localPath = FileConfigHelper.getDefaultBaseDir();
			// 数据库资源地址
			String downloadPath = localPath + StringUtils.substringAfter(resource, FileConstants.RESOURCE_PREFIX);
			// 下载名称
			String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			FileUtils.setAttachmentResponseHeader(response, downloadName);
			FileUtils.writeBytes(downloadPath, response.getOutputStream());
		} catch (Exception e) {
			log.error("下载文件失败", e);
		}
	}
}
