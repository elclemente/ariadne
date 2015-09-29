var ariadne = {
	files : {},
	templates : {},
}

Handlebars.registerHelper('list',
		function(items, options) {
			var out = '<ul class="dropdown-menu">';
			for ( var element in items) {
				var value = options.fn({
					'name' : element
				});
//				var handler = '$(\'#input_' + this.type + '\').val(\'' + value
//						+ '\');';
				var handler = "updateTageditorInput('" + this.type + "', '" + value + "');";
				out = out + '<li><a onclick="' + handler + '">' + value
						+ "</a></li>";
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
	initializeSelectedFiles();

	var type = "artist";

	updateTagInputgroup('artist');
	updateTagInputgroup('album');
	updateTagInputgroup('title');
	updateTagInputgroup('genre');

	$("#selectedFileDiv").text(ariadne.selectedFiles.mainFile);

	if (typeof ariadne.selectedFiles.image === 'undefined') {
		$("#ItemPreview").hide();
		document.getElementById("ItemPreview").src = "";
	} else {
		document.getElementById("ItemPreview").src = "data:image/png;base64,"
				+ ariadne.selectedFiles.image;
		$("#ItemPreview").show();
	}
}

function updateTagInputgroup(type) {
	var selected = ariadne.selectedFiles[type];

	var context = {
		'selected' : selected,
		'value' : Object.keys(selected)[0],
		'type' : type,
		'caption' : type
	}

	var value = Object.keys(selected)[0];

	$("#tageditor_" + type).html(ariadne.templates.tageditorControls(context))
	$('#input_' + type).val(value);

	updateTageditorBadge(type);
}

function updateTageditorInput(type, value) {
	$("#input_" + type).val(value);
	updateTageditorBadge(type);
}

function updateTageditorBadge(type){
	var selected = ariadne.selectedFiles[type];
	var value = $("#input_" + type).val();
	console.log("Value: " + value);
	
	var affectedFiles = 0;
	for (currentValue in selected) {
		if (currentValue != value) {
			affectedFiles += selected[currentValue];
		}
	}

	$("#badge_" + type).text(affectedFiles);
}

function tageditorChanged(type) {
	updateTageditorBadge(type)
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

function initializeSelectedFiles() {

	var selected = $("#file_list").find(":selected");

	var dim = selected.get("length");

	var artists = {};
	var albums = {};
	var titles = {};
	var genres = {};

	function add(set, element) {
		if (set[element] == null) {
			set[element] = 1;
			return;
		}

		set[element] += 1;
	}

	for (i = 0; i < dim; i++) {
		var idSelected = selected.get(i).value;
		var selectedFile = ariadne.files[idSelected];

		add(artists, selectedFile.artist);
		add(titles, selectedFile.title);
		add(albums, selectedFile.album);
		add(genres, selectedFile.genre);
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
		result.mainFile = selected.get(0).text;
	} else {
		result.mainFile = "";
	}

	if (image !== null) {
		result.image = image
	}

	ariadne.selectedFiles = result;
}
