package org.visminer.web.javascript;

public class ExportToJavascript {

	public ExportToJavascript(){}
	
	/**
	 * 
	 * @param metricDescription String containing metricName Name of metric
	 * @param metricName String containing metricDescription Description of metric
	 * @param values String containing json value
	 * @param greater String containing the greater value of metric values
	 * @param selectedChart the chosen chart
	 * @return String containing scripts all charts in this class
	 */
	public String exportAllCharts(String metricName, String metricDescription,String values,String greater,String selectedChart){
		String chart = "<script>"
					+ "\n"
					+ "$(document).ready(function(){ "+selectedChart+"(); });"
					+ "\n"
					+ "\n"
					+ this.bubbleChart(metricName, metricDescription)
					+ "\n"
					+ this.histogram(values, greater, metricName, metricDescription)
					+ "\n"
					+ this.zoomableCirclePacking(metricName, metricDescription)
					+ "\n"
					+ "</script>";
		return chart;
	}
	
	/**
	 * 
	 * @param metricName String containing metricName Name of metric
	 * @param metricDescription String containing metricDescription Description of metric
	 * @return String containing script of bubble chart
	 */
	public String bubbleChart(String metricName,String metricDescription){
		String script = "function bubbleChart(){\n"
		+"\n"
		+"var format = d3.format(',d'),\n"
		+"    color = d3.scale.category20c();\n"
		+"\n"
		+"var strong = document.createElement('strong');\n"
		+"var h4 = document.createElement('h4');\n"
		+"var t = document.createTextNode('Bubble Chart of Metric "+metricName+" - "+metricDescription+"');\n"
		+"strong.appendChild(t);\n"
		+"h4.appendChild(strong);\n"
		+"var chart = document.getElementById('chart');\n"
		+"chart.appendChild(h4);\n"
		+"\n"
		+"var margin = {\n"
		+"	top : 10,\n"
		+"	right : 30,\n"
		+"	bottom : 30,\n"
		+"	left : 30\n"
		+"}, width = chart.offsetWidth - margin.left - margin.right, height = chart.offsetWidth - margin.top\n"
		+"		- margin.bottom;\n"
		+"\n"
		+"var bubble = d3.layout.pack()\n"
		+"	    .sort(null)\n"
		+"    .size([width, height])\n"
		+"    .padding(1.5);\n"
		+"\n"
		+"var svg = d3.select('#chart').append('svg')\n"
		+"    .attr('width', width)\n"
		+"    .attr('height', height)\n"
		+"    .attr('class', 'bubble');\n"
		+"\n"
		+"d3.json('chart.json', function(error, root) {\n"
		+" if(!error){\n"
		+" var node = svg.selectAll('.node')\n"
		+"      .data(bubble.nodes(classes(root))\n"
		+"      .filter(function(d) { return !d.children; }))\n"
		+"    .enter().append('g')\n"
		+"      .attr('class', 'node')\n"
		+"      .attr('transform', function(d) { return (d.depth != 0) ? 'translate('+ d.x + ',' + d.y + ')' : 'translate('+(width/2)+',200)'; });\n"
		+"\n"
		+"node.append('title')\n"
		+"      .text(function(d) { return (d.depth != 0) ? d.className + ': ' + format(d.value) : 'No data.'; });\n"
		+"\n"
		+"node.append('circle')\n"
		+"      .attr('r', function(d) { return (d.depth != 0) ? d.r : 100; })\n"
		+"      .style('fill', function(d) { return color(d.packageName); });\n"
		+"\n"
		+"node.append('text')\n"
		+"      .attr('dy', '.3em')\n"
		+"      .style('text-anchor', 'middle')\n"
		+"      .text(function(d) { return (d.depth != 0) ? d.className.substring(0, d.r / 3) : 'No data related to '+$('#relatedTo').val()+'.'; });\n"
		+"\n"
		+"  }"
		+"\n"
		+"});\n"
		+"\n"
		+"// Returns a flattened hierarchy containing all leaf nodes under the root.\n"
		+"function classes(root) {\n"
		+"  var classes = [];\n"
		+"\n"
		+"function recurse(name, node) {\n"
		+"    if (node.children) node.children.forEach(function(child) { recurse(node.name, child); });\n"
		+"    else classes.push({packageName: name, className: node.name, value: node.size});\n"
		+"}\n"
		+"\n"
		+"recurse(null, root);\n"
		+"  return {children: classes};\n"
		+"}\n"
		+"\n"
		+"d3.select(self.frameElement).style('height', height + 'px');\n"
		+"\n"
		+"}";
		return script;
	}
	
