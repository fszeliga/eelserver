(function(factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as anonymous module.
        define(['jquery'], factory);
    } else {
        // Browser globals.
        factory(jQuery);
    }
}

(function($) {

    var pluses = /\+/g;

    function raw(s) {
        return s;
    }

    function decoded(s) {
        return decodeURIComponent(s.replace(pluses, ' '));
    }

    function converted(s) {
        if (s.indexOf('"') === 0) {
            // This is a quoted cookie as according to RFC2068, unescape
            s = s.slice(1, - 1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
        }
        try {
            return config.json ? JSON.parse(s) : s;
        } catch (er) {}
    }

    var config = $.cookie = function(key, value, options) {

        // write
        if (value !== undefined) {
            options = $.extend({}, config.defaults, options);

            if (typeof options.expires === 'number') {
                var days = options.expires,
                    t = options.expires = new Date();
                t.setDate(t.getDate() + days);
            }

            value = config.json ? JSON.stringify(value) : String(value);

            return (document.cookie = [
            config.raw ? key : encodeURIComponent(key), '=',
            config.raw ? value : encodeURIComponent(value),
            options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
            options.path ? '; path=' + options.path : '',
            options.domain ? '; domain=' + options.domain : '',
            options.secure ? '; secure' : ''].join(''));
        }

        // read
        var decode = config.raw ? raw : decoded;
        var cookies = document.cookie.split('; ');
        var result = key ? undefined : {};
        for (var i = 0, l = cookies.length; i < l; i++) {
            var parts = cookies[i].split('=');
            var name = decode(parts.shift());
            var cookie = decode(parts.join('='));

            if (key && key === name) {
                result = converted(cookie);
                break;
            }

            if (!key) {
                result[name] = converted(cookie);
            }
        }

        return result;
    };

    config.defaults = {};

    $.removeCookie = function(key, options) {
        if ($.cookie(key) !== undefined) {
            $.cookie(key, '', $.extend(options, {
                expires: -1
            }));
            return true;
        }
        return false;
    };

})); 

var at = [
	'bottom left', 'bottom right', 'bottom center',
	'top left', 'top right', 'top center'
];

var demos = {
	contents: {
		'iframe': {
			content: {
				text: '<iframe id="webcamiframe" src="http://IMI-elab1.imi.kit.edu:8081" width="340" height="260" scrolling="auto"></iframe>'
			},
			hide: {
				delay: 90,
				fixed: true
			},
			position: {
				viewport: $(window)
			}
		},
	}	
}


// Setup demos
$.each(demos, function(type, children) {
	$.each(children, function(name, config) {
		var elem = $('#'+type+'-'+name);
		if(!elem.length) { return; }

		if($.isPlainObject(config)) {
			config.id = name;
			elem.qtip(config);
			$('a:not(.source)', elem).click(function(e) { e.preventDefault(); });
		}
		else if($.isFunction(config)) {
			config(elem);
		}
	});
});



