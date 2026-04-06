package anirbansarkar.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {

	public static ExtentReports getReportObject() {

		ExtentSparkReporter esr;
		ExtentReports er;
		String path = System.getProperty("user.dir") + "//reports//report.html";
		esr = new ExtentSparkReporter(path);
		esr.config().setReportName("Web Automation Results");
		esr.config().setDocumentTitle("Test Results");

		er = new ExtentReports();
		er.attachReporter(esr);
		er.setSystemInfo("Tester", "Anirban");
		return er;

	}

}