	/**
	 * 
	 * @param metricName String containing metricName Name of metric
	 * @param metricDescription String containing metricDescription Description of metric
	 * @return String containing script of bubble chart
	 */
	public String zoomableCirclePacking(String metricName,String metricDescription){
		String script = "function zoomableCirclePacking(){\n"
		+"\n"
		+"var color = d3.scale.linear()\n"
		+"    .domain([-1, 5])\n"
		+ "   .range(['hsl(152,80%,80%)', 'hsl(228,30%,40%)'])\n"
		+ "   .interpolate(d3.interpolateHcl);\n"
		+"\n"
		+"var strong = document.createElement('strong');\n"
		+"var h4 = document.createElement('h4');\n"
		+"var t = document.createTextNode('ZoomableCirclePacking "+metricName+" - "+metricDescription+"');\n"
		+"strong.appendChild(t);\n"
		+"h4.appendChild(strong);\n"
		+"var chart = document.getElementById('chart');\n"
		+"chart.appendChild(h4);\n"
		+"\n"
		+"var margin = {\n"
		+"	top : 10,\n"
		+"	right : 30,\n"
		+"	bottom : 30,\n"
		+"	left : 30\n"
		+"}, width = chart.offsetWidth - margin.left - margin.right, height = chart.offsetWidth - margin.top\n"
		+"		- margin.bottom;\n"
		+"\n"
		+"var pack = d3.layout.pack()\n"
		+ "   .padding(2)\n"
		+ "   .size([diameter - margin, diameter - margin])\n"
		+ "   .value(function(d) { return d.size; })\n"
		+ "\n"
		+ "var svg = d3.select('#chart').append('svg')\n"
		+ "    .attr('width', diameter)\n"
		+ "    .attr('height', diameter)\n"
		+ "    .append('g')\n"
		+ "    .attr('transform', 'translate(' + diameter / 2 + ',' + diameter / 2 + ')');\n"
		+"\n"
		+"d3.json('flare.json', function(error, root) {\n"
		+"	if (error) return console.error(error);\n"
		+"\n"
		+"	var focus = root,\n"
		+"	nodes = pack.nodes(root),\n"
		+"	view;\n"
		+"\n"
		+"var circle = svg.selectAll('circle')\n"
		+"    .data(nodes)\n"
		+ "   .enter().append('circle')\n"
		+ "   .attr('class', function(d) { return d.parent ? d.children ? 'node' : 'node node--leaf' : 'node node--root'; })\n"
		+ "   .style('fill', function(d) { return d.children ? color(d.depth) : null; })\n"
		+ "   .on('click', function(d) { if (focus !== d) zoom(d), d3.event.stopPropagation(); });\n"
		+"\n"
		+ "var text = svg.selectAll('text')\n"
		+ "    .data(nodes)\n"
		+ "    .enter().append('text')\n"
		+ "    .attr('class', 'label')\n"
		+ "    .style('fill-opacity', function(d) { return d.parent === root ? 1 : 0; })\n"
		+ "    .style('display', function(d) { return d.parent === root ? null : 'none'; })\n"
		+ "    .text(function(d) { return d.name; });\n"
		+ "\n"
		+"var node = svg.selectAll('circle,text');\n"
		+ "\n"
		+ "d3.select('#chart')\n"
		+"	  .style('background', color(-1))\n"
		+"    .on('click', function() { zoom(root); });\n"
		+ "n"
		+ "zoomTo([root.x, root.y, root.r * 2 + margin]);\n"
		+ "\n"
		+ "function zoom(d) {\n"
		+ "  var focus0 = focus; focus = d;\n"
		+ "\n"
		+ "  var transition = d3.transition()\n"
		+ "  .duration(d3.event.altKey ? 7500 : 750)\n"
		+ "  .tween('zoom', function(d) {\n"
		+ "     var i = d3.interpolateZoom(view, [focus.x, focus.y, focus.r * 2 + margin]);\n"
		+ "     return function(t) { zoomTo(i(t)); };\n"
		+ "   });\n"
		+ "\n"
		+ "  transition.selectAll('text')\n"
		+ "     .filter(function(d) { return d.parent === focus || this.style.display === 'inline'; })\n"
		+ "       .style('fill-opacity', function(d) { return d.parent === focus ? 1 : 0; })\n"
		+ "       .each('start', function(d) { if (d.parent === focus) this.style.display = 'inline'; })\n"
		+ "       .each('end', function(d) { if (d.parent !== focus) this.style.display = 'none'; });\n"
		+ "}\n"
		+ "\n"
		+ "function zoomTo(v) {\n"
		+ "  var k = diameter / v[2]; view = v;\n"
		+ "  node.attr('transform', function(d) { return 'translate(' + (d.x - v[0]) * k + ',' + (d.y - v[1]) * k + ')'; });\n"
		+ "  circle.attr('r', function(d) { return d.r * k; });\n"
		+ "  }\n"
		+ "});\n"
		+ "\n"
		+ "d3.select(self.frameElement).style('height', diameter + 'px');"
		+"\n"
		+"}";
		return script;
	}
	
	
	
