package com.yunchuang.crm.config.zip;

import java.io.File;

/**
 * 批量下载文件的每个文件的实体
 * 
 * @author 尹冬飞
 * @date 2016年7月5日 下午2:23:44
 */
public class FileAndPath {
	/**
	 * 1.文件
	 */
	private File file;
	/**
	 * 2.文件在zip中的相对路径
	 */
	private String filePathInZip;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFilePathInZip() {
		return filePathInZip;
	}

	public void setFilePathInZip(String filePathInZip) {
		this.filePathInZip = filePathInZip;
	}

}
