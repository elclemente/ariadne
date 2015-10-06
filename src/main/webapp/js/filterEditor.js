function updateEnablement(type) {
	if ($('#filterInput' + type).val() != "") {
		$('#filterEnable' + type).prop('checked', true);
	}
	else {
//		$('#filterEnable' + type).prop('checked', false);
	}
}