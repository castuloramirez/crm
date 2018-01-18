jQuery(document).ready(function () {
	/* 登录验证 */
	handleLogin();
	/* 切换背景 */
	back();
});

/* 切换背景 */
function back() {
	$.backstretch([
		getWebName() + "/plugins/metronic/assets/pages/media/bg/1.jpg",
		getWebName() + "/plugins/metronic/assets/pages/media/bg/2.jpg",
		getWebName() + "/plugins/metronic/assets/pages/media/bg/3.jpg",
		getWebName() + "/plugins/metronic/assets/pages/media/bg/4.jpg"
	], {
		fade: 1000,
		duration: 8000
	});
}
/* 登录验证 */
var handleLogin = function () {
	$('.login-form').validate({
		/* default input error message container */
		errorElement: 'span',
		/* default input error message class */
		errorClass: 'help-block',
		/* do not focus the last invalid input */
		focusInvalid: false,
		rules: {
			name: {
				required: true
			},
			password: {
				required: true
			}
		},
		messages: {
			name: {
				required: "用户名必填."
			},
			password: {
				required: "密码必填."
			}
		},

		/* display error alert on form submit */
		invalidHandler: function (event, validator) {
			$('.alert-danger', $('.login-form')).show();
			/* 当有验证错误的时候,隐藏model中的error错误提示框 */
			$("#error").hide();
		},

		/* set error class to the control group hightlight error inputs */
		highlight: function (element) {
			$(element).closest('.form-group').addClass('has-error');
		},

		success: function (label) {
			label.closest('.form-group').removeClass('has-error');
			label.remove();
		},

		errorPlacement: function (error, element) {
			error.insertAfter(element.closest('.input-icon'));
		},

		submitHandler: function (form) {
			form.submit();
		}
	});

	$('.login-form input').keypress(function (e) {
		if (e.which == 13) {
			if ($('.login-form').validate().form()) {
				$('.login-form').submit();
			}
			return false;
		}
	});
};