package com.yunchuang.crm.config.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.yunchuang.crm.config.message.*;
import com.yunchuang.crm.config.zip.FileAndPath;
import com.yunchuang.crm.console.user.domain.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;
import javax.validation.constraints.NotNull;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;

/**
 * 工具包
 *
 * @author 尹冬飞
 * @create 2017-12-27 13:20
 */
public class MyUtils {
	/**
	 * 1.创建druid数据源
	 *
	 * @param driverClassName 数据库驱动
	 * @param url             数据库url
	 * @param username        数据库用户名
	 * @param password        数据库用户密码
	 * @return druid数据源
	 */
	public static DruidDataSource getDruidDataSource(String driverClassName, String url, String username, String password) {
		DruidDataSource dataSource = new DruidDataSource();
		//这一项可配可不配，如果不配置druid会根据url自动识别dbType，然后选择相应的driverClassName
		dataSource.setDriverClassName(driverClassName);
		//连接数据库的url
		dataSource.setUrl(url);
		//连接数据库的用户名
		dataSource.setUsername(username);
		//连接数据库的密码
		dataSource.setPassword(password);
		//初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
		dataSource.setInitialSize(1);
		//最小连接池数量
		dataSource.setMinIdle(1);
		//最大连接池数量
		dataSource.setMaxActive(20);
		//获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
		dataSource.setMaxWait(1000);
		return dataSource;
	}

	/**
	 * 2.数据源里引入多组xml的方法
	 * org.mybatis.spring.boot.autoconfigure包下MybatisProperties里面的方法直接拿来用
	 *
	 * @param mapperLocations xml路径数组
	 * @return 资源数组
	 */
	public static Resource[] resolveMapperLocations(String[] mapperLocations) {
		ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
		List<Resource> resources = new ArrayList<>();
		if (mapperLocations != null) {
			for (String mapperLocation : mapperLocations) {
				try {
					Resource[] mappers = resourceResolver.getResources(mapperLocation);
					resources.addAll(Arrays.asList(mappers));
				} catch (IOException e) {
					// ignore
				}
			}
		}
		return resources.toArray(new Resource[resources.size()]);
	}


	//1.文件操作相关

