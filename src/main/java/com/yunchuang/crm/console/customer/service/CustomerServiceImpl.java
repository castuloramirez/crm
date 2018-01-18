package com.yunchuang.crm.console.customer.service;

import com.yunchuang.crm.config.utils.MyUtils;
import com.yunchuang.crm.console.customer.dao.ICustomerDao;
import com.yunchuang.crm.console.customer.model.Customer;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 尹冬飞 on 2018/1/17 9:05
 */
@Transactional(value = "masterTransactionManager", isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
public class CustomerServiceImpl implements ICustomerService {
	/* 注入dao */
	@Resource
	private ICustomerDao customerDao;
	@Resource
	private SqlSessionFactory masterSqlSessionFactory;

	/**
	 * 1.下载模版
	 *
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@Override
	public void Template(HttpServletResponse response, HttpServletRequest request) throws IOException {
		/* 常量 */
		String[] excelHeader = {"客户编号", "客户名称", "地区", "省份", "城市", "区县", "通讯地址", "行业分类", "业务类别", "法人", "企业联系电话", "经度", "纬度"};
		String sheetName = "客户表";
		int[] excelHeaderWidth = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
		String filename = "客户表导入模版" + MyUtils.getToday() + ".xls";
		HSSFWorkbook wb = MyUtils.template(excelHeader, sheetName, excelHeaderWidth);
		MyUtils.exportExcelToClient(response, request, wb, filename);
	}

	/**
	 * 2.导入
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@Override
	public int importByExcel(MultipartFile file) throws Exception {
		Sheet sheet1 = MyUtils.getSheetByMultipartFile(file);
		List<Customer> customers = getList(sheet1);
		customerDao.truncateAll();
		addBatch(customers);
		return customers.size();
	}

	/**
	 * 3.导出
	 *
	 * @param list
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@Override
	public void exportToExcel(List<Customer> list, HttpServletResponse response, HttpServletRequest request) throws IOException {
		/* 常量 */
		String[] excelHeader = {"客户编号", "客户名称", "地区", "省份", "城市", "区县", "通讯地址", "行业分类", "业务类别", "法人", "企业联系电话", "经度", "纬度"};
		String sheetName = "客户表";
		int[] excelHeaderWidth = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
		String filename = "客户表导出" + MyUtils.getToday() + ".xls";
		HSSFWorkbook wb = ExportMethod(list, excelHeader, sheetName, excelHeaderWidth);
		MyUtils.exportExcelToClient(response, request, wb, filename);
	}

	/**
	 * 4.由List<Customer>获取HSSFWorkbook
	 */
	private HSSFWorkbook ExportMethod(List<Customer> list, String[] excelHeader, String sheetName, int[] excelHeaderWidth) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFRow row;
		HSSFSheet sheet = MyUtils.getSheet(excelHeader, sheetName, excelHeaderWidth, wb);
		// 设置单元格的值
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(i + 1);
			Customer customer = list.get(i);
			for (int j = 0; j < excelHeader.length; j++) {
				HSSFCell cell = row.createCell(j);
				switch (j) {
					case 0:
						/* 0.客户编号 */
						MyUtils.setExcelCellValue(cell, customer.getCustomerNo());
						break;
					case 1:
						/* 1.客户名称 */
						MyUtils.setExcelCellValue(cell, customer.getCustomerName());
						break;
					case 2:
						/* 2.地区 */
						MyUtils.setExcelCellValue(cell, customer.getArea());
						break;
					case 3:
						/* 3.省份 */
						MyUtils.setExcelCellValue(cell, customer.getProvince());
						break;
					case 4:
						/* 4.城市 */
						MyUtils.setExcelCellValue(cell, customer.getCity());
						break;
					case 5:
						/* 5.区县 */
						MyUtils.setExcelCellValue(cell, customer.getCounty());
						break;
					case 6:
						/* 6.通讯地址 */
						MyUtils.setExcelCellValue(cell, customer.getPostalAddress());
						break;
					case 7:
						/* 7.行业分类 */
						MyUtils.setExcelCellValue(cell, customer.getIndustryClassification());
						break;
					case 8:
						/* 8.业务类别 */
						MyUtils.setExcelCellValue(cell, customer.getBusinessCategory());
						break;
					case 9:
						/* 9.法人 */
						MyUtils.setExcelCellValue(cell, customer.getLegalPerson());
						break;
					case 10:
						/* 10.企业联系电话 */
						MyUtils.setExcelCellValue(cell, customer.getEnterpriseContactPhone());
						break;
					case 11:
						/* 11.经度 */
						MyUtils.setExcelCellValue(cell, customer.getLongitude());
						break;
					case 12:
						/* 12.纬度 */
						MyUtils.setExcelCellValue(cell, customer.getLatitude());
						break;
					default:
						break;
				}
			}
		}
		return wb;
	}

	/**
	 * 5.由sheet获取List<Customer>
	 *
	 * @throws Exception
	 */
	private List<Customer> getList(Sheet sheet1) throws Exception {
		List<Customer> customers = new ArrayList<>();
		for (int i = 1; i <= sheet1.getLastRowNum(); i++) {
			try {
				HSSFRow row = (HSSFRow) sheet1.getRow(i);
				HSSFCell cell;
				if (row == null || MyUtils.isBlankRow(row)) {
					continue;
				}
				Customer customer = new Customer();
				for (int j = 0; j <= row.getLastCellNum(); j++) {
					cell = row.getCell(j);
					if (cell == null)
						continue;
					switch (j) {
						case 0:
							/* 0.客户编号 */
							cell.setCellType(CellType.STRING);
							customer.setCustomerNo(cell.getStringCellValue());
							break;
						case 1:
							/* 1.客户名称 */
							cell.setCellType(CellType.STRING);
							customer.setCustomerName(cell.getStringCellValue());
							break;
						case 2:
							/* 2.地区 */
							cell.setCellType(CellType.STRING);
							customer.setArea(cell.getStringCellValue());
							break;
						case 3:
							/* 3.省份 */
							cell.setCellType(CellType.STRING);
							customer.setProvince(cell.getStringCellValue());
							break;
						case 4:
							/* 4.城市 */
							cell.setCellType(CellType.STRING);
							customer.setCity(cell.getStringCellValue());
							break;
						case 5:
							/* 5.区县 */
							cell.setCellType(CellType.STRING);
							customer.setCounty(cell.getStringCellValue());
							break;
						case 6:
							/* 6.通讯地址 */
							cell.setCellType(CellType.STRING);
							customer.setPostalAddress(cell.getStringCellValue());
							break;
						case 7:
							/* 7.行业分类 */
							cell.setCellType(CellType.STRING);
							customer.setIndustryClassification(cell.getStringCellValue());
							break;
						case 8:
							/* 8.业务类别 */
							cell.setCellType(CellType.STRING);
							customer.setBusinessCategory(cell.getStringCellValue());
							break;
						case 9:
							/* 9.法人 */
							cell.setCellType(CellType.STRING);
							customer.setLegalPerson(cell.getStringCellValue());
							break;
						case 10:
							/* 10.企业联系电话 */
							cell.setCellType(CellType.STRING);
							customer.setEnterpriseContactPhone(cell.getStringCellValue());
							break;
						case 11:
							/* 11.经度 */
							cell.setCellType(CellType.STRING);
							customer.setLongitude(MyUtils.convertStringToObject(cell.getStringCellValue(), BigDecimal.class));
							break;
						case 12:
							/* 12.纬度 */
							cell.setCellType(CellType.STRING);
							customer.setLatitude(MyUtils.convertStringToObject(cell.getStringCellValue(), BigDecimal.class));
							break;
						default:
							break;
					}
				}
				customers.add(customer);
			} catch (Exception e) {
				String message = "第" + (i + 1) + "行有错误:" + e.getMessage();
				throw new Exception(message);
			}
		}
		return customers;
	}

	/**
	 * 4.增加
	 *
	 * @param customer
	 */
	@Override
	public void add(Customer customer) {
		customerDao.add(customer);
	}

	/**
	 * 5.更改
	 *
	 * @param customer
	 */
	@Override
	public void update(Customer customer) {
		customerDao.update(customer);
	}

	/**
	 * 6.删除
	 *
	 * @param ids
	 */
	@Override
	public void delete(Integer[] ids) {
		customerDao.delete(ids);
	}

	/**
	 * 7.前台读取全部
	 */
	@Override
	public List<Customer> list() {
		return customerDao.list();
	}

	/**
	 * 8.根据ID读取
	 *
	 * @param id
	 */
	@Override
	public Customer loadById(int id) {
		return customerDao.loadById(id);
	}

	/**
	 * 10.查询所有数量
	 */
	@Override
	public int count() {
		return customerDao.count();
	}

	/**
	 * 11.分页读取
	 *
	 * @param iDisplayStart
	 * @param iDisplayLength
	 */
	@Override
	public List<Customer> findByConsole(int iDisplayStart, int iDisplayLength) {
		/* 前台选择ALL的时候 */
		if (iDisplayLength == -1) {
			return customerDao.list();
		}
		return customerDao.findByConsole((iDisplayStart + 1), (iDisplayLength + iDisplayStart));
	}

	/**
	 * 12.批量插入
	 *
	 * @param list
	 */
	@Override
	public void addBatch(List<Customer> list) {
		SqlSession batchSqlSession = null;
		try {
			batchSqlSession = masterSqlSessionFactory.openSession(ExecutorType.BATCH, false);
			// 每批commit的个数
			int batchCount = 200;
			for (int index = 0; index < list.size(); index++) {
				//就这里需要更改
				customerDao.add(list.get(index));
				if (index != 0 && index % batchCount == 0) {
					batchSqlSession.commit();
				}
			}
			batchSqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (batchSqlSession != null) {
				batchSqlSession.close();
			}
		}
	}

	/**
	 * 13.清除表,不支持回滚
	 */
	@Override
	public void truncateAll() {
		customerDao.truncateAll();
	}
}
