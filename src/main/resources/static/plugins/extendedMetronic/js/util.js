/**
 * Created by ydf on 2017/2/23.
 */

/**
 * 初始化datatables
 * @param table
 * @param option
 */
function initTable1(table, option) {
	// begin first table
	table.dataTable(option);
	//var tableWrapper = jQuery('#sample_1_wrapper');
	table.find('.group-checkable').change(function () {
		var set = jQuery(this).attr("data-set");
		var checked = jQuery(this).is(":checked");
		jQuery(set).each(function () {
			if (checked) {
				$(this).prop("checked", true);
				$(this).parents('tr').addClass("active");
			} else {
				$(this).prop("checked", false);
				$(this).parents('tr').removeClass("active");
			}
		});
	});
	table.on('change', 'tbody tr .checkboxes', function () {
		$(this).parents('tr').toggleClass("active");
	});
}
/**
 * 刷新表格
 * @param table
 */
function refreshTable(table) {
	table.dataTable().fnReloadAjax();
}
/**
 * 获取项目名
 * 格式:/yunchuang
 * @returns string
 */
function getWebName() {
	var pathName = window.location.pathname.substring(1);
	return pathName == '' ? '' : '/' + pathName.substring(0, pathName.indexOf('/'));
}

function getTableChineseJson() {
	return getWebName() + "/plugins/extendedMetronic/json/table-Chinese.json";
}

/**
 * 获取项目的url
 * 格式:http://localhost:8008/yunchuang
 * @returns {string}
 */
function getWebUrl() {
	var webName = getWebName();
	var url = window.location.protocol + '//' + window.location.host;
	return webName == "" ? url : url + '/' + webName;
}

function getUrl() {
	return window.location.protocol + '//' + window.location.host;
}

/**
 * 根据url获取参数
 * @param name
 * @returns {null}
 * @constructor
 */
function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

/**
 * 截取字符串,超过长度的部分显示...
 * @param str
 * @returns {*}
 */
function getLength(str) {
	if (str.length > 5) {
		return str.substr(0, 4) + '...';
	}
	return str;
}

/**
 * 获取操作系统平台，iOS或Android
 */
function getOS() {
	return (navigator.userAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/) ? 'iOS' :
		navigator.userAgent.match(/Android/i) ? 'Android' : '' );
}

// 是否在手机上
function isMobile() {
	var os = getOS();
	return os == 'iOS' || os == 'Android';
}