	/**
	 * 保存图片
	 *
	 * @param newFileName 上传照片文件名
	 * @param file        文件
	 * @param fileName    src下的文件名称
	 * @param key         文件里变量名
	 * @return 系统时间
	 */
	public static String saveFile(String newFileName, MultipartFile file, String fileName, String key) throws IOException {
		String saveFilePath = getValueByKey(fileName, key);
		String now = (new SimpleDateFormat("yyyyMM")).format(new Date());
		// 按月份构建文件夹
		saveFilePath += "/" + now;
		/* 构建文件目录 */
		File fileDir = new File(saveFilePath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(saveFilePath + "\\" + newFileName);
		// 写入文件
		out.write(file.getBytes());
		out.flush();
		out.close();
		return now;
	}

	/**
	 * 功能描述：删除图片
	 *
	 * @param picName  图片名称
	 * @param fileName Properties文件名
	 * @param key      Properties文件名中的变量名
	 */
	public static boolean deleteFile(String picName, String fileName, String key) throws IOException {
		// Properties文件名中的变量值
		String picDir = getValueByKey(fileName, key);
		/* 构建文件目录 */
		File fileDir = new File(picDir + "/" + picName);
		if (fileDir.isFile() && fileDir.exists()) {
			// 图片删除 以免太多没用的图片占据空间
			fileDir.delete();
			return true;
		}
		return false;
	}

	/**
	 * 功能描述 保存图片
	 *
	 * @param file           上传照片文件名
	 * @param propertiesName 文件数据
	 * @param fileUrl        src下的文件名称
	 * @param uploaderID     文件里变量名
	 *                       <p>
	 *                       修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public static Map<String, Object> saveFiles(MultipartFile file, String propertiesName, String fileUrl, String uploaderID, HttpSession session) throws IOException {
		Map<String, Object> map = new HashMap<>();
		// 获取图片的文件名
		String fileName = file.getOriginalFilename();
		// 获取图片的扩展名
		String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);

		String webappsPath = getWebappsPath(session);
		// 新的图片文件名 = 获取时间戳+"."图片扩展名,以后会改
		UUID uuid = UUID.randomUUID();
		String newFileName = String.valueOf(System.currentTimeMillis()) + "_" + uuid.toString() + "." + extensionName;
		String fileUrlPath = getValueByKey(propertiesName, fileUrl);
		String path = (new SimpleDateFormat("yyyyMM")).format(new Date()) + "/" + uploaderID + "/";
		String saveFilePath = webappsPath + fileUrlPath + path;

		// 按月份和上传人构建文件夹
		/* 构建文件目录 */
		File fileDir = new File(saveFilePath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(saveFilePath + "\\" + newFileName);
		// 写入文件
		out.write(file.getBytes());
		out.flush();
		out.close();
		map.put("fileUrl", fileUrlPath + path + newFileName);
		map.put("fileName", fileName);

		return map;

	}

	/**
	 * 功能描述：删除文件
	 *
	 * @param fileUrl 文件绝对路径
	 */
	public static void deleteFile(String fileUrl) {
		/* 构建文件目录 */
		File fileDir = new File(fileUrl);
		if (fileDir.isFile() && fileDir.exists()) {
			// 图片删除 以免太多没用的图片占据空间
			boolean delete = fileDir.delete();
		}
	}

	/**
	 * 逆向递归删除空目录
	 *
	 * @param path 文件绝对路径
	 */
	public static void delEmptyPath(String path) {
		File file = new File(path);
		if (file.exists() && file.isDirectory() && !"upload".equals(file.getName())) {
			File[] files = file.listFiles();
			if (files != null && files.length > 0)
				return;
			if (file.delete()) {
				delEmptyPath(file.getParent());
			}
		}
	}

	/**
	 * 删除文件和空文件夹
	 *
	 * @param fileUrl 文件绝对路径
	 */
	public static void delDirectories(String fileUrl) {
		File file = new File(fileUrl);
		deleteFile(fileUrl);
		delEmptyPath(file.getParent());
	}

	/**
	 * 1.下载压缩文件
	 */
	public static void downloadZip(HttpServletResponse response, HttpServletRequest request, List<FileAndPath> fileAndPaths) throws ArchiveException, IOException {
		// 设置并获取下载压缩文件的名字
		String zipFilePath = getZipFilePath(request);
		String zipFileName = getZipFileName(request);
		String zipFile = zipFilePath + zipFileName;
		// 建立压缩文件
		createZip(fileAndPaths, zipFilePath, zipFileName);
		// 下载文件
		downloadFile(response, request, "批量打包下载.zip", zipFile);
		// 删除文件
		deleteFile(zipFile);

	}

	/**
	 * 2.设置并获取下载压缩文件的路径
	 */
	public static String getZipFilePath(HttpServletRequest request) throws IOException {
		// 例如E:/tomcat8/webapps
		String webappsPath = getWebappsPath(request);
		// 例如 upload/temp
		String tempFilePath = getValueByKey("fileUrl.properties", "tempFilePath");
		return webappsPath + tempFilePath;
	}

	/**
	 * 3.设置并获取下载压缩文件的文件名
	 */
	public static String getZipFileName(HttpServletRequest request) {
		return UUID.randomUUID().toString() + ".zip";
	}

	/**
	 * 3.建立压缩文件
	 *
	 * @throws IOException
	 * @throws ArchiveException
	 */
	public static void createZip(List<FileAndPath> fileAndPaths, String zipFilePath, String zipFileName) throws IOException, ArchiveException {
		String zipFile = zipFilePath + zipFileName;
		File fileDir = new File(zipFilePath);
		if (!fileDir.exists()) {
			fileDir.mkdir();
		}
		OutputStream out = new FileOutputStream(zipFile);
		ZipArchiveOutputStream zos = (ZipArchiveOutputStream) new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, out);
		zos.setEncoding("UTF-8");
		zos.setUseZip64(Zip64Mode.AsNeeded);
		for (FileAndPath fileAndPath : fileAndPaths) {
			File file = fileAndPath.getFile();
			if (file.isFile() || file.exists()) {
				ZipArchiveEntry entry = new ZipArchiveEntry(file, fileAndPath.getFilePathInZip());// zipOutput.createArchiveEntry(logFile, logFile.getName());
				zos.putArchiveEntry(entry);
				InputStream in = new FileInputStream(file);
				IOUtils.copy(in, zos);
				zos.closeArchiveEntry();
				in.close();
			}
		}
		zos.finish();
		zos.close();
		out.flush();
		out.close();
	}

