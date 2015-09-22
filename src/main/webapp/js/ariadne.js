var ariadne = {
	files : {},
	templates : {},
}

Handlebars.registerHelper('list', function(items, options) {
	var out = '<ul class="dropdown-menu">';
	for ( var element in items) {
		var value = options.fn({ 'name' : element }) ;
		var handler = '$(\'#input_' + this.type + '\').val(\'' + value + '\');';
		out = out + '<li><a onclick="' + handler + '">' + value + "</a></li>";
	}
	return out + "</ul>";
});

$(document).ready(
		function() {
			showScanSummary();
			applyFilter({
				'artist' : '*'
			});
			ariadne.templates.tageditorControls = Handlebars.compile($(
					"#tageditor-controls-template").html());
			updateTagEditor();
		});

function startScanner() {
	$("#scanner_running").show();

	var scan;

	$.ajax({
		url : "http://localhost:8080/ariadne/service/scan",
		type : "POST",
		success : function($data) {
			showScanSummary($data);
		},
		error : function($data) {
			console.log("Cannot post new scan");
		}
	});

}

function showScanSummary() {
	$.ajax({
		url : "http://localhost:8080/ariadne/service/scan",
		type : "GET",
		success : function($data) {
			if (typeof $data.scanId !== 'undefined' && $data.scanId != null) {

				var text = "";

				if ($data.finish == null) {
					text = "Scanner running";
				} else {
					var date = new Date($data.start);
					text = "Files scaned on " + date;
				}

			} else {
				text = "No files in database";
			}

			$("#scan_summary").text(text);
		},
		error : function($data) {
			console.log('Cannot fetch last scan');
		}
	});
}

function applyFilter(filter) {

	console.log(filter);
	if (filter === null) {
		filter = {
			"artist" : $("#artist").val(),
			"firstResult" : 0,
			"maxResults" : 30
		}
	}

	$("#file_list").empty();

	$.ajax({
		url : 'http://localhost:8080/ariadne/service/filter',
		data : JSON.stringify(filter),
		type : 'POST',
		contentType : "application/json",
		success : function(data) {

			ariadne.files = {};

			var fileList = "";
			for (var i = 0; i < data.length; i++) {
				ariadne.files[data[i].fileId] = data[i];
				$('#file_list').append(
						'<option value="' + data[i].fileId + '">'
								+ basename(data[i].path) + '</option>');
			}
		},
		errors : function(data) {
			console.log("Filter crashed...");
		}
	});
}

function updateTagEditor() {
	var selected = $("#file_list").find(":selected");

	var type = "artist";
	var values = getSelectedValues(selected);
	
	updateTagInputgroup(values, 'artist');
	updateTagInputgroup(values, 'album');
	updateTagInputgroup(values, 'title');
	updateTagInputgroup(values, 'genre');

	if (typeof values.image === 'undefined') {
		$("#ItemPreview").hide();
		document.getElementById("ItemPreview").src = "";
	} else {
		document.getElementById("ItemPreview").src = "data:image/png;base64,"
				+ values.image;
		$("#ItemPreview").show();
	}
}

function getSelectedValues(selected) {
	var dim = selected.get("length");

	var artists = {};
	var albums = {};
	var titles = {};
	var genres = {};

	for (i = 0; i < dim; i++) {
		var id = selected.get(i).value;
		artists[ariadne.files[id].artist] = true;
		albums[ariadne.files[id].album] = true;
		titles[ariadne.files[id].title] = true;
		genres[ariadne.files[id].genre] = true;
	}

	var result = {
		'artist' : artists,
		'album' : albums,
		'title' : titles,
		'genre' : genres,
	};

	var image = null;
	if (dim > 0) {
		image = ariadne.files[selected.get(0).value].image;
	}

	if (image !== null) {
		result.image = image
	}

	return result;
}

function updateTageditorInputValue(selected, inputId)
{
	console.log(selected + " - " + inputId);
}


function updateTagInputgroup(values, type) {
	var elements = values[type];
	var context = {
		'selected' : elements,
		'value' : Object.keys(elements)[0],
		'type' : type,
		'caption' : type
	}

	$("#tageditor_" + type).html(ariadne.templates.tageditorControls(context))
	$('#input_' + type).val(Object.keys(elements)[0]);
}

function updateInputValue( type, value ) {
	$('#input_' + type).val(value);
}

function _countMembers(object) {
	if (typeof object !== 'object') {
		return 0;
	}

	var counter = 0;
	for (element in object) {
		counter++;
	}
	return counter;
}

function basename(str) {
	var base = new String(str).substring(str.lastIndexOf('/') + 1);
	if (base.lastIndexOf(".") != -1)
		base = base.substring(0, base.lastIndexOf("."));
	return base;
}