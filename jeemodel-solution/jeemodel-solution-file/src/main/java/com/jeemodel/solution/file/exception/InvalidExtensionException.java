package com.jeemodel.solution.file.exception;

import java.util.Arrays;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.lang.NonNull;

import com.jeemodel.bean.enums.code.sub.IBaseSubCode;
import com.jeemodel.bean.enums.code.sub.ISubCodeServiceLogic;
import com.jeemodel.bean.exception.base.IToBeJeeModelException;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件上传 误异常类
 * 
 * @author Rootfive
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class InvalidExtensionException extends FileUploadException implements IToBeJeeModelException{
	private static final long serialVersionUID = 1L;

	
	private IBaseSubCode baseSubCode;
	
	private String[] allowedExtension;
	private String extension;
	private String filename;

	/**
	 * @param subCode			异常Code
	 * @param allowedExtension	允许上传类型
	 * @param extension			实际上传类型
	 * @param filename			上传文件
	 */
	public InvalidExtensionException(@NonNull ISubCodeServiceLogic subCode,String[] allowedExtension, String extension, String filename) {
		super("上传文件名: [" + filename + "], 类型 : [" + extension + "], 允许上传类型 : ["+ Arrays.toString(allowedExtension) + "]");
				
		this.allowedExtension = allowedExtension;
		this.extension = extension;
		this.filename = filename;
		this.baseSubCode = subCode;
	}

	public String[] getAllowedExtension() {
		return allowedExtension;
	}

	public String getExtension() {
		return extension;
	}

	public String getFilename() {
		return filename;
	}

	public static class InvalidImageExtensionException extends InvalidExtensionException {
		private static final long serialVersionUID = 1L;

		public InvalidImageExtensionException(@NonNull ISubCodeServiceLogic subCode,String[] allowedExtension, String extension, String filename) {
			super(subCode,allowedExtension, extension, filename);
		}
	}

	public static class InvalidFlashExtensionException extends InvalidExtensionException {
		private static final long serialVersionUID = 1L;

		public InvalidFlashExtensionException(@NonNull ISubCodeServiceLogic subCode,String[] allowedExtension, String extension, String filename) {
			super(subCode,allowedExtension, extension, filename);
		}
	}

	public static class InvalidMediaExtensionException extends InvalidExtensionException {
		private static final long serialVersionUID = 1L;

		public InvalidMediaExtensionException(@NonNull ISubCodeServiceLogic subCode,String[] allowedExtension, String extension, String filename) {
			super(subCode,allowedExtension, extension, filename);
		}
	}

	public static class InvalidVideoExtensionException extends InvalidExtensionException {
		private static final long serialVersionUID = 1L;

		public InvalidVideoExtensionException(@NonNull ISubCodeServiceLogic subCode,String[] allowedExtension, String extension, String filename) {
			super(subCode,allowedExtension, extension, filename);
		}
	}
}