	/**
	 * 4.下载普通文件
	 *
	 * @param response response
	 * @param request  request
	 * @param fileName fileName 文件下载时的名字
	 * @param url      url 文件在服务器端的绝对路径
	 * @throws IOException
	 */
	public static void downloadFile(HttpServletResponse response, HttpServletRequest request, String fileName, String url) throws IOException {
		// 根据不同浏览器设置不同编码的fileName
		fileName = setFileDownloadHeaderName(fileName, request);

		// 把文件读入到输入流中
		InputStream is = new BufferedInputStream(new FileInputStream(url));
		byte[] buffer = new byte[is.available()];
		is.read(buffer);
		is.close();

		// 设置文件下载名字,\+fileName+\ 解决有空格名字不完整的bug
		response.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
		// 表示下载的文件是*格式.
		response.setContentType("application/octet-stream;charset=UTF-8");
		// 设置编码
		response.setCharacterEncoding("UTF-8");

		// 将文件输入到客户端
		OutputStream os = response.getOutputStream();
		os.write(buffer);
		os.flush();
		os.close();
	}

	/**
	 * 5.根据不同浏览器设置不同编码的fileName,防止中文乱码
	 *
	 * @param fileName fileName 文件下载时的名字
	 * @param request  request
	 */
	public static String setFileDownloadHeaderName(String fileName, HttpServletRequest request) throws UnsupportedEncodingException {
		// 获取浏览器标识
		String userAgent = request.getHeader("USER-AGENT");
		if (contains(userAgent, "Firefox")) {// Firefox浏览器
			fileName = new String(fileName.getBytes("gb2312"), "iso-8859-1");
		} else {
			fileName = URLEncoder.encode(fileName, "UTF8");// 其他浏览器
		}

		return fileName;
	}

	/**
	 * 功能描述 流程保存文件,后4个参数都是为了拼绝对的保存路径<br>
	 * 流程文件保存的目录是upload/workFlow/ +流程定义名/流程实例ID/任务ID
	 *
	 * @param file    文件
	 * @param path    为了获取绝对路径的最后一段路径
	 * @param session 为了获取绝对路径的第一段
	 * @throws IOException IO异常
	 */
	public static Map<String, Object> saveFile(MultipartFile file, String fileUrlPath, String path, HttpSession session) throws IOException {
		Map<String, Object> map = new HashMap<>();

		// 1.保存的文件路径
		// 文件路径的第一部分:E:/tomcat8/webapps/
		String webappsPath = getWebappsPath(session);
		// 文件路径的第二部分fileUrlPath:upload/yunchuang/workFlow/
		// 文件最后保存的路径,例如:E:/tomcat8/webapps/ upload/workFlow/ ProjectCheck/流程实例ID/任务ID/
		String saveFilePath = webappsPath + fileUrlPath + path;

		// 2.保存的文件名
		// 最后实际保存的文件名
		UUID uuid = UUID.randomUUID();
		// 获取文件的文件名
		String fileName = file.getOriginalFilename();
		// 获取文件的扩展名
		String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
		String newFileName = String.valueOf(System.currentTimeMillis()) + "_" + uuid.toString() + "." + extensionName;

		// 3.写入文件到路径下
		writeFile(file, saveFilePath, newFileName);
		map.put("fileUrl", fileUrlPath + path + newFileName);
		map.put("fileName", fileName);
		return map;
	}

	/**
	 * 写入文件到目录.
	 *
	 * @param file         文件
	 * @param saveFilePath 保存路径
	 * @param newFileName  文件名
	 * @throws IOException 异常
	 */
	public static void writeFile(MultipartFile file, String saveFilePath, String newFileName) throws IOException {
		// 如果没有文件夹,创建文件夹
		File fileDir = new File(saveFilePath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		// 写入文件输出流
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(saveFilePath + "\\" + newFileName);
			// 写入文件
			out.write(file.getBytes());
		} finally {
			out.flush();
			out.close();
		}

	}

	/**
	 * 由输入流获取输出流
	 *
	 * @param response    响应
	 * @param inputStream 输入流
	 */
	public static void getOutputStreamByInputStream(HttpServletResponse response, InputStream inputStream) throws IOException {
		byte[] buffer = new byte[inputStream.available()];
		inputStream.read(buffer);
		inputStream.close();
		// 从response对象获取输出流
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(buffer);
		outputStream.flush();
		outputStream.close();

	}

