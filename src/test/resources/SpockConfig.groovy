import com.athaydes.spockframework.report.internal.SpecInfo
import com.athaydes.spockframework.report.IReportCreator
import com.athaydes.spockframework.report.output.SpockReports

spock.reports {
    enabled = true
    template = '/template.html'
    reportFileExtension = 'html'
    summaryFileName = 'summary.html'
}