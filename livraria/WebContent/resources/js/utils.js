Number.prototype.toCurrency = function(decimals, decimal_sep, thousands_sep) {
	var n = this;
	var c = isNaN(decimals) ? 2 : Math.abs(decimals);
	var d = decimal_sep || ',';

	// if you don't want to use a thousands separator you can pass empty string
	// as thousands_sep value
	var t = (typeof thousands_sep === 'undefined') ? '.' : thousands_sep;
	var prefix = 'R$ '
	var sign = (n < 0) ? '-' : '';

	// extracting the absolute value of the integer part of the number and
	// converting to string
	var i = parseInt(n = Math.abs(n).toFixed(c)) + '';

	var j = ((j = i.length) > 3) ? j % 3 : 0;
	return prefix + sign + (j ? i.substr(0, j) + t : '')
			+ i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t)
			+ (c ? d + Math.abs(n - i).toFixed(c).slice(2) : '');
}

Number.prototype.toCurrencyNoPrefix = function(decimals, decimal_sep,
		thousands_sep) {
	var n = this;
	var c = isNaN(decimals) ? 2 : Math.abs(decimals);
	var d = decimal_sep || ',';

	// if you don't want to use a thousands separator you can pass empty string
	// as thousands_sep value
	var t = (typeof thousands_sep === 'undefined') ? '.' : thousands_sep;
	var sign = (n < 0) ? '-' : '';

	// extracting the absolute value of the integer part of the number and
	// converting to string
	var i = parseInt(n = Math.abs(n).toFixed(c)) + '';

	var j = ((j = i.length) > 3) ? j % 3 : 0;
	return sign + (j ? i.substr(0, j) + t : '')
			+ i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t)
			+ (c ? d + Math.abs(n - i).toFixed(c).slice(2) : '');
}

function formatCurrency() {
	var currency = this.value;
	var number = Number(currency.replace('.', '').replace(',', '.').replace(
			/[^0-9\.-]+/g, ""));
	this.value = number.toCurrency();
}

function formatCurrencyNoPrefix() {
	var currency = this.value;
	var number = Number(currency.replace('.', '').replace(',', '.').replace(
			/[^0-9\.-]+/g, ""));
	this.value = number.toCurrencyNoPrefix();
}

function cleanCurrency() {
	var currency = this.value;
	var number = currency.replace('R$ ', '');
	this.value = number;
	if (number == "0,00") {
		$(this).select();
	}
}