	/**
	 * 给图片添加水印
	 *
	 * @param iconPath      水印图片路径
	 * @param srcImgPath    源图片路径
	 * @param targetImgPath 目标图片路径
	 */
	public static void markImageByIcon(String iconPath, String srcImgPath, String targetImgPath) throws IOException {
		OutputStream os = null;
		try {
			Image srcImg = ImageIO.read(new File(srcImgPath));
			BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
			// 得到画笔对象
			Graphics2D g = buffImg.createGraphics();
			// 设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);
			// 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
			ImageIcon imgIcon = new ImageIcon(iconPath);
			// 得到Image对象。
			Image img = imgIcon.getImage();
			float alpha = 0.5f; // 透明度
			//float alpha = 0.5f; // 透明度
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			// 表示水印图片的位置,中间的位置
			g.drawImage(img, buffImg.getWidth() / 4, buffImg.getHeight() / 2, null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			g.dispose();
			os = new FileOutputStream(targetImgPath);
			// 生成图片
			ImageIO.write(buffImg, "PNG", os);
			System.out.println("图片完成添加Icon印章。。。。。。");
		} finally {
			if (null != os)
				os.close();

		}
	}

	//2.excel操作相关

	/**
	 * 1.打印excel,并删除临时文件.
	 *
	 * @param targetPath 临时文件
	 */
	public static void printExcel(String targetPath) throws Exception {
		//初始化COM线程
		ComThread.InitSTA();
		//新建Excel对象
		ActiveXComponent xl = new ActiveXComponent("Excel.Application");
		try {
			//System.out.println("version=" + xl.getProperty("Version"));
			//不打开文档,false不显示打开Excel
			Dispatch.put(xl, "Visible", new Variant(false));
			//打开具体的工作簿
			Dispatch workbooks = xl.getProperty("Workbooks").toDispatch();
			//打开文档
			Dispatch excel = Dispatch.call(workbooks, "Open", targetPath).toDispatch();
			//开始打印
			Dispatch.get(excel, "PrintOut");
			//解决文件无法删除bug
			Dispatch.call(excel, "save");
			Dispatch.call(excel, "Close", new Variant(true));
			xl.invoke("Quit", new Variant[]{});
		} finally {
			//始终释放资源
			ComThread.Release();
			//删除临时文件
			deleteFile(targetPath);
		}
	}

	/**
	 * 1.将excel文件导出到客户端
	 *
	 * @param response 响应
	 * @param request  请求
	 * @param wb       excel
	 * @param fileName 文件名
	 * @throws IOException 异常
	 */
	public static void exportExcelToClient(HttpServletResponse response, HttpServletRequest request, HSSFWorkbook wb, String fileName) throws IOException {
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		fileName = setFileDownloadHeaderName(fileName, request);
		response.setHeader("Content-disposition", "attachment;filename=" + fileName);
		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	/**
	 * 2.生成excel的HSSFWorkbook
	 *
	 * @param excelHeader      头文件
	 * @param sheetName        sheet名字
	 * @param excelHeaderWidth 头宽度
	 * @return HSSFWorkbook
	 */
	public static HSSFWorkbook template(String[] excelHeader, String sheetName, int[] excelHeaderWidth) {
		HSSFWorkbook wb = new HSSFWorkbook();
		getSheet(excelHeader, sheetName, excelHeaderWidth, wb);
		return wb;
	}

	/**
	 * 根据MultipartFile获取Sheet
	 *
	 * @param file 文件
	 * @return sheet
	 * @throws Exception 异常
	 */
	@SuppressWarnings("resource")
	public static Sheet getSheetByMultipartFile(MultipartFile file) throws Exception {
		InputStream stream;
		Workbook wb;
		stream = file.getInputStream();
		/* 文件全名 */
		String fileName = file.getOriginalFilename();
		/* 文件扩展名 */
		int last = fileName.lastIndexOf(".");
		String fileType = last == -1 ? "" : fileName.substring(last + 1);
		switch (fileType) {
			case "xls":
				wb = new HSSFWorkbook(stream);
				break;
			case "xlsx":
				wb = new XSSFWorkbook(stream);
				break;
			default:
				throw new Exception("您输入的excel格式不正确");
		}
		return wb.getSheetAt(0);
	}

	/**
	 * 判断是否是空行
	 *
	 * @param row 行
	 * @return 是否是空行
	 */
	public static boolean isBlankRow(HSSFRow row) {
		if (row == null)
			return true;
		boolean result = true;
		for (int i = 0; i <= row.getLastCellNum(); i++) {
			HSSFCell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			String value = "";
			if (cell != null) {
				switch (cell.getCellTypeEnum()) {
					case STRING:
						value = cell.getStringCellValue();
						break;
					case NUMERIC:
						value = String.valueOf((int) cell.getNumericCellValue());
						break;
					case BOOLEAN:
						value = String.valueOf(cell.getBooleanCellValue());
						break;
					case FORMULA:
						value = String.valueOf(cell.getCellFormula());
						break;
					// case Cell.CELL_TYPE_BLANK:
					// break;
					default:
						break;
				}
				if (!value.trim().equals("")) {
					result = false;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * 转化单元格日期格式
	 *
	 * @param cell 单元格
	 * @return 单元格的值
	 * @throws Exception 异常
	 */
	public static String parseExcelCell(Cell cell) throws Exception {
		String value = null;
		if (cell != null) {
			switch (cell.getCellTypeEnum()) {
				case STRING:
					String cellString1 = cell.getStringCellValue();
					Date date1;
					/* 格式化2种格式的字符串日期,防止用户格式不正确,尽量兼容各种格式 */
					if (cellString1.contains("-")) {
						date1 = new SimpleDateFormat("yyyy-MM-dd").parse(cellString1);
					} else if (cellString1.contains("/")) {
						date1 = new SimpleDateFormat("yyyy/MM/dd").parse(cellString1);
					} else {
						date1 = HSSFDateUtil.getJavaDate(Double.valueOf(cellString1));
					}
					/* 存入数据库的是标准的yyyy-MM-dd格式 */
					value = new SimpleDateFormat("yyyy-MM-dd").format(date1);
					break;
				case NUMERIC:
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						Date date = cell.getDateCellValue();
						SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
						value = dformat.format(date);
					} else {
						value = String.valueOf(cell.getNumericCellValue());
					}
					break;
				case BOOLEAN:
					value = String.valueOf(cell.getBooleanCellValue());
					break;
				//case Cell.CELL_TYPE_FORMULA:
				case FORMULA:
					value = String.valueOf(cell.getCellFormula());
					break;
				// case Cell.CELL_TYPE_BLANK:
				// break;
				default:
					break;
			}
		}
		return value;
	}

	/**
	 * 获取sheet
	 *
	 * @param excelHeader      头部
	 * @param sheetName        sheet名字
	 * @param excelHeaderWidth 头部宽度
	 * @param wb               excel文件
	 * @return sheet
	 */
	public static HSSFSheet getSheet(String[] excelHeader, String sheetName, int[] excelHeaderWidth, HSSFWorkbook wb) {
		HSSFSheet sheet = wb.createSheet(sheetName);
		HSSFRow row = sheet.createRow(0);
		HSSFCellStyle style = wb.createCellStyle();
		// 设置字体
		Font headerFont = wb.createFont();
		// headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(headerFont);
		// 设置居中样式
		style.setAlignment(HorizontalAlignment.CENTER);// 水平居中
		style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
		// 背景颜色
		style.setFillForegroundColor(IndexedColors.DARK_TEAL.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		// 设置边框
		style.setBorderBottom(BorderStyle.THIN); // 下边框
		style.setBorderLeft(BorderStyle.THIN);// 左边框
		style.setBorderTop(BorderStyle.THIN);// 上边框
		style.setBorderRight(BorderStyle.THIN);// 右边框

		for (int i = 0; i < excelHeader.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(excelHeader[i]);
			cell.setCellStyle(style);
			// sheet.autoSizeColumn(i);
		}

		// 设置列宽度（像素）3000的话就是11.7
		for (int i = 0; i < excelHeaderWidth.length; i++) {
			sheet.setColumnWidth(i, 32 * excelHeaderWidth[i]);
		}
		return sheet;
	}

	/**
	 * 设置单元格的值
	 *
	 * @param cell 单元格
	 * @param obj  值
	 */
	public static void setExcelCellValue(HSSFCell cell, Object obj) {
		if (cell != null) {
			if (obj instanceof String) {
				cell.setCellValue((String) obj);
			} else if (obj instanceof Integer) {
				cell.setCellType(NUMERIC);
				cell.setCellValue((Integer) obj);
			} else if (obj instanceof Double) {
				cell.setCellType(NUMERIC);
				cell.setCellValue((Double) obj);
			} else if (obj instanceof BigDecimal) {
				cell.setCellType(NUMERIC);
				// Double格式的数字,可能会有精度的问题.
				cell.setCellValue(Double.parseDouble(obj.toString()));
			}
		}
	}


	/**
	 * 3.Properties操作相关
	 */

	/**
	 * 获取properties文件
	 *
	 * @param filePath 文件路径
	 * @return properties文件
	 */
	public static Properties getProperties(String filePath) throws IOException {
		Properties pps = new Properties();
		/* 用类的反射获取路径 */
		InputStream in = MyUtils.class.getClassLoader().getResourceAsStream(filePath);
		pps.load(in);
		in.close();
		return pps;
	}

	/**
	 * 根据配置文件名(含配置文件路径)和配置文件里变量的key获取value
	 *
	 * @param propertiesName 配置文件名(含配置文件路径)
	 * @param key            配置文件里变量的key
	 * @return value
	 */
	public static String getValueByKey(String propertiesName, String key) throws IOException {
		Properties pps = new Properties();
		/* 用类的反射获取路径 */
		InputStream in = MyUtils.class.getClassLoader().getResourceAsStream(propertiesName);
		pps.load(in);
		in.close();
		String value = pps.getProperty(key);
		return value;
	}

	/**
	 * 读取Properties的全部信息
	 *
	 * @param filePath 文件路径
	 */
	@SuppressWarnings("rawtypes")
	public static void getAllProperties(String filePath) throws IOException {
		Properties pps = new Properties();
		/* 用类的反射获取路径 */
		InputStream in = MyUtils.class.getClassLoader().getResourceAsStream(filePath);
		pps.load(in);
		Enumeration en = pps.propertyNames(); // 得到配置文件的名字
		while (en.hasMoreElements()) {
			String strKey = (String) en.nextElement();
			String strValue = pps.getProperty(strKey);
			System.out.println(strKey + "=" + strValue);
		}

	}

	/**
	 * 写入Properties信息
	 *
	 * @param filePath 文件路径
	 * @param pKey     key
	 * @param pValue   value
	 */
	public static void writeProperties(String filePath, String pKey, String pValue) throws IOException {
		Properties pps = new Properties();
		/* 用类的反射获取路径 */
		InputStream in = MyUtils.class.getClassLoader().getResourceAsStream(filePath);
		// 从输入流中读取属性列表（键和元素对）
		pps.load(in);
		// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
		// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
		OutputStream out = new FileOutputStream(filePath);
		pps.setProperty(pKey, pValue);
		// 以适合使用 load 方法加载到 Properties 表中的格式，
		// 将此 Properties 表中的属性列表（键和元素对）写入输出流
		pps.store(out, "Update " + pKey + " name");
	}


	/**
	 * 4.字符串操作相关
	 */

	/**
	 * 1.字符串name是否包含字符串name2
	 */
	public static boolean contains(String name, String name2) {
		int a = name.indexOf(name2);
		return a >= 0;
	}

	/**
	 * 2.获取今天日期的字符串
	 */
	public static String getToday() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 3.获取今天日期时间的字符串
	 */
	public static String getTime() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 4.获取今天日期时间的字符串
	 */
	public static String getMonth() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月");
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 3.获取今天日期时间的字符串
	 */
	public static int getDate(String dateString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(dateString);
		Date now = new Date();
		int days = (int) ((now.getTime() - date.getTime()) / (1000 * 3600 * 24));
		return days;
	}

	/**
	 * @param session
	 * @return
	 */
	public static String getWebappsPath(HttpSession session) {
		// 获取项目所在绝对路径
		String path = session.getServletContext().getRealPath("");
		// 最后一个\的出现位置的索引
		int b = path.lastIndexOf("\\");
		// 倒数第二个\出现位置的索引
		int c = path.substring(0, b).lastIndexOf("\\");
		// 截取字符串开始到倒数第二个\的字符串
		String d = path.substring(0, c + 1);
		// 转义将\替换为/ 存文件时需要这样转
		String e = d.replaceAll("\\\\", "/");
		return e;
	}

	/**
	 * 获取wegapp路径
	 *
	 * @param request 请求
	 * @return webapp路径
	 */
	public static String getWebappsPath(HttpServletRequest request) {
		// 获取项目所在绝对路径
		String path = request.getSession().getServletContext().getRealPath("");
		// 最后一个\的出现位置的索引
		int b = path.lastIndexOf("\\");
		// 倒数第二个\出现位置的索引
		int c = path.substring(0, b).lastIndexOf("\\");
		// 截取字符串开始到倒数第二个\的字符串
		String d = path.substring(0, c + 1);
		// 转义将\替换为/ 存文件时需要这样转
		String e = d.replaceAll("\\\\", "/");
		return e;
	}

	/**
	 * 由类路径下的相对路径获取物理绝对路径
	 *
	 * @param url 类路径下的相对路径
	 * @return
	 */
	public static String getFilePathInClassPath(String url) {
		return ClassUtils.getDefaultClassLoader().getResource("").getPath() + url;
	}

	/**
	 * 将字符串转换成各种类型,包含字符串b为空的情况
	 *
	 * @param a 类的类型比如String.class
	 * @param b 要转换的字符串
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertStringToObject(String a, Class<T> b) {
		T t = null;
		String c = a.trim();
		if (null == c || c.equals("")) {
			return t;
		}
		if (b.equals(BigDecimal.class)) {
			t = (T) new BigDecimal(c);
		} else if (b.equals(Integer.class)) {
			t = (T) Integer.valueOf(c);
		} else if (b.equals(Double.class)) {
			t = (T) Double.valueOf(c);
		} else {
			t = (T) c;
		}
		return t;
	}

	/**
	 * 获取编号
	 *
	 * @param maxCode  最大编号 比如XMHC201600001
	 * @param noFormat 编号格式 比如00001
	 * @param prefix   编号前缀 比如XMHC
	 */
	public static String getNo(String maxCode, String noFormat, String prefix) {
		// 获取系统年份
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);

		int prefixlen = prefix.length();
		int intlen = noFormat.length();
		// 格式化编号为00000
		DecimalFormat df = new DecimalFormat(noFormat);
		String no;
		// 如果表里没有编号,说明还没有一个编号存在,所以最大编号设置为0
		if (maxCode == null || maxCode.equals("")) {
			maxCode = prefix + currentYear + df.format(0);
		}
		// 取最大编号中的年份
		String year = maxCode.substring(prefixlen + 0, prefixlen + 4);
		// 如果系统年份等于最大编号中的年份,编号+1
		if ((currentYear + "").equals(year)) {
			no = prefix + currentYear + df.format((Integer.valueOf(maxCode.substring(prefixlen + 4, prefixlen + 4 + intlen)) + 1));
		} else {
			// 如果系统年份不等于最大编号中的年份,则编号从1开始
			no = prefix + currentYear + df.format(1);
		}
		return no;
	}

	/**
	 * 由spring上下文获取spring托管的bean
	 */
	public static Object getBeanBySpring(String name) {
		WebApplicationContext ac = ContextLoader.getCurrentWebApplicationContext();
		Object obj = ac.getBean(name);
		return obj;
	}

	/**
	 * 把<>转化为&lt和&gt
	 */
	public static String convertStringGtLt(String b) {
		if (b == null) {
			return null;
		}
		String c = b.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		return c;
	}

	/**
	 * 根据openid和name把user存入session
	 */
	public static void setLoginNameToConsole(String openid, String name, HttpSession httpSession) {
		User admin = new User();
		admin.setName(openid);
		admin.setNickname(name);
		httpSession.setAttribute("loginUser", admin);
	}

	/**
	 * 根据openid和name把user存入session
	 */
	public static String getUserNameBySession(HttpSession httpSession) {
		User admin = (User) httpSession.getAttribute("loginUser");
		if (admin != null) {
			return admin.getName();
		}
		return null;
	}

	/**
	 * 根据输入流获取输出流到客户端
	 *
	 * @param response 给客户端响应
	 * @param in       输入流
	 */
	public static void getOutputStreamToClientByInputStream(HttpServletResponse response, InputStream in) throws IOException {
		OutputStream out = response.getOutputStream();
		for (int i; (i = in.read()) != -1; ) {
			out.write(i);
		}
		out.close();
		in.close();

	}

	/**
	 * 将格式为123456789<尹冬飞>的字符串转化为数组,返回数组,分为123456789和尹冬飞
	 *
	 * @param str 格式为123456789<尹冬飞>的字符串
	 * @return 返回数组, 分为2部分123456789和尹冬飞
	 */
	public static String[] getOpenidAndName(String str) {
		String[] split = str.split("<");
		split[1] = split[1].replaceAll(">", "");
		return split;
	}


	/**
	 * 4.云之家发送消息相关
	 */

	/**
	 * HttpPost发送信息
	 *
	 * @param contentMessage 发送的消息
	 * @param httpURL        发送的URL
	 * @return 返回状态码
	 * @throws IOException
	 */
	public static int readContentFromChunkedPost(String contentMessage, String httpURL) throws IOException {
		URL postUrl = new URL(httpURL);
		// 打开连接
		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		// 发送POST请求必须设置如下两行
		connection.setDoOutput(true);
		connection.setDoInput(true);
		// post方法提交
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		// 按照文档上的格式，此处需要设置成application/json
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setChunkedStreamingMode(5);
		connection.connect();
		PrintWriter out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "utf-8"));
		// 乱码过滤器
		out.print(contentMessage);
		out.flush();
		out.close();
		int statusCode = connection.getResponseCode();
		connection.disconnect();
		return statusCode;
	}

	public static String getContestMessage(InitMessage initMessage) {
		// from
		Tfrom tfrom = new Tfrom();
		tfrom.setNo(initMessage.getNo());
		tfrom.setNonce(initMessage.getNonce());
		tfrom.setPub(initMessage.getPub());
		tfrom.setPubtoken(initMessage.getPubtoken());
		tfrom.setTime(initMessage.getTime());

		// to
		Tto tto = new Tto();
		tto.setNo(initMessage.getNo());
		List<String> user = new ArrayList<>();
		user.add(initMessage.getOpenid());
		tto.setUser(user);
		List<Tto> ttos = new ArrayList<>();
		ttos.add(tto);

		// msg2
		Tmsg2 tmsg2 = new Tmsg2();
		tmsg2.setText(initMessage.getType2Text());

		// message
		Tmessage tmessage = new Tmessage();
		tmessage.setFrom(tfrom);
		tmessage.setTo(ttos);
		tmessage.setType(initMessage.getType());
		tmessage.setMsg(tmsg2);
		return JSON.toJSONString(tmessage);
	}


	/**
	 * sha加密算法.
	 * 本方法由金蝶指定的,直接copy过来就能用.
	 *
	 * @param data (工作圈编号,公共号编号,公共号的pubkey,随机数,随机时间)
	 * @return
	 */
	public static String sha(String... data) {
		// 按字母顺序排序数组
		Arrays.sort(data);
		// 把数组连接成字符串（无分隔符），并sha1哈希
		return DigestUtils.sha1Hex(StringUtils.join(data, null));
		//return DigestUtils.shaHex(StringUtils.join(data, null));
	}

	/**
	 * 返回随机数
	 *
	 * @return 随机数
	 */
	public static int nextInt() {
		Random rand = new Random();
		int tmp = Math.abs(rand.nextInt());
		return tmp % (999999 - 100000 + 1) + 100000;
	}

	/**
	 * 查询状态码对应的错误
	 *
	 * @param statusCode 状态码
	 * @return 错误信息
	 */
	public static Map<String, Object> getStatusCode(int statusCode) {
		Map<String, Object> map = new HashMap<>();
		map.put("200", "您的消息发送成功");
		map.put("5000", "一般参数错误,如：from/to不完整、必须传入参数xxx");
		map.put("5001", "公共号不存在或未审核");
		map.put("5002", "数据长度超限错误，如：传入数据长度超过了1M");
		map.put("5003", "发送的公司或用户错误，如:发送到其他企业,无发送用户或错误的openid");
		map.put("5004", "公共号密钥验证失败，from.pubtoken=sha(from.no,from.pub,公共号.pubkey,from.nonce,from.time)");
		map.put("5005", "发往公共号消息过多,请等x分钟");
		Map<String, Object> newMap = new HashMap<>();
		for (String a : map.keySet()) {
			if (statusCode == Integer.valueOf(a)) {
				newMap.put("statusCode", statusCode);
				newMap.put("status", map.get(a));
			}
		}
		return newMap;
	}

	//5.加密相关开始

	/**
	 * md5加密
	 *
	 * @param inputText 字符串
	 * @return 加密后的字符串
	 */
	public static String md5(String inputText) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		return encrypt(inputText, "md5");
	}

	/**
	 * md5或者sha-1加密
	 *
	 * @param inputText     要加密的内容
	 * @param algorithmName 加密算法名称：md5或者sha-1，不区分大小写
	 * @return 加密后的字符串
	 */
	private static String encrypt(String inputText, String algorithmName) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (inputText == null || "".equals(inputText.trim())) {
			throw new IllegalArgumentException("请输入要加密的内容");
		}
		if (algorithmName == null || "".equals(algorithmName.trim())) {
			algorithmName = "md5";
		}
		MessageDigest m = MessageDigest.getInstance(algorithmName);
		m.update(inputText.getBytes("UTF8"));
		byte s[] = m.digest();
		// m.digest(inputText.getBytes("UTF8"));
		return hex(s);

	}

	/**
	 * 返回十六进制字符串
	 *
	 * @param arr 字符
	 * @return 十六进制字符串
	 */
	@NotNull
	private static String hex(byte[] arr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; ++i) {
			sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}


	/**
	 * 获取resouce下面的文件的内容 比如"classpath:\\static\\about.json"
	 *
	 * @param fileName 文件名称 about.json
	 * @return 文件内容
	 */
	private static String getJsonContentInStatic(String fileName) {
		String url = "classpath:\\static\\" + fileName;
		return getFileContent(url);
	}

	/**
	 * 获取resouce下面的文件的内容
	 *
	 * @param url 比如"classpath:\\static\\about.json"
	 * @return 文件内容
	 */
	private static String getFileContent(String url) {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource(url);
		InputStream inputStream = null;
		StringBuilder stringBuilder = null;
		BufferedReader bufferedReader = null;
		try {
			inputStream = resource.getInputStream();
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			stringBuilder = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return stringBuilder.toString();
	}



}