	/**
	 * 
	 * @param values String containing json value
	 * @param greater String containing the greater value of metric values
	 * @param metricName String containing name of metric
	 * @param metricDescription String containing description of metric
	 * @return String containing script of histogram chart
	 */
	public String histogram(String values,String greater,String metricName,String metricDescription){
		String script = "function histogramChart(){\n"
		+"var values = "+values+"\n"
		+"//A formatter for counts.\n"
		+"var formatCount = d3.format(',.0f');\n"
		+"var margin = {\n"
		+"	top : 10,\n"
		+"	right : 30,\n"
		+"	bottom : 30,\n"
		+"	left : 30\n"
		+"}, width = 960 - margin.left - margin.right, height = 500 - margin.top\n"
		+"		- margin.bottom;\n"
		+"var x = d3.scale.linear().domain([ 0, "+greater+"]).range([ 0, width ]);\n"
		+"\n"
		+"//Generate a histogram using twenty uniformly-spaced bins.\n"
		+"var data = d3.layout.histogram().bins(x.ticks(20))(values);\n"
		+"var y = d3.scale.linear().domain([ 0,d3.max(data, function(d) {\n"
		+"	return d.y;\n"
		+"}) ]).range([height, 0 ]);\n"
		+"\n"
		+"var xAxis = d3.svg.axis().scale(x).orient('bottom');\n"
		+"\n"
		+"var strong = document.createElement('strong');\n"
		+"var h4 = document.createElement('h4');\n"
		+"var t = document.createTextNode('Histogram of Metric "+metricName+" - "+metricDescription+"');\n"
		+"strong.appendChild(t);\n"
		+"h4.appendChild(strong);\n"
		+"var chart = document.getElementById('chart');\n"
		+"chart.appendChild(h4);\n"
		+"\n"
		+"var svg = d3.select('#chart').append('svg').attr('width',\n"
		+"	width + margin.left + margin.right).attr('height',\n"
		+"	height + margin.top + margin.bottom).append('g').attr(\n"
		+"	'transform',\n"
		+"	'translate(' + margin.left + ',' + margin.top + ')');\n"
		+"\n"
		+"var bar = svg.selectAll('.bar').data(data).enter().append('g').attr(\n"
		+"'class', 'bar').attr('transform', function(d) {\n"
		+"		return 'translate(' + x(d.x) + ',' + y(d.y) + ')';\n"
		+"	});\n"
		+"\n"
		+"bar.append('rect').attr('x', 1).attr('width', x(data[0].dx) - 1).attr(\n"
		+"'height', function(d) {\n"
		+"	return height - y(d.y);\n"
		+"});\n"
		+"\n"
		+"bar.append('text').attr('dy', '.75em').attr('y', 10).attr('x',\n"
		+"	x(data[0].dx) / 2).attr('text-anchor', 'middle').text(\n"
		+"		function(d) {\n"
		+"			return formatCount(d.y);\n"
		+"		});\n"
		+"\n"
		+"var xAxisL = svg.append('g').attr('class', 'x axis')"
		+".attr('transform','translate(0,' + (height) + ')')"
		+".call(xAxis);\n"
		+"xAxisL.append('text')"
		+".attr('class', 'axis-label')"
		+".attr('x', margin.left)"
		+".attr('dy', 56);\n"
		+"//.text('Number');\n"
		+ "\n"
		+"}";
		return script;  	
	}

}
