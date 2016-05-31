<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- jsp文件头和头部 -->
<%@ include file="top.jsp"%>   
<html>
	<body>

		<!-- 页面顶部¨ -->
		<%@ include file="head.jsp"%>  
		
		<div class="container-fluid" id="main-container">
			<a href="#" id="menu-toggler"><span></span></a><!-- menu toggler -->



		<!-- 左侧菜单 -->
		<%@ include file="left.jsp"%>  



		
			<div id="main-content" class="clearfix">
					

					<div id="breadcrumbs">

						<ul class="breadcrumb">

							<li><i class="icon-home"></i> <a>首页</a><span class="divider"><i class="icon-angle-right"></i></span></li>

							<li class="active"></li>
						</ul><!--.breadcrumb-->



						<div id="nav-search">

							<form class="form-search">

									<span class="input-icon">

										<input autocomplete="off" id="nav-search-input" type="text" class="input-small search-query" placeholder="Search ..." />

										<i id="nav-search-icon" class="icon-search"></i>

									</span>

							</form>

						</div><!--#nav-search-->

					</div><!--#breadcrumbs-->



					<div id="page-content" class="clearfix">
						
						<div class="page-header position-relative">
							<h1>欢迎使用 <small><i class="icon-double-angle-right">自定义表单+Activiti工作流开发</i></small></h1>
						</div><!--/page-header-->
						
						<div class="row-fluid">
<!-- PAGE CONTENT BEGINS HERE -->
<div class="alert alert-block alert-success">
 <button type="button" class="close" data-dismiss="alert"><i class="icon-remove"></i></button>
 <i class="icon-ok green"></i> 欢迎您登陆 <strong class="green">自定义表单+Activiti工作流开发<small>(www.pxjkc.com)</small></strong>,
 
</div>

<div class="space-6"></div>

		<!-- basic scripts -->
		<script src="${pageContext.request.contextPath}/1.9.1/jquery.min.js"></script>
		<script type="text/javascript">
		window.jQuery || document.write("<script src='${pageContext.request.contextPath}/js/jquery-1.9.1.min.js'>\x3C/script>");
		</script>
		
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
		<!-- page specific plugin scripts -->
		
		<!--[if lt IE 9]>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/excanvas.min.js"></script>
		<![endif]-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.10.2.custom.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.touch-punch.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easy-pie-chart.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.sparkline.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.flot.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.flot.pie.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.flot.resize.min.js"></script>
		<!-- ace scripts -->
		<script src="${pageContext.request.contextPath}/js/ace-elements.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/ace.min.js"></script>
		<!-- inline scripts related to this page -->
		
		<script type="text/javascript">
		
$(function() {
	$('.dialogs,.comments').slimScroll({
        height: '300px'
    });
	
	$('#tasks').sortable();
	$('#tasks').disableSelection();
	$('#tasks input:checkbox').removeAttr('checked').on('click', function(){
		if(this.checked) $(this).closest('li').addClass('selected');
		else $(this).closest('li').removeClass('selected');
	});
	var oldie = $.browser.msie && $.browser.version < 9;
	$('.easy-pie-chart.percentage').each(function(){
		var $box = $(this).closest('.infobox');
		var barColor = $(this).data('color') || (!$box.hasClass('infobox-dark') ? $box.css('color') : 'rgba(255,255,255,0.95)');
		var trackColor = barColor == 'rgba(255,255,255,0.95)' ? 'rgba(255,255,255,0.25)' : '#E2E2E2';
		var size = parseInt($(this).data('size')) || 50;
		$(this).easyPieChart({
			barColor: barColor,
			trackColor: trackColor,
			scaleColor: false,
			lineCap: 'butt',
			lineWidth: parseInt(size/10),
			animate: oldie ? false : 1000,
			size: size
		});
	})
	$('.sparkline').each(function(){
		var $box = $(this).closest('.infobox');
		var barColor = !$box.hasClass('infobox-dark') ? $box.css('color') : '#FFF';
		$(this).sparkline('html', {tagValuesAttribute:'data-values', type: 'bar', barColor: barColor , chartRangeMin:$(this).data('min') || 0} );
	});
	
	
  var data = [
	{ label: "social networks",  data: 38.7, color: "#68BC31"},
	{ label: "search engines",  data: 24.5, color: "#2091CF"},
	{ label: "ad campaings",  data: 8.2, color: "#AF4E96"},
	{ label: "direct traffic",  data: 18.6, color: "#DA5430"},
	{ label: "other",  data: 10, color: "#FEE074"}
  ];
 var placeholder = $('#piechart-placeholder').css({'width':'90%' , 'min-height':'150px'});
 $.plot(placeholder, data, {
	
	series: {
        pie: {
            show: true,
			tilt:0.8,
			highlight: {
				opacity: 0.25
			},
			stroke: {
				color: '#fff',
				width: 2
			},
			startAngle: 2
			
        }
    },
    legend: {
        show: true,
		position: "ne", 
	    labelBoxBorderColor: null,
		margin:[-30,15]
    }
	,
	grid: {
		hoverable: true,
		clickable: true
	},
	tooltip: true, //activate tooltip
	tooltipOpts: {
		content: "%s : %y.1",
		shifts: {
			x: -30,
			y: -50
		}
	}
	
 });
 
  var $tooltip = $("<div class='tooltip top in' style='display:none;'><div class='tooltip-inner'></div></div>").appendTo('body');
  placeholder.data('tooltip', $tooltip);
  var previousPoint = null;
  placeholder.on('plothover', function (event, pos, item) {
	if(item) {
		if (previousPoint != item.seriesIndex) {
			previousPoint = item.seriesIndex;
			var tip = item.series['label'] + " : " + item.series['percent']+'%';
			$(this).data('tooltip').show().children(0).text(tip);
		}
		$(this).data('tooltip').css({top:pos.pageY + 10, left:pos.pageX + 10});
	} else {
		$(this).data('tooltip').hide();
		previousPoint = null;
	}
	
 });
		var d1 = [];
		for (var i = 0; i < Math.PI * 2; i += 0.5) {
			d1.push([i, Math.sin(i)]);
		}
		var d2 = [];
		for (var i = 0; i < Math.PI * 2; i += 0.5) {
			d2.push([i, Math.cos(i)]);
		}
		var d3 = [];
		for (var i = 0; i < Math.PI * 2; i += 0.2) {
			d3.push([i, Math.tan(i)]);
		}
		
		var sales_charts = $('#sales-charts').css({'width':'100%' , 'height':'220px'});
		$.plot("#sales-charts", [
			{ label: "Domains", data: d1 },
			{ label: "Hosting", data: d2 },
			{ label: "Services", data: d3 }
		], {
			hoverable: true,
			shadowSize: 0,
			series: {
				lines: { show: true },
				points: { show: true }
			},
			xaxis: {
				tickLength: 0
			},
			yaxis: {
				ticks: 10,
				min: -2,
				max: 2,
				tickDecimals: 3
			},
			grid: {
				backgroundColor: { colors: [ "#fff", "#fff" ] },
				borderWidth: 1,
				borderColor:'#555'
			}
		});
		$('[data-rel="tooltip"]').tooltip();
})
		</script>
	</body>
</html>
