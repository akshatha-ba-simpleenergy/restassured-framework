package utils;

	import org.jfree.chart.ChartFactory;
	import org.jfree.chart.ChartUtils;
	import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.Color;
import java.io.File;

	public class ChartGenerator {

	    @SuppressWarnings({ "rawtypes", "unchecked" })
		public static void generateChart(int pass, int fail, int skip) throws Exception {

	        DefaultPieDataset dataset = new DefaultPieDataset();
	        dataset.setValue("Pass", pass);
	        dataset.setValue("Fail", fail);
	        dataset.setValue("Skip", skip);

	        JFreeChart chart = ChartFactory.createPieChart(
	                "Execution Results",
	                dataset,
	                true,
	                true,
	                false
	        );
	        
	     // after creating chart
	        PiePlot plot = (PiePlot) chart.getPlot();

	        // ✅ Chart background (outer area)
//	        chart.setBackgroundPaint(new Color(200, 200, 200)); // light grey

	        // ✅ Plot area (inside box)
	        plot.setBackgroundPaint(new Color(200, 200, 200));

	        // keep colors
	        plot.setSectionPaint("Pass", new Color(0, 153, 0));
	        plot.setSectionPaint("Fail", Color.RED);
	        plot.setSectionPaint("Skip", Color.ORANGE);
	        
	        
	        

	        ChartUtils.saveChartAsPNG(
	                new File("test-output/chart.png"),
	                chart,
	                500,
	                300
	        );
	    }
	}

